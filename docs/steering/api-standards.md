# Draftolio AI - API Standards

This document defines the REST conventions, error response formats, authentication flows, versioning strategies, endpoint naming patterns, HTTP status code usage, and request/response examples for the Draftolio AI API.

## API Design Principles

1. **Resource-Oriented**: APIs are organized around resources and use standard HTTP methods to operate on these resources.
2. **Stateless**: Each request contains all the information necessary to process it.
3. **Cacheable**: Responses explicitly indicate their cacheability.
4. **Layered System**: Client cannot tell whether it is connected directly to the end server or an intermediary.
5. **Uniform Interface**: Resources are identified in requests, representations are used for resource manipulation, messages are self-descriptive, and hypermedia drives application state.

## API Versioning

### URL Path Versioning

All API endpoints must include a version in the URL path:

```
/api/v{version}/{resource}
```

Example: `/api/v1/documents`

### Version Lifecycle

1. **Alpha**: Internal testing only, subject to change without notice
2. **Beta**: Limited external testing, may change with notice
3. **GA (General Availability)**: Stable, changes follow deprecation policy
4. **Deprecated**: Still functional but scheduled for removal
5. **Sunset**: No longer available

### Deprecation Policy

1. APIs are never removed without prior notice
2. Deprecated APIs continue to function for at least 6 months
3. Deprecation notices are provided in:
   - API responses (via headers)
   - API documentation
   - Release notes

## URL Structure

### Resource Naming

- Use plural nouns for resource collections: `/documents`, `/templates`
- Use singular nouns for singleton resources: `/user/profile`
- Use kebab-case for multi-word resource names: `/document-templates`

### Hierarchical Resources

For resources that belong to other resources, use nested paths:

```
/api/v1/documents/{documentId}/comments
/api/v1/documents/{documentId}/versions/{versionId}
```

### Query Parameters

- Use for filtering, sorting, pagination, and field selection
- Follow camelCase naming convention
- Common parameters:
  - `page`: Page number (zero-based)
  - `size`: Page size
  - `sort`: Sort field and direction (e.g., `name,asc`)
  - `fields`: Comma-separated list of fields to include
  - `filter`: Filtering criteria

## HTTP Methods

| Method  | Description                                      | Idempotent | Safe |
|---------|--------------------------------------------------|------------|------|
| GET     | Retrieve a resource or collection                | Yes        | Yes  |
| POST    | Create a new resource                            | No         | No   |
| PUT     | Replace a resource completely                    | Yes        | No   |
| PATCH   | Update a resource partially                      | No         | No   |
| DELETE  | Remove a resource                                | Yes        | No   |
| HEAD    | Retrieve headers only                            | Yes        | Yes  |
| OPTIONS | Retrieve supported methods and CORS information  | Yes        | Yes  |

### Method Usage Guidelines

- **GET**: Used for retrieving resources. Never changes state.
- **POST**: Used for creating resources or triggering operations that change state.
- **PUT**: Used for replacing resources completely. Client provides all attributes.
- **PATCH**: Used for partial updates. Client provides only attributes to be changed.
- **DELETE**: Used for removing resources.

## Request Format

### Headers

| Header            | Description                                      | Example                        |
|-------------------|--------------------------------------------------|--------------------------------|
| Accept            | Requested content type                           | `application/json`             |
| Content-Type      | Request body content type                        | `application/json`             |
| Authorization     | Authentication credentials                       | `Bearer {token}`               |
| X-Request-ID      | Unique request identifier for tracing            | `550e8400-e29b-41d4-a716-446655440000` |
| X-API-Key         | API key for authentication (when applicable)     | `abcdef123456`                 |

### Request Body

- Use JSON for request bodies
- Follow camelCase for property names
- Use ISO 8601 format for dates and times (e.g., `2025-08-02T00:46:00Z`)
- Use consistent data types (e.g., always use strings for IDs)

Example POST request:

```json
POST /api/v1/documents HTTP/1.1
Host: api.draftolio.com
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "title": "Contract Agreement",
  "content": "This agreement is made between...",
  "templateId": "template-123",
  "tags": ["legal", "contract"],
  "visibility": "PRIVATE",
  "collaborators": [
    {
      "userId": "user-456",
      "role": "EDITOR"
    }
  ]
}
```

## Response Format

### Headers

| Header                | Description                                      | Example                        |
|-----------------------|--------------------------------------------------|--------------------------------|
| Content-Type          | Response body content type                       | `application/json`             |
| X-Request-ID          | Echo of request identifier                       | `550e8400-e29b-41d4-a716-446655440000` |
| X-Rate-Limit-Limit    | Rate limit ceiling                               | `100`                          |
| X-Rate-Limit-Remaining| Remaining requests in period                     | `95`                           |
| X-Rate-Limit-Reset    | Time when rate limit resets (Unix timestamp)     | `1629461623`                   |
| Cache-Control         | Caching directives                               | `max-age=3600, must-revalidate`|

### Success Response Body

- Use JSON for response bodies
- Follow camelCase for property names
- Include a root object (not an array) for all responses
- For collections, include metadata about the collection

