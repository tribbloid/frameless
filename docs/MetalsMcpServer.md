# Metals MCP Server Configuration

## Overview

The Scala Metals language server has an integrated MCP (Model Context Protocol) server that provides AI assistants with direct access to Scala/sbt project information, navigation, and refactoring capabilities.

## Server Details

- **Server Name**: `frameless-metals`
- **URL**: `http://localhost:39777/sse`
- **Type**: HTTP with Server-Sent Events (SSE)
- **Configuration File**: `.metals/mcp.json` (auto-generated per project)

## Configuration

### Windsurf MCP Configuration

The MCP configuration file is located at:
```
~/.config/Windsurf/User/mcp.json
```

Current configuration:
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

### VS Code Settings

To enable the Metals MCP server, add this to your VS Code/Windsurf settings:
```json
{
  "metals.startMcpServer": true
}
```

## Server Lifecycle

- **Auto-start**: The MCP server starts automatically when Metals initializes for a project
- **Port**: Dynamically assigned (currently 39777 for this project)
- **Log**: Check `.metals/metals.log` for server status and port information
- **Tracing**: Enable by creating an empty file at:
  - `.metals/mcp.trace.json` (project-specific), or
  - `~/.cache/metals/mcp.trace.json` (global)

## Finding the Server

To find the current MCP server endpoint:

```bash
# Check if Metals is running
ps aux | grep scala.meta.metals.Main

# Read the project-specific MCP config
cat .metals/mcp.json

# Check the Metals log for server information
grep "Metals MCP server started" .metals/metals.log
```

## Available MCP Servers

The full Windsurf MCP configuration includes:

1. **frameless-metals**: Scala/sbt project integration (this project)
2. **GitKraken**: Git operations and PR management
3. **deepwiki**: GitHub repository documentation
4. **github**: GitHub API access
5. **unityMCP**: Unity game engine integration

## Capabilities

The Metals MCP server provides access to:

- **Code Navigation**: Jump to definitions, find references, symbol search
- **Project Structure**: List modules, dependencies, compilation units
- **Type Information**: Hover info, type hierarchy, signature help
- **Refactoring**: Rename, organize imports, extract methods
- **Build Integration**: Compile status, build errors, test discovery
- **Documentation**: Scaladoc, API documentation

## Troubleshooting

### Server Not Starting

If the MCP server doesn't start:
1. Check that `"metals.startMcpServer": true` is in settings
2. Restart the Metals language server
3. Check `.metals/metals.log` for errors

### Port Already in Use

If port 39777 is occupied:
1. Metals will automatically select a different port
2. Check `.metals/mcp.json` for the actual port
3. Update `~/.config/Windsurf/User/mcp.json` accordingly

### Connection Issues

To verify the server is accessible:
```bash
curl -s http://localhost:39777/sse -H "Accept: text/event-stream"
```

You should see an endpoint response with a session ID.

## References

- [Metals Documentation](https://scalameta.org/metals/)
- [Model Context Protocol (MCP)](https://modelcontextprotocol.io/)
- [Metals MCP Server Announcement](https://scalameta.org/metals/blog/)
