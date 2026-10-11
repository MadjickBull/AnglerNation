# Agentic Workflow for Android Development

This document explains how to use the agentic workflow system for Android development in the AnglerNation project.

## Overview

The agentic workflow system allows you to create GitHub issues that trigger specialized AI agents to collaboratively implement Android features. The system includes:

- **23 specialized Android agents** covering architecture, DI, UI, data, networking, testing, linting, performance, security, release, and more
- **Automatic routing** of GitHub issues to appropriate agents based on keyword detection
- **Continuous learning** through agent knowledge bases that get updated after each task
- **Centralized logging** and agent output stored in each project's `.hermes` directory
- **Manual on-demand workflow** (start/stop scripts run per development session)

## Prerequisites

1. **Ngrok** must be installed and available in your PATH
2. **Java 21** (via mise or similar) for the AnglerNation backend
3. **GitHub repository** with webhooks configured to point to your ngrok tunnel
4. **AnglerNation project** cloned to `/home/madjickbull/Projects/AnglerNation`

## Getting Started

### 1. Start the Agentic Workflow

Run this command at the beginning of each development session:

```bash
~/.hermes/agent_workflow/start_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
```

This will start three services:
- **Webhook Handler** (FastAPI app on port 8081) - receives GitHub webhooks
- **Ngrok Tunnel** - creates a public URL for GitHub to send webhooks to
- **Queue Processor** - watches for webhook tasks and delegates to Hermes agents

### 2. Configure GitHub Webhook

After starting the workflow, you'll see output similar to:
```
All services started.
To check the status, you can:
  - Check the log files: /home/madjickbull/Projects/AnglerNation/.hermes/logs/*.log
  - View the ngrok tunnel status at: http://127.0.0.1:4040
  - To stop all services, run: /home/madjickbull/.hermes/agent_workflow/stop_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
```

1. Visit <http://127.0.0.1:4040> to see the ngrok inspector
2. Look for the "Forwarding" line showing your public ngrok URL (e.g., `https://abc123.ngrok-free.dev`)
3. In your GitHub repository:
   - Go to Settings → Webhooks → Add webhook
   - Payload URL: `https://<your-ngrok-subdomain>.ngrok-free.dev/webhooks/github`
   - Content type: `application/json`
   - Events: Select "Issues", "Pull request", and "Issue comment"
   - Click "Add webhook"

### 3. Create Android Development Tasks

Create GitHub issues describing the Android work you want done. Use specific keywords to trigger the appropriate agents:

#### Example Issue Templates:

**UI/UX Work:**
```
Title: Add login screen with Jetpack Compose
Body:
- Create a LoginViewModel using Hilt for dependency injection
- Build a LoginScreen composable with email/password fields and validation
- Implement navigation to home screen on successful login
- Add unit tests for the ViewModel using JUnit and MockK
- Run Detekt/Ktlint and fix any warnings
```

**Data Layer Work:**
```
Title: Implement local caching with Room database
Body:
- Create Room database schema for user preferences
- Implement DAO entities for UserSettings and AppConfig
- Build Repository layer with async operations using Coroutines
- Add data migration script for version 1 to 2
- Write instrumented tests for database operations on Android emulator
```

**Networking Work:**
```
Title: Secure API communication with Retrofit and authentication
Body:
- Configure Retrofit instance with OkHttp interceptor for JWT tokens
- Create AuthService for login/token refresh operations
- Implement token storage in EncryptedSharedPreferences
- Add network security configuration for cleartext traffic
- Write unit tests for API service methods with mocked responses
```

**Testing Work:**
```
Title: Add comprehensive test suite for authentication flow
Body:
- Write unit tests for LoginViewModel using JUnit5 and MockK
- Create instrumented tests for LoginActivity using Espresso
- Add Compose UI tests for LoginScreen using createAndroidComposeRule
- Implement test coverage reporting with JaCoCo
- Configure GitHub Actions to run tests on push and pull request
```

**Release Work:**
```
Title: Prepare version 2.0.0 release build
Body:
- Increment versionCode to 200 and versionName to "2.0.0" in build.gradle
- Update release notes in fastlane/metadata/android/en-US/changelogs/default.txt
- Configure Play Store internal test track in GitHub Actions
- Generate signed AAB bundle with appropriate signing config
- Run full test suite including unit, instrumented, and lint checks
```

### 4. Monitor Progress

While agents are working, you can monitor:

- **Ngrok Inspector**: <http://127.0.0.1:4040> - shows webhook requests and responses
- **Webhook Handler Log**: `cat /home/madjickbull/Projects/AnglerNation/.hermes/logs/Webhook_Handler.log`
- **Ngrok Tunnel Log**: `cat /home/madjickbull/Projects/AnglerNation/.hermes/logs/Ngrok_Tunnel.log`
- **Queue Processor Log**: `cat /home/madjickbull/Projects/AnglerNation/.hermes/logs/Queue_Processor.log`
- **Agent Output**: Check `/home/madjickbull/Projects/AnglerNation/.hermes/agent_work/` for:
  - `*_output.txt` files containing agent work products
  - `*.done` files indicating completed tasks
  - Timestamped directories with final reports (`report.md`)

### 5. Review and Merge

