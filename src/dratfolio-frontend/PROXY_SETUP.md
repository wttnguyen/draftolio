# Frontend-Backend Proxy Configuration

This document explains the proxy setup for the Draftolio AI frontend to communicate with the Spring Boot backend during development.

## Overview

The Angular development server (typically running on `http://localhost:4200`) is configured to proxy API requests to the Spring Boot backend server running on
`http://localhost:8080`.

## Configuration Files

### proxy.conf.json

The main proxy configuration file that defines which requests should be proxied to the backend:

```json
{
    "/api/*": {
        "target": "http://localhost:8080",
        "secure": false,
        "logLevel": "debug"
    },
    "/auth/*": {
        "target": "http://localhost:8080",
        "secure": false,
        "logLevel": "debug"
    },
    "/oauth2/*": {
        "target": "http://localhost:8080",
        "secure": false,
        "logLevel": "debug"
    },
    "/oauth2-callback": {
        "target": "http://localhost:8080",
        "secure": false,
        "logLevel": "debug"
    },
    "/login/oauth2/*": {
        "target": "http://localhost:8080",
        "secure": false,
        "logLevel": "debug"
    },
    "/actuator/*": {
        "target": "http://localhost:8080",
        "secure": false,
        "logLevel": "debug"
    }
}
```

### Proxied Endpoints

The following endpoint patterns are automatically proxied to the backend:

- **`/api/*`** - All API endpoints
- **`/auth/*`** - Authentication endpoints (login, logout, user info, etc.)
- **`/oauth2/*`** - OAuth 2.0 authorization endpoints
- **`/oauth2-callback`** - OAuth callback endpoint
- **`/login/oauth2/*`** - OAuth login endpoints
- **`/actuator/*`** - Spring Boot Actuator endpoints (health, metrics, etc.)

## Configuration Options

- **`target`**: The backend server URL (`http://localhost:8080`)
- **`secure`**: Set to `false` for HTTP connections (use `true` for HTTPS)
- **`logLevel`**: Set to `debug` to see proxy logs in the console

## Development Usage

### Starting the Development Server

The proxy configuration is automatically used when running the Angular development server:

```bash
# Standard start (proxy is automatically configured in angular.json)
npm start

# Explicit proxy configuration (alternative)
npm run start:proxy
```

### Verifying Proxy Configuration

1. Start the Spring Boot backend:
   ```bash
   # From the project root
   ./mvnw spring-boot:run
   ```

2. Start the Angular development server:
   ```bash
   # From src/dratfolio-frontend directory
   npm start
   ```

3. Open your browser to `http://localhost:4200`

4. Check the browser's Network tab to verify that API requests are being made to `localhost:4200` but are actually being proxied to `localhost:8080`

5. Check the terminal running `ng serve` for proxy debug logs

## How It Works

1. **Frontend Request**: The Angular app makes an HTTP request to `/auth/login`
2. **Proxy Intercept**: The Angular dev server intercepts this request
3. **Backend Forward**: The request is forwarded to `http://localhost:8080/auth/login`
4. **Response Return**: The backend response is returned to the frontend

## Production Considerations

⚠️ **Important**: This proxy configuration is only for development. In production:

- The Angular app should be built and served as static files
- A reverse proxy (like Nginx) or API Gateway should handle routing
- The backend should be configured with proper CORS settings
- Consider using environment-specific configuration for API base URLs

## Troubleshooting

### Common Issues

1. **Backend not running**: Ensure the Spring Boot application is running on port 8080
2. **Port conflicts**: Check that no other services are using ports 4200 or 8080
3. **CORS errors**: The proxy should handle CORS, but check backend CORS configuration if issues persist
4. **Proxy not working**: Check the console for proxy debug logs and verify the proxy.conf.json syntax

### Debug Logs

With `logLevel: "debug"`, you should see logs like:

```
[HPM] Proxy created: /api/*  -> http://localhost:8080
[HPM] Proxy rewrite rule created: "^/api" ~> ""
```

### Testing Proxy

You can test the proxy by making a request to a backend endpoint:

```bash
# This should return the backend response
curl http://localhost:4200/actuator/health
```

## Environment Variables

For different environments, you can create environment-specific proxy configurations:

- `proxy.conf.json` - Default development
- `proxy.conf.prod.json` - Production-like testing
- `proxy.conf.staging.json` - Staging environment

Use them with:

```bash
ng serve --proxy-config proxy.conf.prod.json
```
