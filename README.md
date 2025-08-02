# Draftolio

Draftolio is a League of Legends draft simulator that supports multiple draft modes, real-time collaboration, and spectating. It helps teams practice and refine their drafting strategies in a realistic environment.

## Draft Modes

Draftolio supports the following draft modes:

1. **Tournament Draft**: Standard draft mode used in competitive League of Legends.
2. **Fearless Draft**: A series of games (Bo3 or Bo5) where champions picked in previous games are considered banned for subsequent games.
3. **Full Fearless Draft**: Similar to Fearless Draft, but both previous picks AND bans are considered banned for subsequent games.

## Draft Process

All draft modes follow the same pick and ban order:

```
Ban Phase 1:
Blue Side Ban 1 → Red Side Ban 1 → Blue Side Ban 2 → Red Side Ban 2 → Blue Side Ban 3 → Red Side Ban 3

Pick Phase 1:
Blue Side Pick 1 → Red Side Pick 1 & 2 → Blue Side Pick 2 & 3 → Red Side Pick 3

Ban Phase 2:
Red Side Ban 4 → Blue Side Ban 4 → Red Side Ban 5 → Blue Side Ban 5

Pick Phase 2:
Red Side Pick 4 → Blue Side Pick 4 & 5 → Red Side Pick 5
```

## User Roles

Draftolio supports the following user roles:

1. **Captains**: Users who control the draft process. Each draft has exactly two captains:
   - One Blue Side Captain
   - One Red Side Captain

2. **Spectators**: Users who can view the draft in real-time but cannot make selections.

## Key Features

- **Draft Creation**: Create a new draft and invite another user to be the opposing captain.
- **Real-time Updates**: All users (captains and spectators) see draft updates in real-time.
- **Multiple Draft Modes**: Support for Tournament Draft, Fearless Draft, and Full Fearless Draft.
- **Spectator Mode**: Allow users to join and watch drafts without participating.
- **Draft History**: View completed drafts and their results.
- **Team Management**: Create and manage teams of League of Legends players, assign them to positions (Top, Jungle, Middle, Bottom, Support), and create drafts between teams.

## Spec-Driven Development Approach

This project follows a spec-driven development approach, which means that all features and changes are guided by detailed specifications before implementation begins. This approach ensures that:

1. **Requirements are clear and well-documented** before development starts
2. **Design decisions are deliberate and consistent** with the overall architecture
3. **Implementation follows established standards and patterns**
4. **Testing is focused on code quality and core functionality**
5. **Documentation is created alongside the code**

### Steering Files

The project is guided by a set of steering files that define the overall guidelines, standards, and practices:

- **[Product Overview](docs/steering/product.md)**: Defines the product's purpose, target users, key features, and business objectives
- **[Technology Stack](docs/steering/tech.md)**: Documents the chosen frameworks, libraries, development tools, and technical constraints
- **[Project Structure](docs/steering/structure.md)**: Outlines file organization, naming conventions, import patterns, and architectural decisions
- **[API Standards](docs/steering/api-standards.md)**: Defines REST conventions, error response formats, authentication flows, and versioning strategies
- **[Testing Approach](docs/steering/testing-standards.md)**: Establishes minimal code testing standards, focusing on unit tests and basic integration tests
- **[Code Style](docs/steering/code-conventions.md)**: Specifies naming patterns, file organization, import ordering, and architectural decisions
- **[Security Guidelines](docs/steering/security-policies.md)**: Documents authentication requirements, data validation rules, input sanitization standards, and vulnerability prevention measures
- **[Deployment Process](docs/steering/deployment-workflow.md)**: Outlines build procedures, environment configurations, deployment steps, and rollback strategies
- **[AI Hooks](docs/steering/ai-hooks.md)**: Defines tasks to perform on pre-determined AI actions to ensure AI assistants follow the spec-driven development pattern

### Feature Specifications

Each feature in the project is defined by a detailed specification before implementation begins. The feature specification template can be found at [docs/templates/feature-spec-template.md](docs/templates/feature-spec-template.md).

Feature specifications include:
- Overview and business objectives
- User stories and requirements
- Design details (UX and technical)
- Implementation plan
- Testing strategy
- Rollout plan
- Success metrics

### Development Workflow

The development workflow follows these steps:

1. **Specification**: Create a detailed feature specification using the template
2. **Review**: Review and approve the specification with stakeholders
3. **Implementation**: Implement the feature according to the specification
4. **Testing**: Test the feature against the requirements in the specification
5. **Deployment**: Deploy the feature following the deployment workflow
6. **Monitoring**: Monitor the feature in production and gather feedback

## Project Structure

```
draftolio/
├── docs/                           # Documentation
│   ├── specs/                      # Feature specifications
│   ├── steering/                   # Steering files
│   └── templates/                  # Templates for documentation
├── src/
│   ├── main/
│   │   ├── java/org/willwin/draftolio/
│   │   │   ├── config/             # Configuration classes
│   │   │   ├── domain/             # Domain model
│   │   │   │   ├── entity/         # JPA entities
│   │   │   │   ├── repository/     # Spring Data repositories
│   │   │   │   ├── service/        # Domain services
│   │   │   │   └── event/          # Domain events
│   │   │   ├── api/                # API layer
│   │   │   │   ├── controller/     # REST controllers
│   │   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   ├── mapper/         # DTO-Entity mappers
│   │   │   │   └── exception/      # API exceptions
│   │   │   ├── infrastructure/     # External services integration
│   │   │   │   ├── client/         # External API clients
│   │   │   │   ├── messaging/      # WebSocket handlers
│   │   │   │   └── storage/        # Storage clients
│   │   │   └── util/               # Utility classes
│   │   └── resources/              # Application resources
│   └── test/                       # Test code
├── .github/                        # GitHub configuration
├── .mvn/                           # Maven wrapper
├── pom.xml                         # Maven configuration
└── README.md                       # This file
```

## Getting Started

### Prerequisites

- Java 24
- Maven 3.9+
- Docker and Docker Compose

### Setup

1. Clone the repository:
   ```
   git clone https://github.com/willwin/draftolio.git
   cd draftolio
   ```

2. Build the project:
   ```
   ./mvnw clean install
   ```

3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

### Development

1. Create a feature specification in `docs/specs/` using the template
2. Get the specification reviewed and approved
3. Create a feature branch from `develop`:
   ```
   git checkout -b feature/feature-name develop
   ```
4. Implement the feature according to the specification
5. Write tests for the feature
6. Submit a pull request to merge the feature into `develop`

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Angular](https://angular.dev/)
- [PrimeNG](https://primeng.org/)
- [TailwindCSS](https://tailwindcss.com/)
- [League of Legends](https://www.leagueoflegends.com/)
