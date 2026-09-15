"""
GitHub Webhook Handler for AnglerNation Multi-Agent System

Receives GitHub webhook events, verifies signatures, and enqueues tasks
for the agent orchestrator to process.
"""

from fastapi import FastAPI, Request, Header, HTTPException, BackgroundTasks
from fastapi.responses import JSONResponse
import hmac
import hashlib
import os
import json
import logging
from datetime import datetime
from typing import Optional
from pathlib import Path
from pydantic import BaseModel

app = FastAPI(title="AnglerNation Webhook Handler", version="1.0.0")

# Configuration
WEBHOOK_SECRET = os.getenv("GITHUB_WEBHOOK_SECRET", "")
QUEUE_DIR = Path(os.getenv("WEBHOOK_QUEUE_DIR", "/tmp/github_webhook_queue"))
QUEUE_DIR.mkdir(parents=True, exist_ok=True)

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


class WebhookPayload(BaseModel):
    """Base webhook payload model."""
    action: Optional[str] = None
    issue: Optional[dict] = None
    pull_request: Optional[dict] = None
    comment: Optional[dict] = None
    label: Optional[dict] = None
    repository: Optional[dict] = None
    sender: Optional[dict] = None


def verify_signature(payload: bytes, signature: str) -> bool:
    """
    Verify GitHub webhook signature using HMAC-SHA256.
    
    Args:
        payload: Raw request body bytes
        signature: X-Hub-Signature-256 header value
        
    Returns:
        True if signature is valid, False otherwise
    """
    if not WEBHOOK_SECRET:
        logger.warning("GITHUB_WEBHOOK_SECRET not set - skipping verification (dev mode)")
        return True
    
    if not signature or not signature.startswith("sha256="):
        return False
    
    expected = "sha256=" + hmac.new(
        WEBHOOK_SECRET.encode(), payload, hashlib.sha256
    ).hexdigest()
    
    return hmac.compare_digest(expected, signature)


def enqueue_task(task_data: dict) -> Path:
    """
    Save task to queue directory as JSON file.
    
    Args:
        task_data: Task information to queue
        
    Returns:
        Path to the created queue file
    """
    timestamp = datetime.utcnow().isoformat().replace(":", "-")
    event_type = task_data.get("event", "unknown")
    filename = f"{timestamp}_{event_type}.json"
    filepath = QUEUE_DIR / filename
    
    # Add metadata
    task_data["enqueued_at"] = datetime.utcnow().isoformat() + "Z"
    task_data["queue_file"] = filename
    
    with open(filepath, "w") as f:
        json.dump(task_data, f, indent=2)
    
    logger.info(f"Enqueued task: {filepath}")
    return filepath


def parse_issue_event(payload: dict, action: str) -> dict:
    """Parse issue event into standardized task format."""
    issue = payload.get("issue", {})
    return {
        "event": f"issue_{action}",
        "issue_number": issue.get("number"),
        "title": issue.get("title"),
        "body": issue.get("body"),
        "labels": [l.get("name") for l in issue.get("labels", [])],
        "assignee": issue.get("assignee", {}).get("login") if issue.get("assignee") else None,
        "author": issue.get("user", {}).get("login"),
        "state": issue.get("state"),
        "html_url": issue.get("html_url"),
    }


def parse_pull_request_event(payload: dict, action: str) -> dict:
    """Parse pull request event into standardized task format."""
    pr = payload.get("pull_request", {})
    return {
        "event": f"pr_{action}",
        "pr_number": pr.get("number"),
        "title": pr.get("title"),
        "body": pr.get("body"),
        "head_branch": pr.get("head", {}).get("ref"),
        "base_branch": pr.get("base", {}).get("ref"),
        "author": pr.get("user", {}).get("login"),
        "state": pr.get("state"),
        "merged": pr.get("merged", False),
        "html_url": pr.get("html_url"),
    }


def parse_issue_comment_event(payload: dict, action: str) -> dict:
    """Parse issue comment event into standardized task format."""
    comment = payload.get("comment", {})
    issue = payload.get("issue", {})
    return {
        "event": f"comment_{action}",
        "issue_number": issue.get("number"),
        "comment_body": comment.get("body"),
        "comment_author": comment.get("user", {}).get("login"),
        "comment_id": comment.get("id"),
        "html_url": comment.get("html_url"),
    }


