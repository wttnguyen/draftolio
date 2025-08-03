# Draftolio - Project Structure

This document outlines the project structure, file organization, naming conventions, import patterns, and architectural decisions for the Draftolio project.

## Overall Architecture

Draftolio follows a microservices architecture with a clear separation of concerns. The system is divided into the following main components:

1. **Frontend Applications**
   - Web application (Angular)
   - Mobile applications (future expansion)

2. **Backend Services**
   - API Gateway
   - Authentication Service
   - Draft Service
   - Champion Service
   - User Management Service
   - Notification Service
   - Statistics Service

3. **Data Stores**
   - Relational databases (PostgreSQL)
   - Document databases (MongoDB)
   - Cache (Redis)

4. **Infrastructure Components**
   - Message broker (Kafka)
   - WebSocket server
   - Monitoring and logging

## Backend Structure

### Java Package Structure

The backend services follow a domain-driven design approach with a clear separation of layers. Each service has its own repository and follows this package structure:

```
org.willwin.draftolio
├── [service-name]
│   ├── DraftolioApplication.java (Main application class)
│   ├── config/           (Configuration classes)
│   ├── domain/           (Domain model)
│   │   ├── entity/       (JPA entities)
│   │   ├── repository/   (Spring Data repositories)
│   │   ├── service/      (Domain services)
│   │   └── event/        (Domain events)
│   ├── api/              (API layer)
│   │   ├── controller/   (REST controllers)
│   │   ├── dto/          (Data Transfer Objects)
│   │   ├── mapper/       (DTO-Entity mappers)
│   │   └── exception/    (API exceptions)
│   ├── infrastructure/   (External services integration)
│   │   ├── client/       (External API clients)
│   │   ├── messaging/    (Kafka producers/consumers)
│   │   └── websocket/    (WebSocket handlers)
│   └── util/             (Utility classes)
```

### Resource Structure

```
resources/
├── application.properties       (Main properties file)
├── application-dev.properties   (Development environment properties)
├── application-test.properties  (Test environment properties)
├── application-prod.properties  (Production environment properties)
├── db/
│   ├── migration/               (Flyway database migrations)
│   └── changelog/               (Liquibase changelogs, if used)
├── static/                      (Static resources, if any)
└── champion-data/               (Champion data files)
```

### Test Structure

```
test/
├── java/
│   └── org/willwin/draftolio/
│       ├── [service-name]/
│       │   ├── domain/
│       │   │   ├── entity/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── api/
│       │   │   ├── controller/
│       │   │   └── mapper/
│       │   └── infrastructure/
│       └── util/
└── resources/
    ├── application-test.properties
    └── test-data/                  (Test data files)
```

## Frontend Structure

### Angular Application Structure

```
src/
├── app/
│   ├── core/                    (Core functionality)
│   │   ├── auth/                (Authentication)
│   │   ├── http/                (HTTP interceptors)
│   │   ├── guards/              (Route guards)
│   │   ├── websocket/           (WebSocket services)
│   │   └── services/            (Core services)
│   ├── shared/                  (Shared components)
│   │   ├── components/          (Reusable components)
│   │   ├── directives/          (Custom directives)
│   │   ├── pipes/               (Custom pipes)
│   │   └── models/              (Shared data models)
│   ├── features/                (Feature modules)
│   │   ├── draft/               (Draft management)
│   │   │   ├── components/      (Draft-specific components)
│   │   │   ├── services/        (Draft-specific services)
│   │   │   ├── models/          (Draft-specific models)
│   │   │   └── pages/           (Draft pages)
│   │   ├── champions/           (Champion management)
│   │   ├── user/                (User management)
│   │   └── statistics/          (Statistics and history)
│   ├── layout/                  (Layout components)
│   │   ├── header/              (Header component)
│   │   ├── footer/              (Footer component)
│   │   ├── sidebar/             (Sidebar component)
│   │   └── main/                (Main content area)
│   └── app.component.*          (Root component)
├── assets/                      (Static assets)
│   ├── images/                  (Image files)
│   │   └── champions/           (Champion images)
│   ├── icons/                   (Icon files)
│   └── fonts/                   (Font files)
├── environments/                (Environment configurations)
└── styles/                      (Global styles)
    ├── _variables.css           (CSS variables)
    ├── _mixins.css              (CSS mixins)
    └── _themes.css              (Theme definitions)
```

