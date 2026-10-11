# AnglerNation

A Spring Boot backend application for fishing enthusiasts to track catches, gear, locations, and share fishing experiences.

## Project Overview

AnglerNation is a comprehensive fishing companion platform that allows anglers to:
- **Track fishing gear** - Rods, lures, lines with detailed specifications
- **Log catches** - Record fish caught with location, time, gear used, and photos
- **Discover locations** - Find and share fishing spots with visibility controls
- **Social features** - Posts, comments, and community interactions
- **Fish identification** - Catalog of fish species with details

## Tech Stack

### Backend
- **Java 21** with Spring Boot 3.3
- **PostgreSQL** (production) / **H2** (testing)
- **Spring Data JPA** with Hibernate
- **Spring Security** with JWT authentication (RS256, refresh token rotation)
- **OAuth2 Resource Server** for Google/Apple login (mobile ready)
- **MapStruct** for DTO ↔ Entity mapping
- **Lombok** for boilerplate reduction
- **SpringDoc OpenAPI** for API documentation
- **Maven** for build management

### Infrastructure & DevOps
- **GitHub Webhooks** for event-driven development
- **Ngrok** for local tunnel exposure
- **Hermes Agent** for AI-assisted development workflow
- **Multi-agent orchestration** (Security, Backend, Architect, Review agents)

## Project Structure

```
AnglerNation/
├── src/
│   ├── main/
│   │   ├── java/com/anglernation/
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── controllers/      # REST controllers (to be implemented)
│   │   │   ├── dtos/             # Data Transfer Objects (records)
│   │   │   ├── exceptions/       # Custom exceptions & GlobalExceptionHandler
│   │   │   ├── mappers/          # MapStruct mappers
│   │   │   ├── models/           # JPA entities
│   │   │   │   ├── catches/
│   │   │   │   ├── fish/
│   │   │   │   ├── lines/
│   │   │   │   ├── locations/
│   │   │   │   ├── lures/
│   │   │   │   ├── posts/
│   │   │   │   ├── rods/
│   │   │   │   └── users/
│   │   │   ├── repositories/     # JPA repositories
│   │   │   ├── security/         # Security config, JWT, Auth service
│   │   │   ├── services/         # Business logic services
│   │   │   └── AnglerNationApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/     # Flyway migrations
│   └── test/
│       ├── java/com/anglernation/
│   │   └── resources/
│   │       └── application-test.yml
├── webhook-handler/              # FastAPI GitHub webhook receiver
│   ├── app.py                    # Webhook endpoint
│   ├── queue_processor.py        # Task queue processor
│   └── requirements.txt
├── .hermes/                      # Agent workflow artifacts (gitignored)
│   ├── logs/                     # Service logs
│   ├── agent_work/               # Agent output & reports
│   └── skills/                   # Project-specific agent skills
├── pom.xml
├── AGENTIC_WORKFLOW_SETUP.md     # Centralized workflow setup (Android-focused, but concepts apply)
└── AGENT_WORKFLOW_README.md      # Detailed workflow documentation
```

## Domain Models

| Entity | Description |
|--------|-------------|
| **User** | Angler accounts with roles (ANGLER, ADMIN, MODERATOR) |
| **Rod** | Fishing rods (type, power, action, length, weight) |
| **Lure** | Lures/baits (type, buoyancy, size, color, depth) |
| **Line** | Fishing lines (type, test strength, diameter, material) |
| **Location** | Fishing spots (GPS, water type, visibility, access) |
| **Fish** | Fish species catalog (type, scientific name, habitat) |
| **Catch** | Catch records (fish, location, gear, weight, length, photos) |
| **Post** | Social posts about catches/locations |
| **Comment** | Comments on posts |

## API Endpoints (Planned)

All endpoints under `/api/v1/`

| Domain | Endpoints |
|--------|-----------|
| **Rods** | `GET/POST /rods`, `GET/PUT/DELETE /rods/{id}` |
| **Lures** | `GET/POST /lures`, `GET/PUT/DELETE /lures/{id}` |
| **Lines** | `GET/POST /lines`, `GET/PUT/DELETE /lines/{id}` |
| **Locations** | `GET/POST /locations`, `GET/PUT/DELETE /locations/{id}` |
| **Fish** | `GET/POST /fish`, `GET/PUT/DELETE /fish/{id}` |
| **Catches** | `GET/POST /catches`, `GET/PUT/DELETE /catches/{id}` |
| **Posts** | `GET/POST /posts`, `GET/PUT/DELETE /posts/{id}` |
| **Comments** | `GET/POST /posts/{postId}/comments`, `DELETE /comments/{id}` |
| **Auth** | `POST /auth/register`, `POST /auth/login`, `POST /auth/refresh` |

