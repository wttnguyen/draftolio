# Draftolio - Technology Stack

This document outlines the technology stack, frameworks, libraries, and tools used in the Draftolio project. All technical decisions and implementations should align with these technology choices to ensure consistency and maintainability.

## Core Technologies

### Backend

#### Java & Spring Framework
- **Java 24**: Latest LTS version with features like virtual threads, pattern matching for switch, record patterns, and other performance improvements
- **Spring Boot 4.0.0**: Latest major version with significant improvements in performance, security, and developer experience
- **Spring WebFlux**: Reactive programming model for building non-blocking, asynchronous applications
- **Spring Security**: Authentication and authorization framework
- **Spring Data**: Data access abstraction for various data stores

#### Database & Storage
- **PostgreSQL 16**: Primary relational database for structured data
- **Redis**: In-memory data structure store for caching and real-time features
- **MongoDB**: Document database for storing draft history and statistics

#### API & Communication
- **RESTful APIs**: Primary API style following REST principles
- **WebSockets**: For real-time draft updates and collaboration
- **Server-Sent Events (SSE)**: For one-way real-time updates to spectators
- **Apache Kafka**: Event streaming platform for asynchronous communication

### Frontend

#### Web Application
- **Angular v20**: Latest version with modern features like signals, standalone components, and new control flow syntax
- **PrimeNG v20**: Comprehensive UI component library for Angular
- **TailwindCSS v4**: Utility-first CSS framework for rapid UI development
- **TypeScript 5.9**: Typed JavaScript for improved developer experience
- **RxJS**: Reactive Extensions library for JavaScript
- **Socket.IO Client**: For WebSocket communication with the backend

### DevOps & Infrastructure

#### Containerization & Orchestration
- **Docker**: Containerization platform
- **Kubernetes**: Container orchestration
- **Helm**: Kubernetes package manager

#### CI/CD
- **GitHub Actions**: Continuous integration and deployment
- **JUnit 5**: Testing framework for Java

#### Monitoring & Observability
- **Prometheus**: Metrics collection and alerting
- **Grafana**: Metrics visualization
- **ELK Stack**: Logging and log analysis
- **OpenTelemetry**: Distributed tracing

## Development Tools

### IDE & Code Editors
- **IntelliJ IDEA**: Primary IDE for Java development
- **Visual Studio Code**: For frontend and scripting development

### Build Tools
- **Maven**: Build automation tool for Java
- **npm/yarn**: Package managers for JavaScript/TypeScript

### Version Control
- **Git**: Distributed version control system
- **GitHub**: Hosting platform for version control and collaboration

## Dependency Management Guidelines

1. **Spring Boot Compatibility**: Use dependencies explicitly supported by Spring Boot 4.0.0. Refer to the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html) for supported dependencies and versions.

2. **Java 24 Compatibility**: Ensure libraries support Java 24. Some older libraries might not be compatible with the latest Java features.

3. **Use Spring Boot Starters**: Whenever possible, use Spring Boot Starters to ensure compatibility and proper integration with the Spring ecosystem.

4. **Avoid Deprecated APIs**: Be aware of deprecated APIs in both Spring Boot and Java, and avoid using them in new code.

5. **Snapshot Repositories**: This project uses Spring Snapshots repository. Be cautious when updating dependencies as snapshot versions may change frequently.

## Technical Constraints

### Performance Requirements
- **Response Time**: API endpoints should respond within 200ms for 95% of requests
- **WebSocket Latency**: Real-time updates should be delivered within 100ms
- **Throughput**: System should handle at least 1000 concurrent users
- **Scalability**: Horizontal scaling for all services

### Security Requirements
- **Authentication**: Riot Sign-On (RSO) only
- **Authorization**: Role-based access control (RBAC)
- **Data Protection**: Encryption at rest and in transit
- **Rate Limiting**: Protection against abuse and DoS attacks

### Availability & Reliability
- **Uptime**: 99.9% availability target
- **Disaster Recovery**: RPO < 15 minutes, RTO < 1 hour
- **Fault Tolerance**: Graceful degradation of services

### Real-time Requirements
- **Draft State Synchronization**: All users must see the same draft state within 500ms
- **Concurrent Actions**: System must handle multiple users attempting actions simultaneously
- **Reconnection Handling**: Seamless reconnection after temporary disconnections
- **Spectator Scaling**: Support for hundreds of spectators per draft

## Technology Selection Criteria

When evaluating new technologies or libraries for inclusion in the project, consider the following criteria:

1. **Compatibility**: Must be compatible with our core technology stack
2. **Maturity**: Prefer established technologies with active maintenance
3. **Community Support**: Strong community and documentation
4. **Performance**: Must meet our performance requirements
5. **Security**: No known critical vulnerabilities
6. **Licensing**: Compatible with our licensing model
7. **Maintainability**: Ease of maintenance and updates

## Technology Roadmap

### Short-term (6 months)
- Implement WebSocket infrastructure for real-time draft updates
- Develop Redis-based draft state management
- Create responsive UI components for draft visualization

### Medium-term (12 months)
- Enhance real-time collaboration features with presence indicators
- Implement advanced draft statistics and analysis
- Integrate with Riot API for player data and champion preferences

### Long-term (24+ months)
- Expand Riot API integration for advanced champion statistics
- Implement AI-assisted draft recommendations
- Develop tournament management features
