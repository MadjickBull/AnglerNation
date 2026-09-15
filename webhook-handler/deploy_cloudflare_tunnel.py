"""
Deploy webhook handler to Cloudflare Tunnel for free HTTPS endpoint.
Run this script to expose local webhook handler to the internet.
"""

import subprocess
import sys
import time
import os
from pathlib import Path

def check_cloudflared():
    """Check if cloudflared is installed."""
    try:
        result = subprocess.run(["cloudflared", "version"], capture_output=True, text=True)
        return result.returncode == 0
    except FileNotFoundError:
        return False

def install_cloudflared():
    """Install cloudflared on Linux."""
    print("Installing cloudflared...")
    try:
        # Download latest release
        subprocess.run([
            "curl", "-L", 
            "https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64",
            "-o", "cloudflared"
        ], check=True)
        
        subprocess.run(["chmod", "+x", "cloudflared"], check=True)
        subprocess.run(["sudo", "mv", "cloudflared", "/usr/local/bin/"], check=True)
        print("✅ cloudflared installed")
        return True
    except subprocess.CalledProcessError as e:
        print(f"❌ Failed to install cloudflared: {e}")
        return False

def run_tunnel(port=8081, tunnel_name="anglernation-webhook"):
    """Run Cloudflare Tunnel to expose local port."""
    print(f"Starting Cloudflare Tunnel for localhost:{port}...")
    print("This will create a public HTTPS URL like: https://random-name.trycloudflare.com")
    print("Press Ctrl+C to stop\n")
    
    try:
        # Run tunnel with random subdomain (no login required)
        subprocess.run([
            "cloudflared", "tunnel", "--url", f"http://localhost:{port}",
            "--no-autoupdate"
        ], check=True)
    except KeyboardInterrupt:
        print("\nTunnel stopped")
    except subprocess.CalledProcessError as e:
        print(f"Tunnel error: {e}")

def main():
    if not check_cloudflared():
        print("cloudflared not found. Installing...")
        if not install_cloudflared():
            print("Please install cloudflared manually:")
            print("  https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/downloads/")
            sys.exit(1)
    
    # Check if webhook handler is running
    print("Make sure the webhook handler is running on port 8081:")
    print("  cd webhook-handler && python app.py")
    print("  OR: docker-compose up")
    print()
    
    input("Press Enter when webhook handler is running, or Ctrl+C to cancel...")
    run_tunnel()

if __name__ == "__main__":
    main()