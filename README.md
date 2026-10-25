# AnglerNation

A Spring Boot application for anglers to track their fishing gear, catches, locations, and share experiences with the community.

---

## 🎯 Project Goal

AnglerNation is a comprehensive fishing companion application that enables anglers to:

- **Catalog Gear**: Track rods, reels, lines, lures with detailed specifications
- **Log Catches**: Record fish catches with species, weight, length, location, gear used, and photos
- **Manage Locations**: Save and share fishing spots with visibility controls (public/private/friends)
- **Social Features**: Create posts, comment on catches, follow other anglers
- **Analytics**: View personal fishing statistics and trends

The app is designed as a **RESTful backend API** that will serve both web and mobile (Android) frontends.

---

## 🏗️ Technical Stack

| Layer | Technology |
|-------|------------|
| **Runtime** | Java 21 |
| **Framework** | Spring Boot 4.1.0 |
| **Build** | Maven (wrapper included) |
| **Database** | PostgreSQL (production), H2 (testing) |
| **ORM** | Spring Data JPA / Hibernate |
| **Validation** | Jakarta Bean Validation |
| **Mapping** | MapStruct 1.6.0 |
| **Boilerplate Reduction** | Lombok 1.18.36 |
| **API Docs** | OpenAPI 3.0 / Swagger UI |
| **Testing** | JUnit 5, Mockito, Testcontainers |
| **CI/CD** | GitHub Actions |

---

## 📁 Project Structure

```
AnglerNation/
├── .github/                    # GitHub Actions workflows
├── .hermes/                    # Agent workspace (logs, agent_work)
├── src/
│   ├── main/
│   │   ├── java/com/anglernation/
│   │   │   ├── DemoApplication.java      # Spring Boot entry point
│   │   │   ├── config/                   # Configuration classes
│   │   │   ├── controllers/              # REST Controllers (9 domains)
│   │   │   ├── dtos/                     # Request/Response DTOs (records)
│   │   │   ├── exceptions/               # Custom exceptions & GlobalExceptionHandler
│   │   │   ├── mappers/                  # MapStruct mappers
│   │   │   ├── models/                   # JPA Entities & Enums
│   │   │   │   ├── users/
│   │   │   │   ├── rods/
│   │   │   │   ├── lures/
│   │   │   │   ├── lines/
│   │   │   │   ├── locations/
│   │   │   │   ├── fish/
│   │   │   │   ├── catches/
│   │   │   │   ├── posts/
│   │   │   │   └── comments/
│   │   │   ├── services/                 # Business logic layer
│   │   │   │   └── repositories/         # Custom repository interfaces
│   │   │   └── ...
│   │   └── resources/
│   │       ├── application.yml           # Main configuration
│   │       └── application-test.yml      # Test configuration
│   └── test/
│       ├── java/com/anglernation/
│       │   ├── services/                 # Unit tests
│       │   ├── controllers/              # Integration tests
│       │   └── DemoApplicationTests.java # Context load test
│       └── resources/
│           └── application.properties    # Test properties
├── webhook-handler/              # Python FastAPI GitHub webhook receiver
├── pom.xml                       # Maven configuration
├── mvnw / mvnw.cmd               # Maven wrapper
└── README.md                     # This file
```

---

## 🤖 Development Workflow: Agent-Based Development

This project uses a **multi-agent orchestration system** powered by **Hermes Agent** and GitHub webhooks. Here's how it works:

### Architecture Overview

```
GitHub Events → Webhook Handler → Task Queue → CEO Orchestrator → Specialist Agents
                                                              ├── backend-agent
                                                              ├── security-agent
                                                              ├── review-agent
                                                              ├── reporting-agent
                                                              ├── frontend-agent
                                                              └── android-* agents
```

### 1. GitHub Webhook Handler (`webhook-handler/app.py`)

A FastAPI server that:
- Receives GitHub webhook events (issues, PRs, comments)
- Verifies HMAC signatures
- Enqueues tasks as JSON files in `/tmp/github_webhook_queue/`
- Supports: `issues.opened`, `issues.labeled`, `issues.reopened`, `pull_request.opened`, `issue_comment.created`

### 2. Task Queue (Filesystem-Based)

Each task is written as an atomic JSON file:
```
/tmp/github_webhook_queue/
  20260124_143022_issue_opened_14_a1b2c3d4.json
  20260124_143510_pr_opened_12_e5f6g7h8.json
```

### 3. CEO Orchestrator (Managing Agent)

The CEO agent:
- Polls the task queue
- Analyzes the issue/PR context
- Creates a **Basecamp todo list** with subtasks
- Delegates to specialist agents via `delegate_task()`
- Monitors progress and aggregates results

### 4. Specialist Agents

Each agent is a Hermes skill with domain expertise:

| Agent | Skill | Responsibilities |
|-------|-------|------------------|
| **Backend** | `devops/backend-agent` | REST APIs, services, repos, DTOs, mappers, tests |
| **Security** | `devops/security-agent` | Auth, JWT, OAuth2, RBAC, vulnerabilities |
| **Review** | `devops/review-agent` | Code review, quality gates, standards |
| **Reporting** | `devops/reporting-agent` | Summaries, PR descriptions, metrics |
| **Frontend** | `devops/frontend-agent` | Android UI, Jetpack Compose |
| **Android Data** | `devops/android-data-agent` | Room DB, DataStore, offline sync |
| **Android Network** | `devops/android-network-agent` | Retrofit, API clients, caching |

### 5. Agent Workflow for an Issue

