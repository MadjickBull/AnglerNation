#!/usr/bin/env python3
"""
Create PR for webhook-handler using GitHub App token
"""

import jwt
import time
import requests
import json
import os
from pathlib import Path

# Load credentials
with open('/home/madjickbull/.hermes/github_app_credentials.json') as f:
    creds = json.load(f)

payload = {
    'iat': int(time.time()),
    'exp': int(time.time()) + 600,
    'iss': creds['app_id']
}
with open(creds['private_key_path'], 'r') as f:
    private_key = f.read()

jwt_token = jwt.encode(payload, private_key, algorithm='RS256')

resp = requests.post(
    f'https://api.github.com/app/installations/{creds["installation_id"]}/access_tokens',
    headers={
        'Authorization': f'Bearer {jwt_token}',
        'Accept': 'application/vnd.github+json',
        'X-GitHub-Api-Version': '2022-11-28'
    }
)
TOKEN = resp.json()['token']
print(f"Token: {TOKEN}")

HEADERS = {
    'Authorization': f'Bearer {TOKEN}',
    'Accept': 'application/vnd.github+json',
    'X-GitHub-Api-Version': '2022-11-28'
}

# Get main branch SHA
resp = requests.get('https://api.github.com/repos/MadjickBull/AnglerNation/git/ref/heads/main', headers=HEADERS)
print(f"Main ref: {resp.status_code}")
if resp.status_code != 200:
    print(resp.json())
    exit(1)

main_sha = resp.json()['object']['sha']
print(f"Main SHA: {main_sha}")

# Create new branch for the PR
branch_name = f"feat/webhook-handler-{int(time.time())}"
resp = requests.post(
    'https://api.github.com/repos/MadjickBull/AnglerNation/git/refs',
    headers=HEADERS,
    json={'ref': f'refs/heads/{branch_name}', 'sha': main_sha}
)
print(f"Create branch {branch_name}: {resp.status_code}")
if resp.status_code != 201:
    print(resp.json())
    # Check if branch already exists
    if resp.status_code == 422:
        print("Branch exists, continuing...")
    else:
        exit(1)

# Read all webhook-handler files
webhook_dir = Path('/home/madjickbull/Projects/AnglerNation/webhook-handler')
files_to_upload = []

for filepath in webhook_dir.rglob('*'):
    if filepath.is_file():
        rel_path = filepath.relative_to('/home/madjickbull/Projects/AnglerNation')
        with open(filepath, 'r') as f:
            content = f.read()
        files_to_upload.append({
            'path': str(rel_path).replace('\\', '/'),
            'content': content
        })
        print(f"  Will upload: {rel_path}")

# Create tree
tree = [{'path': f['path'], 'mode': '100644', 'type': 'blob', 'content': f['content']} for f in files_to_upload]
resp = requests.post(
    'https://api.github.com/repos/MadjickBull/AnglerNation/git/trees',
    headers=HEADERS,
    json={'tree': tree, 'base_tree': main_sha}
)
print(f"Create tree: {resp.status_code}")
if resp.status_code != 201:
    print(resp.json())
    exit(1)

tree_sha = resp.json()['sha']
print(f"Tree SHA: {tree_sha}")

# Create commit
resp = requests.post(
    'https://api.github.com/repos/MadjickBull/AnglerNation/git/commits',
    headers=HEADERS,
    json={
        'message': 'feat: Add GitHub webhook handler for multi-agent system\n\n- FastAPI webhook receiver with HMAC-SHA256 verification\n- Supports issues, PRs, comments, push events\n- File-based task queue for agent consumption\n- Docker and Cloudflare Tunnel deployment ready\n- Health check and queue inspection endpoints',
        'tree': tree_sha,
        'parents': [main_sha]
    }
)
print(f"Create commit: {resp.status_code}")
if resp.status_code != 201:
    print(resp.json())
    exit(1)

commit_sha = resp.json()['sha']
print(f"Commit SHA: {commit_sha}")

# Update branch to point to new commit
resp = requests.patch(
    f'https://api.github.com/repos/MadjickBull/AnglerNation/git/refs/heads/{branch_name}',
    headers=HEADERS,
    json={'sha': commit_sha, 'force': True}
)
print(f"Update branch: {resp.status_code}")
if resp.status_code != 200:
    print(resp.json())
    exit(1)

# Create PR targeting AI-Dev branch
resp = requests.post(
    'https://api.github.com/repos/MadjickBull/AnglerNation/pulls',
    headers=HEADERS,
    json={
        'title': 'feat: Add GitHub webhook handler for multi-agent system',
        'head': branch_name,
        'base': 'AI-Dev',
        'body': '## Summary\n\nAdds a complete GitHub webhook handler for the AnglerNation multi-agent development system.\n\n## Features\n\n- **FastAPI webhook receiver** with HMAC-SHA256 signature verification\n- **Multi-event support**: Issues, Pull Requests, Issue Comments, Push events\n- **File-based task queue** for agent consumption\n- **Docker & Cloudflare Tunnel** deployment ready\n- **Health check** and queue inspection endpoints\n\n## Files Added\n\n- `webhook-handler/app.py` - Main FastAPI application\n- `webhook-handler/requirements.txt` - Python dependencies\n- `webhook-handler/Dockerfile` - Container image\n- `webhook-handler/docker-compose.yml` - Local development\n- `webhook-handler/deploy_cloudflare_tunnel.py` - Free HTTPS tunnel\n- `webhook-handler/README.md` - Complete documentation\n\n## Deployment\n\nReady for Cloudflare Tunnel (free HTTPS) or any container platform.\n\n## Next Steps\n\n1. Set `GITHUB_WEBHOOK_SECRET` in production\n2. Configure GitHub App webhook URL to point to deployed endpoint\n3. Agents can consume tasks via `/queue/process` endpoint'
    }
)
print(f"Create PR: {resp.status_code}")
if resp.status_code != 201:
    print(resp.json())
else:
    pr = resp.json()
    print(f"PR created: #{pr['number']} - {pr['html_url']}")