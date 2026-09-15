# AnglerNation Webhook Handler

FastAPI-based GitHub webhook receiver for the AnglerNation multi-agent development system.

## Features

- **Secure**: HMAC-SHA256 signature verification
- **Event-driven**: Receives GitHub events in real-time (no polling)
- **Multi-event support**: Issues, PRs, comments, pushes, pings
- **Queue-based**: Stores events as JSON files for agent consumption
- **Cloudflare Tunnel ready**: Free HTTPS endpoint for development
- **Docker support**: Containerized deployment

## Supported GitHub Events

| Event | Actions Handled |
|-------|----------------|
| `issues` | opened, closed, labeled, unlabeled, assigned, unassigned, reopened |
| `pull_request` | opened, closed, reopened, ready_for_review, labeled, unlabeled, assigned, unassigned, synchronize |
| `issue_comment` | created, edited, deleted |
| `push` | Any push to any branch |
| `ping` | Webhook connectivity test |

## Quick Start

### 1. Local Development

```bash
cd webhook-handler
pip install -r requirements.txt
export GITHUB_WEBHOOK_SECRET="your-secret-here"
python app.py
# Server runs on http://localhost:8081
```

### 2. Docker

```bash
cd webhook-handler
export GITHUB_WEBHOOK_SECRET="your-secret-here"
docker-compose up
```

### 3. Expose via Cloudflare Tunnel (Free HTTPS)

```bash
# In one terminal - start webhook handler
cd webhook-handler && python app.py

# In another terminal - start tunnel
python deploy_cloudflare_tunnel.py
# Gives you: https://random-name.trycloudflare.com/webhooks/github
```

## Configuration

| Environment Variable | Description | Default |
|---------------------|-------------|---------|
| `GITHUB_WEBHOOK_SECRET` | Secret for HMAC verification (required in production) | empty (dev mode) |
| `WEBHOOK_QUEUE_DIR` | Directory for queued task files | `/tmp/github_webhook_queue` |

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/webhooks/github` | POST | Receive GitHub webhooks |
| `/health` | GET | Health check |
| `/queue` | GET | View queued tasks (debug) |
| `/queue/process` | POST | Get and remove tasks (agent consumption) |

## GitHub App Setup

1. Create GitHub App at `https://github.com/settings/apps`
2. Set **Webhook URL** to your public HTTPS endpoint + `/webhooks/github`
3. Set **Webhook Secret** to a strong random value: `openssl rand -hex 32`
4. Subscribe to events: Issues, Pull requests, Issue comments, Push
5. Install on target repository

## Task Queue Format

Tasks are stored as JSON files in the queue directory:

```json
{
  "event": "issue_opened",
  "github_event": "issues",
  "github_delivery_id": "abc123...",
  "issue_number": 42,
  "title": "Add user profile feature",
  "body": "As a user, I want...",
  "labels": ["backend", "feature"],
  "assignee": null,
  "author": "madjickbull",
  "state": "open",
  "html_url": "https://github.com/MadjickBull/AnglerNation/issues/42",
  "enqueued_at": "2026-09-15T10:30:00Z",
  "queue_file": "2026-09-15T10-30-00_issue_opened.json"
}
```

## Agent Integration

Agents consume tasks via:

```bash
# Get and remove next 50 tasks
curl -X POST http://localhost:8081/queue/process

# Or just view queue without removing
curl http://localhost:8081/queue
```

## Production Deployment

For production, deploy to:
- **AWS Lambda + API Gateway** (serverless, pay-per-request)
- **AWS ECS Fargate** (containerized, always-on)
- **Cloudflare Workers** (edge, low latency)
- **Any container platform** (Railway, Render, Fly.io, etc.)

Always use a proper secret for `GITHUB_WEBHOOK_SECRET` in production.

## License

Part of AnglerNation project.