```
1. GitHub Issue Created (e.g., "Implement Rod CRUD")
       ↓
2. Webhook receives `issues.opened` event
       ↓
3. Task enqueued with full context (title, body, labels, author)
       ↓
4. CEO Orchestrator picks up task
       ↓
5. CEO creates Basecamp todos & delegates to backend-agent:
   - "Create Rod DTOs"
   - "Create RodService with CRUD"
   - "Create RodController with REST endpoints"
   - "Enhance RodRepo with custom queries"
   - "Create RodMapper (MapStruct)"
   - "Write unit tests (80% coverage)"
   - "Write integration tests (Testcontainers)"
       ↓
6. backend-agent implements each piece
       ↓
7. review-agent validates code quality
       ↓
8. reporting-agent generates summary & PR description
       ↓
9. PR created against `AI-Dev` branch
       ↓
10. Human reviews & merges
```

### 6. Agent Persistence & Learning

- **Session Memory**: Each agent has persistent memory across sessions
- **Project Memory**: Stored in `.hermes/memories/` per project
- **Skills**: Reusable workflows in `.hermes/skills/` (version controlled)
- **Logs**: All agent output in `.hermes/logs/`
- **Work Artifacts**: Agent outputs in `.hermes/agent_work/<agent-name>/`

---

## 🚀 Getting Started

### Prerequisites

- Java 21 (managed via `mise` or SDKMAN)
- Maven 3.9+ (or use `./mvnw`)
- PostgreSQL 16+ (for production)
- Docker (for Testcontainers integration tests)

### Quick Start

```bash
# Clone and enter project
cd /home/madjickbull/Projects/AnglerNation

# Build and run tests
./mvnw clean test

# Run application (dev profile)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Access Swagger UI
open http://localhost:8080/swagger-ui.html
```

### Running with PostgreSQL

```bash
# Start PostgreSQL (Docker)
docker run -d \
  --name anglernation-db \
  -e POSTGRES_DB=anglernation \
  -e POSTGRES_USER=anglernation \
  -e POSTGRES_PASSWORD=anglernation \
  -p 5432:5432 \
  postgres:16

# Run app
./mvnw spring-boot:run
```

### Running Integration Tests (Testcontainers)

```bash
# Requires Docker daemon running
./mvnw verify
```

---

## 📚 API Endpoints

All endpoints are under `/api/v1/` with standard REST conventions:

| Domain | Base Path | Operations |
|--------|-----------|------------|
| Users | `/users` | CRUD, profile, auth |
| Rods | `/rods` | CRUD, filter by owner |
| Lures | `/lures` | CRUD, filter by type |
| Lines | `/lines` | CRUD, filter by type |
| Locations | `/locations` | CRUD, visibility, nearby |
| Fish | `/fish` | CRUD, species catalog |
| Catches | `/catches` | CRUD, analytics |
| Posts | `/posts` | CRUD, feed, comments |
| Comments | `/comments` | CRUD on posts/catches |

### Example: Create a Rod

```bash
curl -X POST http://localhost:8080/api/v1/rods \
  -H "Content-Type: application/json" \
  -d '{
    "rodType": "SPINNING",
    "name": "St. Croix Premier",
    "length": 7.0,
    "weight": 4.2,
    "rodPower": "MEDIUM",
    "rodAction": "FAST",
    "ownerId": "550e8400-e29b-41d4-a716-446655440000"
  }'
```

---

## 🧪 Testing Strategy

| Test Type | Location | Run Command |
|-----------|----------|-------------|
| Unit Tests | `src/test/java/.../services/` | `./mvnw test` |
| Integration Tests | `src/test/java/.../controllers/` | `./mvnw verify` |
| Coverage Report | `target/site/jacoco/index.html` | `./mvnw jacoco:report` |

**Target**: 80%+ code coverage on services layer.

---

## 🌿 Branching Strategy

| Branch | Purpose |
|--------|---------|
| `main` | Production-ready, protected |
| `AI-Dev` | Agent development branch (default for agent PRs) |
| `feature/*` | Human feature branches |
| `hotfix/*` | Emergency fixes |

**Agent PRs**: Always target `AI-Dev` branch. Human review required before merge to `main`.

---

## 🔧 Configuration

### Application Profiles

- `dev` - Development (H2 in-memory, debug logging)
- `test` - Testcontainers (PostgreSQL in Docker)
- `prod` - Production (PostgreSQL, optimized)

### Key Properties (`application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/anglernation
    username: anglernation
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  flyway:
    enabled: true

server:
  port: 8080

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

---

## 📦 Building for Production

```bash
# Package as JAR
./mvnw clean package -DskipTests

# Run JAR
java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# Or build Docker image
docker build -t anglernation:latest .
```

---

## 🤝 Contributing

### For Human Contributors

1. Fork the repository
2. Create a feature branch from `main`
3. Make changes with tests
4. Ensure `./mvnw verify` passes
5. Open PR against `main`

### For Agent Contributors

Agents automatically:
1. Receive tasks via GitHub webhooks
2. Implement on `AI-Dev` branch
3. Run full test suite
4. Generate PR with summary
5. Request human review

---

## 📄 License

MIT License - see [LICENSE](LICENSE) for details.

---

## 🙏 Acknowledgments

- **Hermes Agent** by Nous Research for the agent framework
- **Spring Boot** team for the excellent framework
- **MapStruct** for compile-time mapping
- **Testcontainers** for reliable integration testing

---

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/MadjickBull/AnglerNation/issues)
- **Discussions**: [GitHub Discussions](https://github.com/MadjickBull/AnglerNation/discussions)
- **Webhook Endpoint**: `https://your-domain.com/webhooks/github` (configure in GitHub repo settings)

---

*Generated as part of GitHub Issue #14: "Write a comprehensive Readme for this project"*