When agents complete a task:
1. They generate a report in `/home/madjickbull/Projects/AnglerNation/.hermes/agent_work/<timestamp>/report.md`
2. Optionally create a pull request to the `AI-Dev` branch
3. You can review the PR, test the changes, and merge to main when ready

### 6. Stopping the Workflow

When finished with your development session:

```bash
~/.hermes/agent_workflow/stop_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
```

This cleanly shuts down all services.

## Agent Specializations

The system includes these Android-specific agents:

### Core Architecture
- `android-architect-agent`: Overall architecture, modularization, clean architecture
- `android-di-agent`: Dependency injection (Hilt, Dagger, Service Locator)
- `android-build-agent`: Gradle configuration, build variants, signing

### UI/UX
- `android-ui-agent`: Jetpack Compose, theming, Material Design
- `android-theming-agent`: Material3, dark/light themes, color schemes
- `android-navigation-agent`: Nav graph, Safe Args, deep linking
- `android-accessibility-agent`: contentDescription, TalkBack, accessibility testing
- `android-localization-agent`: String resources, plurals, i18n/l10n
- `android-widget-agent`: Home screen widgets, glance, app widgets
- `android-notification-agent`: Local notifications, push notifications, channels

### Data Layer
- `android-data-agent`: Room database, Repository pattern, DAOs
- `android-network-agent`: Retrofit, Moshi, auth interceptors, API clients
- `android-workmanager-agent`: Background work, periodic tasks, constraints
- `android-data-agent`: DataStore, DataStore proto, serialization

### Quality & Testing
- `android-testing-agent`: Unit tests (JUnit, MockK), instrumented tests, Espresso
- `android-lint-agent`: Detekt, Ktlint, Android Lint, code quality
- `android-performance-agent`: Benchmarking, profiling, startup time optimization
- `android-security-agent`: ProGuard/R8, secrets management, network security config

### DevOps & Release
- `android-release-agent`: Versioning, Play Store publishing, release bundles
- `android-ci-cd-agent`: GitHub Actions, Fastlane, automated testing/deployment
- `android-analytics-agent`: Firebase Analytics, event tracking, user properties
- `android-monetization-agent`: AdMob, in-app purchases, billing library
- `android-docs-agent`: Dokka, Javadoc, README generation, API documentation

## How Agents Learn

Each agent maintains a knowledge base in `~/.hermes/skills/<agent-name>/references/*_knowledge.md` that gets updated after each task with:
- Lessons learned
- Code snippets and patterns that worked
- Configuration tips
- Troubleshooting solutions
- Best practices discovered during implementation

This enables continuous improvement across projects and sessions.

## Troubleshooting

### Common Issues:

1. **"Address already in use" on port 8081**
   - Kill existing processes: `lsof -ti:8081 | xargs kill -9`
   - Restart the workflow

2. **Ngrok tunnel already online**
   - Kill ngrok processes: `pkill -f ngrok`
   - Restart the workflow (uses `--pooling-enabled` to allow multiple connections)

3. **Webhook handler import errors**
   - The start script automatically sets up/verifies the virtual environment
   - Check `.hermes/logs/Webhook_Handler.log` for detailed errors
   - Ensure you have internet access for pip package installation

4. **No agents responding to GitHub issues**
   - Check queue processor log: `.hermes/logs/Queue_Processor.log`
   - Verify webhook handler is receiving GitHub payloads
   - Ensure ngrok tunnel is forwarding to localhost:8081

### Log Locations:
All logs are stored in: `/home/madjickbull/Projects/AnglerNation/.hermes/logs/`
- `Webhook_Handler.log` - FastAPI webhook endpoint
- `Ngrok_Tunnel.log` - Ngrok tunnel stdout/stderr
- `Queue_Processor.log` - Task delegation and agent routing

### Agent Output:
All agent work products are stored in: `/home/madjickbull/Projects/AnglerNation/.hermes/agent_work/`
- Individual task outputs: `*-output.txt`
- Completion markers: `*.done`
- Final reports: Timestamped directories containing `report.md`

## Best Practices

1. **Be Specific in Issues**: The more detailed your GitHub issue, the better the agents can implement it. Include:
   - Clear acceptance criteria
   - Specific technologies to use (or avoid)
   - Testing requirements
   - Performance or security considerations

2. **One Feature Per Issue**: Create separate issues for distinct features to enable parallel agent work.

3. **Use Keywords**: Include relevant technology keywords to trigger the right agents:
   - UI: "compose", "material", "theme", "navigation"
   - Data: "room", "repository", "dao", "datastore"
   - Network: "retrofit", "api", "http", "json"
   - Testing: "test", "junit", "mockito", "espresso"
   - Release: "version", "release", "bundle", "aab"

4. **Review Reports**: Always check the agent-generated report before merging any PRs.

5. **Update Knowledge**: If you discover better approaches during review, consider updating the relevant agent's knowledge base.

## Getting Help

If you encounter issues:
1. Check the relevant log file in `.hermes/logs/`
2. Verify services are running with `ps aux | grep -E "(python.*app.py|ngrok|queue_processor)"`
3. Ensure your internet connection is working for package downloads
4. Confirm ngrok is installed and accessible in your PATH

The agentic workflow is designed to be resilient and self-healing. Most issues can be resolved by restarting the workflow with the start/stop scripts.