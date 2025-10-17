# Metals MCP Server - Quick Start

## What is it?

The Metals MCP (Model Context Protocol) server provides AI assistants like Cascade with direct access to your Scala project's structure, types, and navigation capabilities.

## Status ✓

- **Server Running**: Yes (port 39777)
- **Windsurf Configured**: Yes
- **Configuration File**: `~/.codeium/windsurf/mcp_config.json`

## Quick Commands

```bash
# Verify the server is running
./scripts/verify-metals-mcp.sh

# Check current MCP endpoint
cat .metals/mcp.json

# View Metals logs
tail -f .metals/metals.log | grep -i mcp

# Enable debug tracing
touch .metals/mcp.trace.json
```

## What Can You Do Now?

With the Metals MCP server configured, AI assistants can:

1. **Navigate Code**: "Find all usages of this TypedEncoder"
2. **Understand Structure**: "Show me the hierarchy of TypedColumn classes"
3. **Get Type Info**: "What's the signature of aggregate function?"
4. **Access Documentation**: "Show me the Scaladoc for this method"
5. **Project Context**: "List all modules in this project"

## Configuration Files

### Windsurf MCP Config
**Location**: `~/.codeium/windsurf/mcp_config.json`

```json
{
  "servers": {
    "frameless-metals": {
      "url": "http://localhost:39777/sse",
      "type": "http"
    }
  }
}
```

### Project MCP Config (Auto-generated)
**Location**: `.metals/mcp.json`

```json
{
  "servers": {
    "frameless-metals": {
      "url": "http://localhost:39777/sse"
    }
  }
}
```

## Troubleshooting

### Server Not Responding?
1. Restart Windsurf/VS Code
2. Check if Metals is running: `ps aux | grep metals`
3. Look for errors in `.metals/metals.log`

### Port Changed?
The port is dynamically assigned. If it changes:
1. Check `.metals/mcp.json` for the new port
2. Update `~/.codeium/windsurf/mcp_config.json`

## More Information

See [MetalsMcpServer.md](./MetalsMcpServer.md) for detailed documentation.