## Domain Model

### Key Entities

1. **Draft**
   - Properties: id, name, status, draftMode, createdAt, updatedAt, blueSideCaptain, redSideCaptain, currentPhase, currentTurn, blueSideTeam, redSideTeam
   - Relationships: blueSidePicks, redSidePicks, blueSideBans, redSideBans, spectators, draftHistory, blueSideTeam, redSideTeam

2. **Champion**
   - Properties: id, name, title, roles, imageUrl, description

3. **User**
   - Properties: id, username, email, password, role, createdAt, updatedAt, riotId
   - Relationships: createdDrafts, participatedDrafts, spectatedDrafts, ownedTeams, memberOfTeams

4. **Team**
   - Properties: id, name, description, createdAt, updatedAt, ownerId
   - Relationships: owner, players, blueSideDrafts, redSideDrafts

5. **Player**
   - Properties: id, summonerName, riotId, teamId, position, lastAnalyzedAt
   - Relationships: team, topChampions, matchHistory

6. **PlayerChampion**
   - Properties: id, playerId, championId, position, preference (1-3 indicating top 1, 2, or 3), winRate, kda, gamesPlayed, lastUpdatedAt
   - Relationships: player, champion

7. **PlayerMatchHistory**
   - Properties: id, playerId, matchId, championId, position, win, kills, deaths, assists, cs, gameDuration, gameDate
   - Relationships: player, champion

8. **ChampionPerformance**
   - Properties: id, playerId, championId, position, winRate, kda, csPerMin, gamesPlayed, averageDamage
   - Relationships: player, champion

9. **Pick**
   - Properties: id, champion, pickOrder, team, draftId, position, playerId
   - Relationships: player, position

10. **Ban**
   - Properties: id, champion, banOrder, team, draftId

11. **DraftHistory**
   - Properties: id, draftId, action, champion, team, timestamp, user, player, position

## Naming Conventions

### Java Naming Conventions

1. **Classes**
   - Use PascalCase (e.g., `DraftService`, `UserController`)
   - Suffix classes based on their role:
     - Controllers: `*Controller`
     - Services: `*Service`
     - Repositories: `*Repository`
     - Entities: No specific suffix, represent domain concepts
     - DTOs: `*Dto` or `*Request`/`*Response`
     - Mappers: `*Mapper`
     - Configurations: `*Config`

2. **Methods**
   - Use camelCase (e.g., `findById`, `createDraft`)
   - Prefix getter methods with "get" (e.g., `getName`)
   - Prefix boolean getter methods with "is" or "has" (e.g., `isActive`, `hasPermission`)
   - Prefix setter methods with "set" (e.g., `setName`)

3. **Variables and Parameters**
   - Use camelCase (e.g., `draftId`, `userName`)
   - Use meaningful names that describe the purpose
   - Avoid single-letter variable names except for loop counters

4. **Constants**
   - Use UPPER_SNAKE_CASE (e.g., `MAX_BANS_PER_TEAM`, `DEFAULT_PAGE_SIZE`)

5. **Packages**
   - Use lowercase with dots as separators (e.g., `org.willwin.draftolio.draft.service`)

### TypeScript/Angular Naming Conventions

1. **Components, Directives, Pipes, Services**
   - Use PascalCase for class names (e.g., `DraftBoardComponent`)
   - Use kebab-case for file names (e.g., `draft-board.component.ts`)
   - Suffix files based on their type:
     - Components: `.component.ts`
     - Services: `.service.ts`
     - Directives: `.directive.ts`
     - Pipes: `.pipe.ts`
     - Guards: `.guard.ts`
     - Interceptors: `.interceptor.ts`

