#!/usr/bin/env python3
"""
GitHub Webhook Handler for AnglerNation
FastAPI server that receives GitHub webhooks and queues tasks for CEO orchestrator.
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
from pydantic import BaseModel
from pathlib import Path
import hashlib as hashlib_module

app = FastAPI(title="GitHub Webhook Handler for AnglerNation")

# Configuration
WEBHOOK_SECRET = os.getenv("GITHUB_WEBHOOK_SECRET", "")
TASK_QUEUE_DIR = Path("/tmp/github_webhook_queue")

# Ensure the task queue directory exists
TASK_QUEUE_DIR.mkdir(parents=True, exist_ok=True)

# Setup logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def verify_signature(payload: bytes, signature: str) -> bool:
    """Verify GitHub webhook signature"""
    if not WEBHOOK_SECRET:
        logger.warning("WEBHOOK_SECRET not set, skipping verification")
        return True
    
    if not signature.startswith("sha256="):
        return False
    
    expected = "sha256=" + hmac.new(
        WEBHOOK_SECRET.encode(),
        payload,
        hashlib.sha256
    ).hexdigest()
    
    return hmac.compare_digest(expected, signature)

def enqueue_task(task_data: dict):
    """Add task to processing queue as a separate JSON file"""
    # Create a unique filename based on timestamp and a hash of the task data
    timestamp = datetime.utcnow().strftime("%Y%m%d_%H%M%S_%f")
    # Use issue number or pr number or event type for readability
    issue_num = task_data.get("issue_number", task_data.get("pr_number", "unknown"))
    event = task_data.get("event", "unknown")
    # Create a hash of the task data to avoid collisions
    task_str = json.dumps(task_data, sort_keys=True)
    task_hash = hashlib_module.md5(task_str.encode()).hexdigest()[:8]
    filename = f"{timestamp}_{event}_{issue_num}_{task_hash}.json"
    filepath = TASK_QUEUE_DIR / filename
    # Write the task data to a temporary file and then rename atomically
    temp_filepath = filepath.with_suffix(".json.tmp")
    with open(temp_filepath, "w") as f:
        json.dump(task_data, f, indent=2)
    temp_filepath.rename(filepath)
    logger.info(f"Enqueued task: {task_data.get('event')} - #{task_data.get('issue_number') or task_data.get('pr_number')}")

def process_issue_opened(payload: dict):
    """Handle issues.opened event"""
    issue = payload.get("issue", {})
    if issue.get("pull_request"):  # Skip PRs (handled separately)
        return
    
    task_data = {
        "event": "issue_opened",
        "issue_number": issue["number"],
        "title": issue["title"],
        "body": issue.get("body", ""),
        "labels": [l["name"] for l in issue.get("labels", [])],
        "assignee": issue.get("assignee", {}).get("login") if issue.get("assignee") else None,
        "author": issue["user"]["login"],
    }
    enqueue_task(task_data)

def process_issue_labeled(payload: dict):
    """Handle issues.labeled event"""
    issue = payload.get("issue", {})
    label = payload.get("label", {}).get("name")
    
    if issue.get("pull_request"):
        return
    
    task_data = {
        "event": "issue_labeled",
        "issue_number": issue["number"],
        "label": label,
    }
    enqueue_task(task_data)

def process_issue_unlabeled(payload: dict):
    """Handle issues.unlabeled event"""
    issue = payload.get("issue", {})
    label = payload.get("label", {}).get("name")
    
    if issue.get("pull_request"):
        return
    
    task_data = {
        "event": "issue_unlabeled",
        "issue_number": issue["number"],
        "label": label,
    }
    enqueue_task(task_data)

def process_issue_closed(payload: dict):
    """Handle issues.closed event"""
    issue = payload.get("issue", {})
    
    if issue.get("pull_request"):
        return
    
    task_data = {
        "event": "issue_closed",
        "issue_number": issue["number"],
    }
    enqueue_task(task_data)

def process_issue_reopened(payload: dict):
    """Handle issues.reopened event"""
    issue = payload.get("issue", {})
    
    if issue.get("pull_request"):
        return
    
    task_data = {
        "event": "issue_reopened",
        "issue_number": issue["number"],
        "title": issue["title"],
        "labels": [l["name"] for l in issue.get("labels", [])],
    }
    enqueue_task(task_data)

def process_comment_created(payload: dict):
    """Handle issue_comment.created event"""
    comment = payload.get("comment", {})
    issue = payload.get("issue", {})
    
    if issue.get("pull_request"):
        return  # PR comments handled separately if needed
    
    task_data = {
        "event": "comment_created",
        "issue_number": issue["number"],
        "comment_body": comment["body"],
        "comment_author": comment["user"]["login"],
        "comment_id": comment["id"],
    }
    enqueue_task(task_data)

def process_pr_opened(payload: dict):
    """Handle pull_request.opened/reopened/ready_for_review"""
    pr = payload.get("pull_request", {})
    
    task_data = {
        "event": "pr_opened",
        "pr_number": pr["number"],
        "title": pr["title"],
        "body": pr.get("body", ""),
        "head_branch": pr["head"]["ref"],
        "base_branch": pr["base"]["ref"],
        "author": pr["user"]["login"],
        "draft": pr.get("draft", False),
    }
    enqueue_task(task_data)

def process_pr_closed(payload: dict):
    """Handle pull_request.closed event"""
    pr = payload.get("pull_request", {})
    
    task_data = {
        "event": "pr_closed",
        "pr_number": pr["number"],
        "merged": pr.get("merged", False),
        "head_branch": pr["head"]["ref"],
    }
    enqueue_task(task_data)

@app.post("/webhooks/github")
async def github_webhook(
    request: Request,
    background_tasks: BackgroundTasks,
    x_hub_signature_256: Optional[str] = Header(None),
    x_github_event: Optional[str] = Header(None)
):
    """Receive GitHub webhook events"""
    
    # Get raw body for signature verification
    body = await request.body()
    
    # Verify signature
    if x_hub_signature_256 and not verify_signature(body, x_hub_signature_256):
        logger.warning("Invalid webhook signature")
        raise HTTPException(status_code=401, detail="Invalid signature")
    
    # Parse JSON
    try:
        payload = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")
    
    event = x_github_event
    logger.info(f"Received webhook: {event}")
    
    # Route to handler
    handlers = {
        "issues": {
            "opened": process_issue_opened,
            "labeled": process_issue_labeled,
            "unlabeled": process_issue_unlabeled,
            "closed": process_issue_closed,
            "reopened": process_issue_reopened,
        },
        "issue_comment": {
            "created": process_comment_created,
        },
        "pull_request": {
            "opened": process_pr_opened,
            "reopened": process_pr_opened,
            "ready_for_review": process_pr_opened,
            "closed": process_pr_closed,
        },
    }
    
    if event in handlers:
        action = payload.get("action")
        if action in handlers[event]:
            background_tasks.add_task(handlers[event][action], payload)
            return JSONResponse({"status": "accepted", "event": event, "action": action})
        else:
            logger.info(f"Unhandled action for {event}: {action}")
            return JSONResponse({"status": "ignored", "event": event, "action": action})
    else:
        logger.info(f"Unhandled event type: {event}")
        return JSONResponse({"status": "ignored", "event": event})

@app.get("/health")
async def health():
    return {"status": "ok", "service": "github-webhook"}

@app.get("/queue")
async def get_queue():
    """View current task queue"""
    # Return empty queue for compatibility - the real queue is filesystem-based
    return {"tasks": [], "processed": []}

@app.post("/queue/process")
async def process_queue():
    """Process queued tasks (called by CEO orchestrator)"""
    # This endpoint is not used with filesystem-based queue
    return {"processed": 0, "tasks": []}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8081)