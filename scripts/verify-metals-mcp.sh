#!/bin/bash

# Verify Metals MCP Server
# This script checks if the Metals MCP server is running and accessible

set -e

echo "=== Metals MCP Server Verification ==="
echo ""

# Check if Metals process is running
echo "1. Checking if Metals is running..."
if ps aux | grep -q "[s]cala.meta.metals.Main"; then
    echo "   ✓ Metals process is running"
else
    echo "   ✗ Metals process not found"
    exit 1
fi

# Check if MCP config exists
echo ""
echo "2. Checking MCP configuration..."
if [ -f ".metals/mcp.json" ]; then
    echo "   ✓ MCP config found at .metals/mcp.json"
    echo ""
    echo "   Configuration:"
    cat .metals/mcp.json | sed 's/^/   /'
else
    echo "   ✗ MCP config not found"
    exit 1
fi

# Extract the URL from the config
MCP_URL=$(cat .metals/mcp.json | grep -oP '"url":\s*"\K[^"]+' || echo "")

if [ -z "$MCP_URL" ]; then
    echo "   ✗ Could not extract MCP URL from config"
    exit 1
fi

echo ""
echo "3. Testing connection to $MCP_URL..."
RESPONSE=$(curl -s --max-time 2 "$MCP_URL" -H "Accept: text/event-stream" || echo "FAILED")

if echo "$RESPONSE" | grep -q "endpoint"; then
    echo "   ✓ MCP server is responding"
    echo ""
    echo "   Response:"
    echo "$RESPONSE" | head -5 | sed 's/^/   /'
else
    echo "   ✗ MCP server is not responding"
    exit 1
fi

# Check Windsurf MCP config
echo ""
echo "4. Checking Windsurf MCP configuration..."
WINDSURF_MCP="$HOME/.codeium/windsurf/mcp_config.json"
if [ -f "$WINDSURF_MCP" ]; then
    echo "   ✓ Windsurf MCP config exists at $WINDSURF_MCP"
    if grep -q "frameless-metals" "$WINDSURF_MCP"; then
        echo "   ✓ frameless-metals server is configured"
    else
        echo "   ⚠ frameless-metals server not found in Windsurf config"
    fi
else
    echo "   ⚠ Windsurf MCP config not found (this is okay if using a different IDE)"
fi

# Check Metals log
echo ""
echo "5. Recent MCP-related log entries..."
if [ -f ".metals/metals.log" ]; then
    echo "   Latest MCP server start:"
    grep "Metals MCP server started" .metals/metals.log | tail -1 | sed 's/^/   /'
else
    echo "   ⚠ metals.log not found"
fi

echo ""
echo "=== Verification Complete ==="
echo ""
echo "To enable MCP tracing, create an empty file:"
echo "  touch .metals/mcp.trace.json"
echo ""
echo "To view all available MCP servers in Windsurf:"
echo "  cat ~/.codeium/windsurf/mcp_config.json"
