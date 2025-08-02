# Draftolio AI - Deployment Workflow

This document outlines the build procedures, environment configurations, deployment steps, and rollback strategies for the Draftolio AI project. It provides a comprehensive guide for the CI/CD pipeline and deployment process.

## Environments

### Environment Hierarchy

Draftolio AI uses the following environments:

1. **Local Development**: Individual developer environments
2. **Production (PROD)**: Live environment for end users

### Environment Configurations

Each environment has specific configurations and characteristics:

| Environment | Purpose | Data | Access | Monitoring | Scaling |
|-------------|---------|------|--------|------------|---------|
| Local | Development | Synthetic | Developers | Minimal | None |
| PROD | End user access | Production | Limited access | Full | Auto-scaling |

### Configuration Management

1. **Configuration Sources**:
   - Environment variables for sensitive information
   - Spring profiles for environment-specific configurations
   - Kubernetes ConfigMaps for non-sensitive configuration
   - Kubernetes Secrets for sensitive information

2. **Configuration Files**:
   - `application.properties`: Base configuration
   - `application-{env}.properties`: Environment-specific overrides
   - `bootstrap.properties`: Spring Cloud Config settings

3. **Secret Management**:
   - Use HashiCorp Vault for secret storage
   - Inject secrets into containers at runtime
   - Rotate secrets regularly
   - Audit secret access

## Infrastructure as Code

### Kubernetes Resources

All infrastructure is defined as code using Kubernetes manifests and Helm charts:

1. **Kubernetes Manifests**:
   - Deployments
   - Services
   - Ingress
   - ConfigMaps
   - Secrets
   - PersistentVolumeClaims

2. **Helm Charts**:
   - Application charts
   - Dependency charts
   - Environment-specific values

3. **Terraform**:
   - Cloud infrastructure provisioning
   - Network configuration
   - Database provisioning
   - Monitoring setup

### Infrastructure Repository Structure

```
infrastructure/
├── terraform/
│   ├── modules/
│   │   ├── networking/
│   │   ├── database/
│   │   ├── kubernetes/
│   │   └── monitoring/
│   └── environments/
│       └── production/
├── kubernetes/
│   ├── base/
│   │   ├── deployments/
│   │   ├── services/
│   │   └── ingress/
│   └── overlays/
│       └── production/
└── helm/
    ├── draftolio/
    │   ├── templates/
    │   ├── values.yaml
    │   └── values-{env}.yaml
    └── dependencies/
```

## CI/CD Pipeline

### Continuous Integration

The CI pipeline is triggered on every commit and pull request:

1. **Code Checkout**:
   - Clone repository
   - Checkout branch

2. **Build**:
   - Compile code
   - Run static code analysis
   - Check code style

3. **Test**:
   - Run unit tests
   - Run basic integration tests
   - Generate code coverage reports

4. **Security Scan**:
   - Scan dependencies for vulnerabilities
   - Check for secrets in code

5. **Artifact Creation**:
   - Build Docker images
   - Tag images with commit SHA and branch name
   - Push images to container registry

### Continuous Deployment

The CD pipeline is triggered after successful CI for specific branches:

1. **Production Deployment**:
   - Triggered on tags with `v*` prefix (semantic versioning)
   - Manual approval required
   - Canary or blue-green deployment
   - Run smoke tests

### CI/CD Tools

- **GitHub Actions**: CI/CD orchestration
- **JUnit**: Unit testing
- **Testcontainers**: Basic integration testing
- **OWASP Dependency Check**: Dependency scanning
- **Docker**: Containerization
- **Harbor**: Container registry
- **ArgoCD**: Kubernetes deployment

### Pipeline Configuration