## Getting Started

### Prerequisites
- Java 21 (via [mise](https://mise.jdx.dev/) or SDKMAN)
- Maven 3.9+
- PostgreSQL 16+ (or Docker)
- Ngrok (for webhook development)

### Local Development

```bash
# Clone and navigate
cd /home/madjickbull/Projects/AnglerNation

# Build and test
mvn clean test

# Run with dev profile (H2 in-memory)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Run with PostgreSQL
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Database Setup

```bash
# Start PostgreSQL (Docker)
docker run --name anglernation-db \
  -e POSTGRES_DB=anglernation \
  -e POSTGRES_USER=angler \
  -e POSTGRES_PASSWORD=angler \
  -p 5432:5432 \
  -d postgres:16

# Run Flyway migrations (automatic on startup)
mvn flyway:migrate
```

### Agentic Development Workflow Setup

To enable the AI agent workflow that processes GitHub issues:

1. Start the workflow services:
   ```bash
   $HOME/.hermes/agent_workflow/start_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
   ```

2. This will start three services:
   - **Webhook Handler** (FastAPI app on port 8081) - receives GitHub webhooks
   - **Ngrok Tunnel** - creates a public URL for GitHub to send webhooks to
   - **Queue Processor** - watches for webhook tasks and delegates to Hermes agents

3. After starting, check the Ngrok inspector at http://127.0.0.1:4040 to see the public URL

4. Configure your GitHub repository webhook:
   - Go to Settings → Webhooks → Add webhook
   - Payload URL: `https://<your-ngrok-subdomain>.ngrok-free.dev/webhooks/github`
   - Content type: `application/json`
   - Events: Select "Issues", "Pull request", and "Issue comment"
   - Click "Add webhook"

5. To stop the workflow when finished:
   ```bash
   $HOME/.hermes/agent_workflow/stop_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
   ```

## Agentic Development Workflow

This project uses an **AI agent workflow** triggered by GitHub webhooks. The system automatically delegates development tasks to specialized agents based on issue content and labels.

### Architecture Overview

```
GitHub Issue/PR → Webhook (FastAPI) → Queue → Hermes Agent → Specialist Agents
                                              ↓
                                    ┌─────────┴─────────┐
                                    │   Agent Types     │
                                    ├── security-agent  │
                                    ├── backend-agent   │
                                    ├── architect-agent │
                                    ├── review-agent    │
                                    └── managing-agent  │
```

### Components

1. **Webhook Handler** (`webhook-handler/app.py`)
   - FastAPI server on port 8081
   - Verifies GitHub HMAC signatures
   - Parses events: issues, PRs, comments, pushes
   - Enqueues tasks to `/tmp/github_webhook_queue/`

2. **Queue Processor** (`webhook-handler/queue_processor.py`)
   - Polls queue directory every 5 seconds
   - Routes events to appropriate agents based on keyword analysis
   - Creates task specifications
   - Invokes Hermes CLI with relevant skills

3. **Ngrok Tunnel**
   - Exposes local webhook handler to GitHub via a secure public URL
   - Uses `--pooling-enabled` to allow multiple concurrent connections
   - Public URL format: `https://<subdomain>.ngrok-free.dev/webhooks/github`
   - Inspection interface available at http://127.0.0.1:4040

4. **Hermes Agent**
   - Runs specialist agents with project context
   - Loads skills: `backend-agent`, `security-agent`, `java-coding`, etc.
   - Outputs reports to `results/` and `.hermes/agent_work/`
   - Updates agent knowledge bases in `~/.hermes/skills/<agent>/references/`

### Tunneling & Network Architecture

The tunneling setup enables secure, real-time communication between GitHub and your local development environment:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        GITHUB EVENT FLOW                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  GitHub Repository                                                           │
│  ┌─────────────┐     HTTPS POST      ┌─────────────────────┐               │
│  │  Webhook    │ ──────────────────▶ │  Ngrok Tunnel       │               │
│  │  Configured │   (Payload + Sig)   │  (HTTPS → HTTP)     │               │
│  └─────────────┘                      └──────────┬──────────┘               │
│                                                   │                          │
│                                                   ▼                          │
│  ┌──────────────────────────────────────────────────────────────────┐       │
│  │                    LOCAL MACHINE                                 │       │
│  │  ┌─────────────────────┐    HTTP     ┌──────────────────────────┐  │       │
│  │  │  Ngrok Client   │ ──────────▶ │  Webhook Handler         │  │       │
│  │  │  (Port 4040)    │             │  (FastAPI :8081)         │  │       │
│  │  └─────────────────┘             └────────────┬─────────────┘  │       │
│  │                                                │                │       │
│  │                                                ▼                │       │
│  │  ┌─────────────────────────────────────────────────────────┐  │       │
│  │  │  Queue Directory (/tmp/github_webhook_queue/)           │  │       │
│  │  │  *.json task files                                      │  │       │
│  │  └────────────────────────┬────────────────────────────────┘  │       │
│  │                           │                                   │       │
│  │                           ▼                                   │       │
│  │  ┌─────────────────────────────────────────────────────────┐  │       │
│  │  │  Queue Processor (queue_processor.py)                   │  │       │
│  │  │  - Polls every 5 seconds                                │  │       │
│  │  │  - Parses event type & keywords                         │  │       │
│  │  │  - Selects appropriate agent(s)                         │  │       │
│  │  │  - Generates task specification                         │  │       │
│  │  │  - Invokes Hermes CLI with skills                       │  │       │
│  │  └────────────────────────┬────────────────────────────────┘  │       │
│  │                           │                                   │       │
│  │                           ▼                                   │       │
│  │  ┌─────────────────────────────────────────────────────────┐  │       │
│  │  │  Hermes Agent Orchestration                             │  │       │
│  │  │  ┌──────────────┐ ┌──────────────┐ ┌────────────────┐  │  │       │
│  │  │  │ managing-    │ │ backend-     │ │ security-      │  │  │       │
│  │  │  │ agent        │ │ agent        │ │ agent          │  │  │       │
│  │  │  │ (orchestrates│ │ (REST APIs,  │ │ (auth, JWT,    │  │  │       │
│  │  │  │  delegation) │ │  services)   │ │  OAuth2)       │  │  │       │
│  │  │  └──────────────┘ └──────────────┘ └────────────────┘  │  │       │
│  │  │  ┌──────────────┐ ┌──────────────┐                      │  │       │
│  │  │  │ architect-   │ │ review-      │  ... additional    │  │  │       │
│  │  │  │ agent        │ │ agent        │     agents         │  │  │       │
│  │  │  │ (design,     │ │ (code        │                      │  │       │
│  │  │  │  patterns)   │ │  quality)    │                      │  │       │
│  │  │  └──────────────┘ └──────────────┘                      │  │       │
│  │  └─────────────────────────────────────────────────────────┘  │       │
│  │                           │                                   │       │
│  │                           ▼                                   │       │
│  │  ┌─────────────────────────────────────────────────────────┐  │       │
│  │  │  Output & Learning                                      │  │       │
│  │  │  - Reports: .hermes/agent_work/<timestamp>/report.md   │  │       │
│  │  │  - Logs: .hermes/logs/*.log                             │  │       │
│  │  │  - Skills updated: ~/.hermes/skills/<agent>/references/ │  │       │
│  │  │  - PRs created to AI-Dev branch                         │  │       │
│  │  └─────────────────────────────────────────────────────────┘  │       │
│  └──────────────────────────────────────────────────────────────────┘       │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

#### Ngrok Tunnel Details
- **Purpose**: Provides a public HTTPS endpoint that forwards to your local webhook handler
- **Technology**: Ngrok creates a secure tunnel from `https://<subdomain>.ngrok-free.dev` → `http://localhost:8081`
- **Configuration**: The start script uses `--pooling-enabled` to allow multiple concurrent connections
- **Persistence**: Uses a reserved subdomain when possible to avoid URL changes between sessions
- **Inspection**: Visit `http://127.0.0.1:4040` to see the Ngrok inspector with request/response details

### Starting the Workflow

```bash
# Start all services (run once per dev session)
$HOME/.hermes/agent_workflow/start_agent_workflow.sh /home/madjickbull/Projects/AnglerNation

# Check ngrok URL at http://127.0.0.1:4040
# Configure GitHub webhook with the ngrok URL
```

### Stopping the Workflow

```bash
$HOME/.hermes/agent_workflow/stop_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
```

### Triggering Agents via GitHub

Create issues with specific keywords to trigger agents:

| Keywords | Agent Triggered |
|----------|-----------------|
| `security`, `auth`, `vulnerability` | security-agent |
| `api`, `controller`, `endpoint`, `rest` | backend-agent |
| `architecture`, `design`, `pattern` | architect-agent |
| `review`, `pr`, `code quality` | review-agent |
| `test`, `testing`, `coverage` | backend-agent + review-agent |

**Example Issue:**
```markdown
Title: Implement Rod CRUD API endpoints

Body:
- Create RodController with full CRUD
- Implement RodService with validation
- Add pagination and filtering
- Write unit and integration tests
- Update OpenAPI documentation
```

### Agent Delegation Flow

When a GitHub issue is created/reopened:

1. **Webhook Received**: GitHub sends POST to ngrok URL → forwarded to local FastAPI handler
2. **Signature Verified**: HMAC-SHA256 verified using `GITHUB_WEBHOOK_SECRET`
3. **Event Parsed**: Issue/PR/comment extracted and converted to task JSON
4. **Task Queued**: JSON written to `/tmp/github_webhook_queue/` with timestamp
5. **Queue Polling**: `queue_processor.py` detects new task within 5 seconds
6. **Keyword Analysis**: Task analyzed for trigger keywords in title, body, labels
7. **Agent Selection**: Appropriate specialist agents identified
8. **Task Specification**: Detailed context package created with:
   - Issue details (title, body, labels, author)
   - Current project state (existing files, patterns)
   - Reference implementations available
   - Expected deliverables and patterns to follow
9. **Hermes Invocation**: CLI launched with relevant skills loaded
10. **Agent Execution**: Specialist agent works on task, produces output
11. **Report Generation**: Markdown report written to `.hermes/agent_work/<timestamp>/`
12. **Skill Updates**: Agent updates its knowledge base in `~/.hermes/skills/<agent>/references/`
13. **PR Creation** (optional): Changes committed and PR opened to `AI-Dev` branch

### Agent Output Location

All agent work products are stored in:
```
/home/madjickbull/Projects/AnglerNation/.hermes/
├── logs/
│   ├── Webhook_Handler.log
│   ├── Ngrok_Tunnel.log
│   └── Queue_Processor.log
└── agent_work/
    ├── task_20261011_103333_output.txt
    ├── task_20261011_103333.done
    └── 2026-10-11_10-33-33/
        └── report.md
```

### Centralized Workflow Scripts

The workflow management scripts are centrally located at `$HOME/.hermes/agent_workflow/` for reuse across projects:

| Script | Purpose |
|--------|---------|
| `start_agent_workflow.sh` | Starts webhook handler, ngrok tunnel, and queue processor |
| `stop_agent_workflow.sh` | Cleanly shuts down all services |
| `queue_processor.py` | Polls queue and delegates to Hermes agents |

Project-specific logs are stored in `$HOME/.hermes/agent_workflow/logs/AnglerNation/`.

## Development Workflow

### 1. Create GitHub Issue
Describe the feature/bug with clear acceptance criteria.

### 2. Agent Processing
- Webhook receives event
- Queue processor routes to appropriate agent
- Agent implements solution following project patterns
- Agent writes report to `results/` and `.hermes/agent_work/`

### 3. Review & Merge
- Review agent-generated report
- Check PR created to `AI-Dev` branch
- Run tests: `mvn clean test`
- Merge to `main` when satisfied

### 4. Continuous Learning
Agents update their knowledge bases in `$HOME/.hermes/skills/<agent-name>/references/` after each task with:
- Lessons learned
- Working code patterns
- Configuration tips
- Troubleshooting solutions

## Testing

```bash
# Unit tests
mvn test

# Integration tests (Testcontainers)
mvn verify -DskipUnitTests

# Specific test class
mvn test -Dtest=RodServiceTest

# Coverage report
mvn jacoco:report
# Open target/site/jacoco/index.html
```

## API Documentation

When running, visit:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Security

- JWT RS256 with 2048-bit keys
- Refresh token rotation with reuse detection
- Method-level `@PreAuthorize` annotations
- Role-based access: `ANGLER`, `ADMIN`, `MODERATOR`
- OAuth2 ready for Google/Apple Sign-In
- Dependency scanning: `mvn org.owasp:dependency-check-maven:check`

## Contributing

1. Create an issue describing the work
2. Agent workflow will pick it up automatically
3. Review the generated PR to `AI-Dev` branch
4. Test locally and merge to `main`

## License

Proprietary - AnglerNation Project

## Contact

Repository: https://github.com/MadjickBull/AnglerNation

## Workflow Test
This README was updated by the backend-agent as part of workflow validation for issue #7 (Test Readme) at 2026-10-11 14:05:36 UTC.

## Workflow Test - Issue Reopened Validation
Validated issue_reopened event handling for issue #7 by backend-agent at 2026-10-11 14:05:36 UTC. Agent executed successfully, generated report at `.hermes/agent_work/backend-agent/2026-10-11_14-05-36_report.md`.

## Workflow Test - Readme Issue Resolution
Updated README to address issue #6 'Readme' by detailing what the project will be about and describing the workflow created for development, including the tunneling used to link GitHub issues along the agentic workflow of the local machine's Hermes agent and all its delegations. Resolved by backend-agent at 2026-10-11 19:31:42 UTC.