Example GET response for a single resource:

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "doc-789",
  "title": "Contract Agreement",
  "content": "This agreement is made between...",
  "templateId": "template-123",
  "tags": ["legal", "contract"],
  "visibility": "PRIVATE",
  "createdAt": "2025-08-01T14:30:00Z",
  "updatedAt": "2025-08-02T09:15:00Z",
  "createdBy": "user-123",
  "collaborators": [
    {
      "userId": "user-456",
      "role": "EDITOR"
    }
  ],
  "_links": {
    "self": {
      "href": "/api/v1/documents/doc-789"
    },
    "versions": {
      "href": "/api/v1/documents/doc-789/versions"
    },
    "comments": {
      "href": "/api/v1/documents/doc-789/comments"
    }
  }
}
```

Example GET response for a collection:

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "items": [
    {
      "id": "doc-789",
      "title": "Contract Agreement",
      "createdAt": "2025-08-01T14:30:00Z",
      "updatedAt": "2025-08-02T09:15:00Z",
      "_links": {
        "self": {
          "href": "/api/v1/documents/doc-789"
        }
      }
    },
    {
      "id": "doc-790",
      "title": "Project Proposal",
      "createdAt": "2025-08-02T10:45:00Z",
      "updatedAt": "2025-08-02T10:45:00Z",
      "_links": {
        "self": {
          "href": "/api/v1/documents/doc-790"
        }
      }
    }
  ],
  "page": {
    "size": 10,
    "totalElements": 42,
    "totalPages": 5,
    "number": 0
  },
  "_links": {
    "self": {
      "href": "/api/v1/documents?page=0&size=10"
    },
    "next": {
      "href": "/api/v1/documents?page=1&size=10"
    },
    "last": {
      "href": "/api/v1/documents?page=4&size=10"
    }
  }
}
```

## Error Handling

### Error Response Format