@app.post("/webhooks/github")
async def github_webhook(
    request: Request,
    background_tasks: BackgroundTasks,
    x_hub_signature_256: Optional[str] = Header(None),
    x_github_event: Optional[str] = Header(None),
    x_github_delivery: Optional[str] = Header(None),
):
    """
    Handle incoming GitHub webhook events.
    
    Supported events:
    - issues (opened, closed, labeled, unlabeled, assigned, etc.)
    - pull_request (opened, closed, reopened, merged, etc.)
    - issue_comment (created, edited, deleted)
    - push (commits pushed)
    """
    body = await request.body()
    
    # Verify signature
    if not verify_signature(body, x_hub_signature_256):
        logger.warning(f"Invalid signature for delivery {x_github_delivery}")
        raise HTTPException(status_code=401, detail="Invalid signature")
    
    try:
        payload = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON payload: {e}")
        raise HTTPException(status_code=400, detail=f"Invalid JSON: {e}")
    
    event = x_github_event
    logger.info(f"Received GitHub webhook: {event} (delivery: {x_github_delivery})")
    
    task_data = None
    
    # Parse different event types
    if event == "issues":
        action = payload.get("action")
        if action in ["opened", "closed", "labeled", "unlabeled", "assigned", "unassigned", "reopened"]:
            task_data = parse_issue_event(payload, action)
    
    elif event == "pull_request":
        action = payload.get("action")
        if action in ["opened", "closed", "reopened", "ready_for_review", "labeled", "unlabeled", "assigned", "unassigned", "synchronize"]:
            task_data = parse_pull_request_event(payload, action)
            # Special handling for merged PRs
            if action == "closed" and payload.get("pull_request", {}).get("merged"):
                task_data["event"] = "pr_merged"
    
    elif event == "issue_comment":
        action = payload.get("action")
        if action in ["created", "edited", "deleted"]:
            task_data = parse_issue_comment_event(payload, action)
    
    elif event == "push":
        # Push events - could trigger CI/CD or other automation
        repo = payload.get("repository", {})
        commits = payload.get("commits", [])
        ref = payload.get("ref", "")
        task_data = {
            "event": "push",
            "ref": ref,
            "branch": ref.replace("refs/heads/", ""),
            "commits": len(commits),
            "head_commit": commits[-1] if commits else None,
            "pusher": payload.get("pusher", {}).get("name"),
            "repository": repo.get("full_name"),
        }
    
    elif event == "ping":
        # GitHub sends this to test webhook connectivity
        task_data = {
            "event": "ping",
            "zen": payload.get("zen"),
            "hook_id": payload.get("hook_id"),
        }
        logger.info(f"GitHub ping received: {payload.get('zen')}")
    
    else:
        logger.info(f"Unhandled event type: {event}")
        return JSONResponse({"status": "accepted", "event": event, "note": "unhandled_event_type"})
    
    if task_data:
        # Add delivery ID for deduplication
        task_data["github_delivery_id"] = x_github_delivery
        task_data["github_event"] = event
        
        # Enqueue in background to return 200 quickly
        background_tasks.add_task(enqueue_task, task_data)
    
    return JSONResponse({"status": "accepted", "event": event})


@app.get("/health")
async def health():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "anglernation-webhook-handler",
        "timestamp": datetime.utcnow().isoformat() + "Z",
        "queue_dir": str(QUEUE_DIR),
        "queue_files": len(list(QUEUE_DIR.glob("*.json"))),
    }


@app.get("/queue")
async def get_queue(limit: int = 100):
    """
    Get queued tasks (for debugging/monitoring).
    
    Args:
        limit: Maximum number of tasks to return
    """
    files = sorted(QUEUE_DIR.glob("*.json"), key=lambda f: f.stat().st_mtime, reverse=True)
    tasks = []
    
    for filepath in files[:limit]:
        try:
            with open(filepath) as f:
                task = json.load(f)
                task["queue_file"] = filepath.name
                tasks.append(task)
        except Exception as e:
            logger.error(f"Error reading {filepath}: {e}")
    
    return {
        "total_queued": len(list(QUEUE_DIR.glob("*.json"))),
        "returned": len(tasks),
        "tasks": tasks,
    }


@app.post("/queue/process")
async def process_queue(limit: int = 50):
    """
    Process and remove tasks from queue (for agent consumption).
    
    Returns tasks and removes them from queue.
    """
    files = sorted(QUEUE_DIR.glob("*.json"), key=lambda f: f.stat().st_mtime)[:limit]
    processed = []
    
    for filepath in files:
        try:
            with open(filepath) as f:
                task = json.load(f)
                task["queue_file"] = filepath.name
                processed.append(task)
            filepath.unlink()  # Remove from queue after processing
        except Exception as e:
            logger.error(f"Error processing {filepath}: {e}")
    
    return {
        "processed": len(processed),
        "tasks": processed,
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8081)