GitHub Actions workflow example:

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main ]
    tags: [ 'v*' ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 24
        uses: actions/setup-java@v3
        with:
          java-version: '24'
          distribution: 'temurin'
          
      - name: Build with Maven
        run: mvn -B package --file pom.xml
        
      - name: Run Tests
        run: mvn test
        
      - name: Build Docker image
        run: |
          docker build -t draftolio:${{ github.sha }} .
          docker tag draftolio:${{ github.sha }} harbor.draftolio.com/draftolio/app:${{ github.sha }}
          
      - name: Push Docker image
        run: |
          echo ${{ secrets.HARBOR_PASSWORD }} | docker login harbor.draftolio.com -u ${{ secrets.HARBOR_USERNAME }} --password-stdin
          docker push harbor.draftolio.com/draftolio/app:${{ github.sha }}
          
  deploy-production:
    needs: build
    if: startsWith(github.ref, 'refs/tags/v')
    runs-on: ubuntu-latest
    environment: production
    steps:
      - name: Deploy to Production
        uses: ./.github/actions/deploy
        with:
          environment: production
          image-tag: ${{ github.sha }}
```

## Build Process

### Backend Build

1. **Compile**:
   - Use Maven or Gradle for Java compilation
   - Set Java version to 24
   - Enable preview features if needed

2. **Package**:
   - Create executable JAR file
   - Include dependencies
   - Set Spring profiles

3. **Containerize**:
   - Build Docker image
   - Use multi-stage builds for smaller images
   - Set appropriate JVM options

Example Dockerfile:

```dockerfile
# Build stage
FROM eclipse-temurin:24-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

# Run stage
FROM eclipse-temurin:24-jre-alpine
VOLUME /tmp
ARG JAR_FILE=/workspace/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
```

### Frontend Build

1. **Install Dependencies**:
   - Use npm or yarn
   - Verify dependency integrity

2. **Lint**:
   - Run ESLint
   - Enforce code style

3. **Build**:
   - Use Angular CLI for production build
   - Enable ahead-of-time compilation
   - Optimize for production

4. **Containerize**:
   - Use Nginx to serve static files
   - Configure for Angular routing

Example Dockerfile for Angular:

```dockerfile
# Build stage
FROM node:20-alpine as build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build -- --configuration production

# Run stage
FROM nginx:alpine
COPY --from=build /app/dist/draftolio /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## Deployment Strategies

### Blue-Green Deployment

For zero-downtime deployments in production:

1. **Setup**:
   - Maintain two identical environments (Blue and Green)
   - Only one environment is live at a time

2. **Process**:
   - Deploy new version to inactive environment
   - Run tests on the new deployment
   - Switch traffic to the new environment
   - Keep old environment as fallback

3. **Implementation**:
   - Use Kubernetes Services to control traffic routing
   - Update Service selector to point to new Deployment

### Canary Deployment

For gradual rollout with risk mitigation:

1. **Setup**:
   - Deploy new version alongside old version
   - Route a small percentage of traffic to new version

2. **Process**:
   - Monitor performance and errors
   - Gradually increase traffic to new version
   - Rollback if issues are detected

3. **Implementation**:
   - Use Istio or similar service mesh for traffic splitting
   - Configure weighted routing rules

### Rollback Strategy

In case of deployment failures:

1. **Automatic Rollbacks**:
   - Monitor deployment health
   - Automatically rollback if health checks fail
   - Alert operations team

2. **Manual Rollbacks**:
   - Provide one-click rollback option
   - Document rollback procedures
   - Test rollback procedures regularly

3. **Implementation**:
   - Keep previous deployment available
   - Store deployment history
   - Maintain database migration scripts

## Database Migrations

### Migration Strategy

1. **Schema Changes**:
   - Use Flyway or Liquibase for database migrations
   - Version all database changes
   - Make changes backward compatible when possible

2. **Data Migrations**:
   - Separate schema changes from data migrations
   - Plan for large data migrations
   - Consider performance impact

3. **Rollback Plans**:
   - Create rollback scripts for each migration
   - Test rollback procedures
   - Document data recovery procedures

### Migration Process

1. **Development**:
   - Developers create migration scripts
   - Test migrations in local environment
   - Review migration scripts

2. **Testing**:
   - Run migrations in local environment
   - Verify basic functionality
   - Ensure data integrity

3. **Production**:
   - Schedule migrations during maintenance windows
   - Backup database before migration
   - Monitor migration progress
   - Verify data integrity after migration

Example Flyway migration:

```sql
-- V1.0.0__Initial_Schema.sql
CREATE TABLE documents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    status VARCHAR(20) NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE document_versions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    document_id BIGINT NOT NULL,
    version INT NOT NULL,
    content TEXT,
    created_by VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (document_id) REFERENCES documents(id)
);
```

## Monitoring and Observability

### Monitoring Stack

1. **Infrastructure Monitoring**:
   - Prometheus for metrics collection
   - Grafana for visualization
   - AlertManager for alerting

2. **Application Monitoring**:
   - Spring Boot Actuator for health and metrics
   - Micrometer for application metrics
   - Custom health indicators for business logic

3. **Log Management**:
   - ELK Stack (Elasticsearch, Logstash, Kibana)
   - Structured logging format (JSON)
   - Centralized log collection

4. **Distributed Tracing**:
   - OpenTelemetry for instrumentation
   - Jaeger or Zipkin for trace visualization
   - Correlation IDs for request tracking

### Key Metrics

1. **System Metrics**:
   - CPU usage
   - Memory usage
   - Disk I/O
   - Network traffic

2. **Application Metrics**:
   - Request rate
   - Error rate
   - Response time
   - Concurrent users

3. **Business Metrics**:
   - Document creation rate
   - User registration rate
   - Collaboration sessions
   - AI processing time

### Alerting

1. **Alert Levels**:
   - Critical: Requires immediate attention
   - Warning: Requires investigation
   - Info: Informational only

2. **Alert Channels**:
   - Email
   - Slack
   - PagerDuty
   - SMS (for critical alerts)

3. **Alert Rules**:
   - High error rate (>1%)
   - Slow response time (>500ms p95)
   - High CPU usage (>80%)
   - Low disk space (<20%)

## Disaster Recovery

### Backup Strategy

1. **Database Backups**:
   - Full daily backups
   - Incremental hourly backups
   - Transaction log backups every 15 minutes
   - Retain backups for 30 days

2. **File Storage Backups**:
   - Daily snapshots
   - Cross-region replication
   - Versioning enabled

3. **Configuration Backups**:
   - Infrastructure as Code in version control
   - Regular exports of configuration

### Recovery Procedures

1. **Database Recovery**:
   - Restore from latest backup
   - Apply transaction logs
   - Verify data integrity

2. **Application Recovery**:
   - Deploy from known good artifact
   - Restore configuration
   - Verify functionality

3. **Complete Environment Recovery**:
   - Provision infrastructure from IaC
   - Deploy applications
   - Restore data
   - Verify system integration

### Basic Recovery Validation

1. **Minimal Validation**:
   - Annual recovery procedure review
   - Basic recovery procedure validation
   - Documentation updates

2. **Essential Scenarios**:
   - Database backup restoration
   - Application deployment verification

## Release Management

### Release Planning

1. **Release Schedule**:
   - Regular releases every 2 weeks
   - Hotfixes as needed
   - Major releases quarterly

2. **Release Coordination**:
   - Release planning meetings
   - Change Advisory Board (CAB) for production changes
   - Communication plan for stakeholders

3. **Release Documentation**:
   - Release notes
   - Deployment instructions
   - Rollback procedures

### Versioning

1. **Semantic Versioning**:
   - Follow SemVer (MAJOR.MINOR.PATCH)
   - Increment MAJOR for breaking changes
   - Increment MINOR for new features
   - Increment PATCH for bug fixes

2. **Version Tagging**:
   - Tag releases in Git
   - Use version in artifact names
   - Document version dependencies

### Changelog Management

1. **Changelog Format**:
   - Group changes by type (Added, Changed, Fixed, Removed)
   - Link to issue tracker
   - Include contributor acknowledgments

2. **Automation**:
   - Generate changelog from commit messages
   - Use conventional commits format
   - Review and edit before release

Example changelog:

```markdown
# Changelog

## [1.2.0] - 2025-08-15

### Added
- AI-powered document suggestions (#123)
- Real-time collaboration indicators (#145)
- Export to PDF functionality (#156)

### Changed
- Improved document editor performance (#134)
- Updated user interface for template selection (#142)
- Enhanced search algorithm for better results (#150)

### Fixed
- Document sharing permissions issue (#138)
- Template rendering in Safari browser (#144)
- User notification delivery delays (#149)

## [1.1.0] - 2025-07-01

...
```

## Environment-Specific Procedures

### Production Environment

1. **Deployment Frequency**:
   - Scheduled releases (bi-weekly)
   - Emergency hotfixes as needed

2. **Deployment Window**:
   - Primary: Tuesdays 2:00-4:00 AM UTC
   - Secondary: Thursdays 2:00-4:00 AM UTC
   - Hotfix: Any time with approval

3. **Approval Process**:
   - Change Advisory Board approval
   - Technical review
   - Business sign-off

4. **Post-Deployment Verification**:
   - Automated smoke tests
   - Manual verification of key functionality
   - Monitoring for anomalies

## Conclusion

This deployment workflow provides a comprehensive framework for building, deploying, and maintaining the Draftolio AI application across all environments. By following these procedures, we ensure consistent, reliable, and secure deployments with minimal disruption to users.

The workflow is designed to be adaptable as the application and team evolve. Regular reviews and improvements to the deployment process should be conducted to incorporate lessons learned and new best practices.