All error responses follow the [RFC 9457 Problem Details](https://www.rfc-editor.org/rfc/rfc9457) format:

```json
HTTP/1.1 400 Bad Request
Content-Type: application/problem+json

{
  "type": "https://api.draftolio.com/errors/invalid-input",
  "title": "Invalid Input",
  "status": 400,
  "detail": "The 'title' field is required",
  "instance": "/api/v1/documents/doc-123",
  "errors": [
    {
      "field": "title",
      "message": "must not be null",
      "code": "NotNull"
    }
  ]
}
```

### Common Error Types

| Type                                           | Status | Description                                      |
|------------------------------------------------|--------|--------------------------------------------------|
| https://api.draftolio.com/errors/invalid-input | 400    | Invalid request parameters or body               |
| https://api.draftolio.com/errors/unauthorized  | 401    | Authentication required                          |
| https://api.draftolio.com/errors/forbidden     | 403    | Insufficient permissions                         |
| https://api.draftolio.com/errors/not-found     | 404    | Resource not found                               |
| https://api.draftolio.com/errors/conflict      | 409    | Request conflicts with current state             |
| https://api.draftolio.com/errors/rate-limit    | 429    | Too many requests                                |
| https://api.draftolio.com/errors/server-error  | 500    | Internal server error                            |

### HTTP Status Codes

| Code | Description               | Usage                                                |
|------|---------------------------|------------------------------------------------------|
| 200  | OK                        | Successful GET, PUT, PATCH, or DELETE                |
| 201  | Created                   | Successful resource creation with POST               |
| 202  | Accepted                  | Request accepted for processing (async operations)   |
| 204  | No Content                | Successful operation with no response body           |
| 400  | Bad Request               | Invalid request format or parameters                 |
| 401  | Unauthorized              | Missing or invalid authentication                    |
| 403  | Forbidden                 | Authentication succeeded but insufficient permissions|
| 404  | Not Found                 | Resource not found                                   |
| 405  | Method Not Allowed        | HTTP method not supported for the resource           |
| 409  | Conflict                  | Request conflicts with current state                 |
| 422  | Unprocessable Entity      | Validation errors                                    |
| 429  | Too Many Requests         | Rate limit exceeded                                  |
| 500  | Internal Server Error     | Server-side error                                    |
| 503  | Service Unavailable       | Service temporarily unavailable                      |

## Pagination

### Request Parameters

| Parameter | Description                                      | Default | Example        |
|-----------|--------------------------------------------------|---------|----------------|
| page      | Page number (zero-based)                         | 0       | `?page=2`      |
| size      | Number of items per page                         | 20      | `?size=50`     |
| sort      | Sort field and direction                         | None    | `?sort=name,asc` |

### Response Format

```json
{
  "items": [...],
  "page": {
    "size": 10,
    "totalElements": 42,
    "totalPages": 5,
    "number": 0
  },
  "_links": {
    "self": { "href": "..." },
    "first": { "href": "..." },
    "prev": { "href": "..." },
    "next": { "href": "..." },
    "last": { "href": "..." }
  }
}
```

## Filtering and Sorting

### Filtering

Use query parameters for filtering:

```
/api/v1/documents?status=DRAFT&createdBy=user-123
```

For more complex filtering, use a structured query parameter:

```
/api/v1/documents?filter={"status":"DRAFT","createdAt":{"$gt":"2025-01-01T00:00:00Z"}}
```

### Sorting

Use the `sort` parameter for sorting:

```
/api/v1/documents?sort=title,asc
```

For multiple sort criteria:

```
/api/v1/documents?sort=status,desc&sort=createdAt,desc
```

## Field Selection

Allow clients to request only specific fields:

```
/api/v1/documents?fields=id,title,createdAt
```

## Authentication and Authorization

### Authentication Methods

1. **JWT Bearer Token**
   - Obtain token via OAuth 2.0 flow
   - Include in Authorization header: `Authorization: Bearer {token}`
   - Tokens expire after 1 hour

2. **API Key**
   - For service-to-service communication
   - Include in header: `X-API-Key: {api-key}`
   - Different scopes for different access levels

### OAuth 2.0 Flows

1. **Authorization Code Flow**
   - For web applications
   - Redirect to authorization server
   - Exchange code for token

2. **Client Credentials Flow**
   - For service-to-service communication
   - Direct token request with client ID and secret

3. **Password Grant Flow**
   - For trusted first-party applications only
   - Direct token request with username and password

### Authorization

- Role-based access control (RBAC)
- Resource-level permissions
- Include user roles and permissions in JWT claims

## Rate Limiting

- Rate limits are applied per API key or user
- Limits are specified in requests per minute (RPM)
- Rate limit information is included in response headers

| Header                | Description                                      |
|-----------------------|--------------------------------------------------|
| X-Rate-Limit-Limit    | Maximum requests allowed in the current period   |
| X-Rate-Limit-Remaining| Remaining requests in the current period         |
| X-Rate-Limit-Reset    | Time when the rate limit resets (Unix timestamp) |

## Caching

- Use standard HTTP caching mechanisms
- Include appropriate Cache-Control headers
- Use ETags for conditional requests

Example caching headers:

```
Cache-Control: max-age=3600, must-revalidate
ETag: "33a64df551425fcc55e4d42a148795d9f25f89d4"
```

## CORS (Cross-Origin Resource Sharing)

- CORS is enabled for all API endpoints
- Allowed origins are configurable per environment
- Preflight requests (OPTIONS) are handled automatically

## Webhooks

- Allow clients to subscribe to events via webhooks
- Events are delivered as HTTP POST requests
- Payload includes event type, timestamp, and relevant data
- Clients must acknowledge receipt with 2xx response
- Failed deliveries are retried with exponential backoff

Example webhook payload:

```json
POST /webhook-endpoint HTTP/1.1
Host: client.example.com
Content-Type: application/json
X-Draftolio-Event: document.updated
X-Draftolio-Signature: sha256=...

{
  "event": "document.updated",
  "timestamp": "2025-08-02T00:46:00Z",
  "data": {
    "documentId": "doc-123",
    "title": "Updated Title",
    "updatedBy": "user-456"
  }
}
```

## API Documentation

- All APIs are documented using OpenAPI (Swagger)
- Documentation is generated from code
- Interactive documentation is available at `/swagger-ui.html`
- OpenAPI specification is available at `/v3/api-docs`

## Example API Endpoints

### Documents API

| Method | Endpoint                                  | Description                                      |
|--------|-------------------------------------------|--------------------------------------------------|
| GET    | /api/v1/documents                         | List documents                                   |
| POST   | /api/v1/documents                         | Create a document                                |
| GET    | /api/v1/documents/{id}                    | Get a document                                   |
| PUT    | /api/v1/documents/{id}                    | Update a document (full)                         |
| PATCH  | /api/v1/documents/{id}                    | Update a document (partial)                      |
| DELETE | /api/v1/documents/{id}                    | Delete a document                                |
| GET    | /api/v1/documents/{id}/versions           | List document versions                           |
| GET    | /api/v1/documents/{id}/versions/{version} | Get a specific document version                  |
| POST   | /api/v1/documents/{id}/share              | Share a document                                 |
| GET    | /api/v1/documents/{id}/comments           | List comments on a document                      |
| POST   | /api/v1/documents/{id}/comments           | Add a comment to a document                      |

### Templates API

| Method | Endpoint                                  | Description                                      |
|--------|-------------------------------------------|--------------------------------------------------|
| GET    | /api/v1/templates                         | List templates                                   |
| POST   | /api/v1/templates                         | Create a template                                |
| GET    | /api/v1/templates/{id}                    | Get a template                                   |
| PUT    | /api/v1/templates/{id}                    | Update a template                                |
| DELETE | /api/v1/templates/{id}                    | Delete a template                                |
| POST   | /api/v1/templates/{id}/generate           | Generate a document from a template              |

### Users API

| Method | Endpoint                                  | Description                                      |
|--------|-------------------------------------------|--------------------------------------------------|
| GET    | /api/v1/users/me                          | Get current user profile                         |
| PATCH  | /api/v1/users/me                          | Update current user profile                      |
| GET    | /api/v1/users/me/documents                | List current user's documents                    |
| GET    | /api/v1/users/me/notifications            | List current user's notifications                |
| PUT    | /api/v1/users/me/notifications/{id}/read  | Mark a notification as read                      |
