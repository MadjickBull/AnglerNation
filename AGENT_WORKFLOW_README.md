# Agent Workflow Setup Summary

The agent workflow executables have been moved to a central location to be reusable across projects.

## Location of Executables
All workflow-related scripts are now stored in:
```
$HOME/.hermes/agent_workflow/
```
- `start_agent_workflow.sh` - Starts the services for a given project
- `stop_agent_workflow.sh` - Stops the services for a given project  
- `queue_processor.py` - Watches for GitHub webhooks and delegates tasks to Hermes

## How to Use
To start the workflow for your AnglerNation project (or any other project):

```bash
# Start services (run once per development session)
$HOME/.hermes/agent_workflow/start_agent_workflow.sh /home/madjickbull/Projects/AnglerNation

# When finished:
$HOME/.hermes/agent_workflow/stop_agent_workflow.sh /home/madjickbull/Projects/AnglerNation
```

If no project directory is provided, the scripts will use the current directory.

## What the Scripts Do
- Starts the webhook handler (FastAPI) on port 8081
- Creates an ngrok tunnel to expose the webhook handler to GitHub
- Starts the queue processor which watches `/tmp/github_webhook_queue/` for events
- All logs are stored in `$HOME/.hermes/agent_workflow/logs/<project_name>/`

## GitHub Webhook Setup
1. Run the start script to get a public ngrok URL
2. In your GitHub repository Settings → Webhooks:
   - Payload URL: `https://<subdomain>.ngrok-free.dev/webhooks/github`
   - Content type: `application/json`
   - Select events: Issues, Pull request, Issue comment (at minimum)

## Project-Specific Files Removed from .gitignore
The following files are no longer ignored in your project's .gitignore (since they're now centrally located):
- start_agent_workflow.sh
- stop_agent_workflow.sh
- queue_processor.py
- __pycache__/
- *.pyc, *.pyo, *.pyd
- .python-version, .env
- .venv/, env/, venv/, ENV/
- env.bak/, venv.bak/
- webhook_handler.log, ngrok.log, queue_processor.log

## What Remains Ignored
Your .gitignore still ignores:
- Build artifacts (target/, build/, etc.)
- IDE files (.vscode/, .idea/, *.iws, etc.)
- Project-specific virtual environments (webhook-handler/.venv/, .venv/)

## Log Location
Logs for each project are stored in:
```
$HOME/.hermes/agent_workflow/logs/<project_name>/
```
For AnglerNation, logs will be in:
```
$HOME/.hermes/agent_workflow/logs/AnglerNation/
```

You can check the status of services by examining the log files in that directory.