2. **Methods and Properties**
   - Use camelCase (e.g., `getUserDrafts()`, `championName`)

3. **Interfaces and Types**
   - Use PascalCase (e.g., `Draft`, `UserProfile`)
   - Do not prefix with "I" (e.g., use `Draft` instead of `IDraft`)

4. **Enums**
   - Use PascalCase for enum names (e.g., `DraftStatus`, `Team`)
   - Use PascalCase for enum values (e.g., `InProgress`, `Completed`)

5. **CSS**
   - Use kebab-case for class names (e.g., `draft-board`, `champion-card`)
   - Use BEM (Block Element Modifier) methodology for structuring CSS classes

## Import Patterns

### Java Import Patterns

1. **Ordering**
   - Java standard library imports
   - Third-party library imports
   - Application imports

2. **Wildcards**
   - Avoid wildcard imports (e.g., `import java.util.*`)
   - Import specific classes explicitly

3. **Static Imports**
   - Use static imports for constants and static methods when it improves readability
   - Group static imports separately

### TypeScript Import Patterns

1. **Ordering**
   - Angular framework imports
   - Third-party library imports
   - Application imports
   - Relative imports

2. **Path Aliases**
   - Use path aliases for imports from app modules (e.g., `@core`, `@shared`)
   - Configure path aliases in `tsconfig.json`

3. **Barrel Files**
   - Use barrel files (index.ts) to simplify imports from directories

## Architectural Decisions

### Backend Architecture

1. **Hexagonal Architecture (Ports and Adapters)**
   - Core domain logic is isolated from external concerns
   - Dependencies point inward toward the domain
   - External systems are integrated through adapters

2. **CQRS (Command Query Responsibility Segregation)**
   - Separate command (write) and query (read) models
   - Commands change state but return no data
   - Queries return data but don't change state

3. **Event-Driven Architecture**
   - Services communicate through events
   - Kafka is used as the event bus
   - Events represent facts that have occurred (e.g., DraftCreated, ChampionPicked)

4. **Real-time Communication**
   - WebSockets for real-time draft updates
   - Server-Sent Events (SSE) for one-way updates to spectators
   - Redis pub/sub for distributing real-time events across instances

### Frontend Architecture

1. **Component-Based Architecture**
   - Standalone components with clear responsibilities
   - Reusable UI components in shared module
   - Feature-specific components in feature modules

2. **Signal-Based State Management**
   - Use Angular signals for local component state
   - Use computed signals for derived state
   - Avoid mutable state

3. **Container/Presentational Pattern**
   - Container components manage state and data fetching
   - Presentational components are pure and focus on UI
   - Clear separation of concerns

4. **Lazy Loading**
   - Feature modules are lazy-loaded
   - Improves initial load time
   - Enables code splitting

## Documentation Standards

1. **Code Documentation**
   - Use Javadoc for Java code
   - Use JSDoc for TypeScript code
   - Document public APIs thoroughly
   - Include examples where appropriate

2. **Architecture Documentation**
   - Use C4 model for architecture documentation
   - Maintain up-to-date diagrams
   - Document architectural decisions

3. **API Documentation**
   - Use OpenAPI (Swagger) for REST API documentation
   - Generate API documentation from code
   - Keep examples up-to-date

## Version Control Practices

1. **Branching Strategy**
   - Use GitFlow branching model
   - Main branches: `main`, `develop`
   - Feature branches: `feature/feature-name`
   - Release branches: `release/version`
   - Hotfix branches: `hotfix/issue-description`

2. **Commit Messages**
   - Follow conventional commits format
   - Format: `type(scope): description`
   - Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
   - Include issue number when applicable

3. **Pull Requests**
   - Require code reviews before merging
   - Include description of changes
   - Link to related issues
   - Include tests for new features or bug fixes
