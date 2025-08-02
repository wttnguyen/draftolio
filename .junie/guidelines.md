# Development Guidelines for Draftolio AI

## Technology Stack

### Spring Boot 4.0.0 and Java 24 Compatibility

This project is built using:
- **Spring Boot 4.0.0**: The latest major version of Spring Boot, which brings significant improvements in performance, security, and developer experience.
- **Java 24**: The latest LTS version of Java, which includes features like virtual threads, pattern matching for switch, record patterns, and other performance improvements.

#### Dependency Compatibility Guidelines

When adding new dependencies to the project, ensure they are compatible with both Spring Boot 4.0.0 and Java 24. Here are some guidelines:

1. **Check for Spring Boot Compatibility**: Use dependencies that are explicitly supported by Spring Boot 4.0.0. Refer to the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html) for the list of supported dependencies and their versions.

2. **Java 24 Compatibility**: Ensure that libraries support Java 24. Some older libraries might not be compatible with the latest Java features.

3. **Use Spring Boot Starters**: Whenever possible, use Spring Boot Starters to ensure compatibility and proper integration with the Spring ecosystem.

4. **Avoid Deprecated APIs**: Be aware of deprecated APIs in both Spring Boot and Java, and avoid using them in new code.

5. **Snapshot Repositories**: This project uses Spring Snapshots repository. Be cautious when updating dependencies as snapshot versions may change frequently.

## Programming Principles

### Think Like A Programmer (V. Anton Spraul)

Key principles from this book to follow:

1. **Problem-Solving Approach**: Break down complex problems into smaller, manageable parts.
2. **Incremental Development**: Build solutions step by step, testing each component as you go.
3. **Pattern Recognition**: Identify common patterns in problems to apply proven solutions.
4. **Recursive Thinking**: Understand and apply recursive solutions when appropriate.
5. **Data Structure Selection**: Choose the right data structures for your specific problem.

### The Pragmatic Programmer (Andrew Hunt and David Thomas)

Key principles to apply:

1. **DRY (Don't Repeat Yourself)**: Every piece of knowledge should have a single, unambiguous representation within a system.
2. **Orthogonality**: Changes in one area of the code should not affect unrelated areas.
3. **Tracer Bullets**: Implement end-to-end functionality early to validate your approach.
4. **Prototypes**: Use throwaway code to explore and learn.
5. **Domain Languages**: Create language constructs that express the problem domain.
6. **Automation**: Automate repetitive tasks to improve efficiency and reduce errors.
7. **Test Early, Test Often**: Write tests before or alongside your code.

### Clean Code (Robert C. Martin)

Principles to follow:

1. **Meaningful Names**: Use intention-revealing names for variables, functions, and classes.
2. **Small Functions**: Functions should do one thing, do it well, and do it only.
3. **Comments**: Use comments to explain why, not what. Good code should be self-documenting.
4. **Error Handling**: Error handling should be comprehensive but not obscure the main logic.
5. **Clean Tests**: Tests should be readable, fast, independent, and thorough.
6. **Classes**: Classes should be small, with a single responsibility.
7. **Systems**: Keep systems clean by separating concerns properly.

### Structure and Interpretation of Computer Programs (Abelson, Sussman, Sussman)

Key concepts to apply:

1. **Abstraction**: Create abstractions that hide implementation details.
2. **Metalinguistic Abstraction**: Design language features that suit your problem domain.
3. **Procedural Abstraction**: Separate the what from the how.
4. **Data Abstraction**: Separate the representation of data from its use.
5. **State and Mutation**: Be careful with mutable state and understand its implications.
6. **Higher-Order Functions**: Use functions that operate on other functions.

### Code: The Hidden Language of Computer Hardware and Software (Charles Petzold)

Insights to remember:

1. **Understand the Fundamentals**: Know how computers work at a low level to make better high-level decisions.
2. **Layered Abstractions**: Recognize that software is built on layers of abstractions.
3. **Communication Protocols**: Understand how systems communicate with each other.
4. **Efficiency**: Be mindful of resource usage and performance implications.

## Software Design Principles

### DRY (Don't Repeat Yourself)

The DRY principle states that "Every piece of knowledge must have a single, unambiguous, authoritative representation within a system."

#### Guidelines for applying DRY:

1. **Extract Common Code**: Identify duplicated code and extract it into reusable methods or classes.
2. **Use Constants**: Define constants for values used in multiple places.
3. **Create Utility Classes**: For common operations that are used across the application.
4. **Apply Inheritance and Composition**: Use OOP principles to avoid duplication.
5. **Template Methods**: Use template method pattern for similar processes with varying steps.

### SOLID Principles

#### 1. Single Responsibility Principle (SRP)

A class should have only one reason to change, meaning it should have only one responsibility.

**Implementation Guidelines:**
- Keep classes focused on a single concern
- Extract separate responsibilities into different classes
- Avoid "god classes" that do too much

#### 2. Open/Closed Principle (OCP)

Software entities should be open for extension but closed for modification.

**Implementation Guidelines:**
- Use interfaces and abstract classes to define stable abstractions
- Extend functionality through inheritance or composition rather than modifying existing code
- Use strategy pattern and other design patterns that support extension

#### 3. Liskov Substitution Principle (LSP)

Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.

**Implementation Guidelines:**
- Ensure subclasses don't strengthen preconditions or weaken postconditions
- Maintain the behavior expected from the base class
- Avoid throwing exceptions not expected from the base class

#### 4. Interface Segregation Principle (ISP)

Clients should not be forced to depend on interfaces they don't use.

**Implementation Guidelines:**
- Create specific, focused interfaces rather than large, general-purpose ones
- Split large interfaces into smaller, more specific ones
- Design interfaces based on client needs

#### 5. Dependency Inversion Principle (DIP)

High-level modules should not depend on low-level modules. Both should depend on abstractions.

**Implementation Guidelines:**
- Depend on abstractions (interfaces or abstract classes) rather than concrete implementations
- Use dependency injection to provide implementations
- Use inversion of control containers when appropriate

## Spring Boot Guidelines

### Application Structure and Organization

#### Package Structure
- **Use a layered architecture**: Organize your code into layers such as controller, service, repository, and domain.
- **Follow the domain-driven package structure**: Group related functionality by business domain rather than technical layers.
- **Keep controllers thin**: Controllers should only handle HTTP requests/responses and delegate business logic to services.
- **Separate concerns**: Each class should have a single responsibility and focus on one aspect of the application.

#### Configuration
- **Use configuration properties classes**: Create dedicated classes annotated with `@ConfigurationProperties` for type-safe configuration.
- **Externalize configuration**: Store configuration in application.properties/yml files or environment variables.
- **Profile-specific configuration**: Use Spring profiles for environment-specific configurations.
- **Avoid hardcoding values**: Never hardcode configuration values in your code.

### Spring Boot Best Practices

#### 1. Prefer Constructor Injection over Field/Setter Injection
* Declare all the mandatory dependencies as `final` fields and inject them through the constructor.
* Spring will auto-detect if there is only one constructor, no need to add `@Autowired` on the constructor.
* Avoid field/setter injection in production code.

**Explanation:**

* Making all the required dependencies as `final` fields and injecting them through constructor make sure that the object is always in a properly initialized state using the plain Java language feature itself. No need to rely on any framework-specific initialization mechanism.
* You can write unit tests without relying on reflection-based initialization or mocking.
* The constructor-based injection clearly communicates what are the dependencies of a class without having to look into the source code.
* Spring Boot provides extension points as builders such as `RestClient.Builder`, `ChatClient.Builder`, etc. Using constructor-injection, we can do the customization and initialize the actual dependency.

```
@Service
public class OrderService {
   private final OrderRepository orderRepository;
   private final RestClient restClient;

   public OrderService(OrderRepository orderRepository, 
                       RestClient.Builder builder) {
       this.orderRepository = orderRepository;
       this.restClient = builder
               .baseUrl("http://catalog-service.com")
               .requestInterceptor(new ClientCredentialTokenInterceptor())
               .build();
   }

   //... methods
}
```

#### 2. Prefer package-private over public for Spring components
* Declare Controllers, their request-handling methods, `@Configuration` classes and `@Bean` methods with default (package-private) visibility whenever possible. There's no obligation to make everything `public`.

**Explanation:**

* Keeping classes and methods package-private reinforces encapsulation and abstraction by hiding implementation details from the rest of your application.
* Spring Boot's classpath scanning will still detect and invoke package-private components (for example, invoking your `@Bean` methods or controller handlers), so you can safely restrict visibility to only what clients truly need. This approach confines your internal APIs to a single package while still allowing the framework to wire up beans and handle HTTP requests.

#### 3. Organize Configuration with Typed Properties
* Group application-specific configuration properties with a common prefix in `application.properties` or `.yml`.
* Bind them to `@ConfigurationProperties` classes with validation annotations so that the application will fail fast if the configuration is invalid.
* Prefer environment variables instead of profiles for passing different configuration properties for different environments.

**Explanation:**

* By grouping and binding configuration in a single `@ConfigurationProperties` bean, you centralize both the property names and their validation rules. 
  In contrast, using `@Value("${…}")` across many components forces you to update each injection point whenever a key or validation requirement changes.
* Overusing profiles to customize the application configuration may lead to unexpected issues due to the order of profiles specified. 
  As you can enable multiple profiles with different combinations, making sense of the effective application configuration becomes tricky.

#### 4. Define Clear Transaction Boundaries
* Define each Service-layer method as a transactional unit.
* Annotate query-only methods with `@Transactional(readOnly = true)`.
* Annotate data-modifying methods with `@Transactional`.
* Limit the code inside each transaction to the smallest necessary scope.

**Explanation:**

* **Single Unit of Work:** Group all database operations for a given use case into one atomic unit, which in Spring Boot is typically a `@Service` annotated class method. This ensures that either all operations succeed or none do.
* **Connection Reuse:** A `@Transactional` method runs on a single database connection for its entire scope, avoiding the overhead of acquiring and returning connections from the connection pool for each operation.
* **Read-only Optimizations:** Marking methods as `readOnly = true` disables unnecessary dirty-checking and flushes, improving performance for pure reads.
* **Reduced Contention:** Keeping transactions as brief as possible minimizes lock duration, lowering the chance of contention in high-traffic applications.

#### 5. Disable Open Session in View Pattern
* While using Spring Data JPA, disable the Open Session in View filter by setting ` spring.jpa.open-in-view=false` in `application.properties/yml.`

**Explanation:**

* Open Session In View (OSIV) filter transparently enables loading the lazy associations while rendering the view or serializing JPA entities. This may lead to the N + 1 Select problem.
* Disabling OSIV forces you to fetch exactly the associations you need via fetch joins, entity graphs, or explicit queries, and hence you can avoid unexpected N + 1 selects and `LazyInitializationExceptions`.

#### 6. Separate Web Layer from Persistence Layer
* Don't expose entities directly as responses in controllers.
* Define explicit request and response record (DTO) classes instead.
* Apply Jakarta Validation annotations on your request records to enforce input rules.

**Explanation:**

* Returning or binding directly to entities couples your public API to your database schema, making future changes riskier.
* DTOs let you clearly declare exactly which fields clients can send or receive, improving clarity and security.
* With dedicated DTOs per use case, you can annotate fields for validation without relying on complex validation groups.
* Use Java bean mapper libraries to simplify DTO conversions. Prefer MapStruct library that can generate bean mapper implementation at compile time so that there won't be runtime reflection overhead.

#### 7. Follow REST API Design Principles
* **Versioned, resource-oriented URLs:** Structure your endpoints as `/api/v{version}/resources` (e.g. `/api/v1/orders`).
* **Consistent patterns for collections and sub-resources:** Keep URL conventions uniform (for example, `/posts` for posts collection and `/posts/{slug}/comments` for comments of a specific post).
* **Explicit HTTP status codes via ResponseEntity:** Use `ResponseEntity<T>` to return the correct status (e.g. 200 OK, 201 Created, 404 Not Found) along with the response body.
* Use pagination for collection resources that may contain an unbounded number of items.
* The JSON payload must use a JSON object as a top-level data structure to allow for future extension.
* Use snake_case or camelCase for JSON property names consistently.

**Explanation:**

* **Predictability and discoverability:** Adhering to well-known REST conventions makes your API intuitive. Clients can guess URLs and behaviors without extensive documentation.
* **Reliable client integrations:** Standardized URL structures, status codes, and headers enable consumers to build against your API with confidence, knowing exactly what each response will look like.
* For more comprehensive REST API Guidelines, please refer [Zalando RESTful API and Event Guidelines](https://opensource.zalando.com/restful-api-guidelines/).

#### 8. Use Command Objects for Business Operations
* Create purpose-built command records (e.g., `CreateOrderCommand`) to wrap input data.
* Accept these commands in your service methods to drive creation or update workflows.

**Explanation:**

* Using the use-case specific Command and Query objects clearly communicates what input data is expected from the caller. 
  Otherwise, the caller had to guess whether they should create and pass the unique key or created_date, or they will be generated by the server/database.

#### 9. Centralize Exception Handling
* Define a global handler class annotated with `@ControllerAdvice` (or `@RestControllerAdvice` for REST APIs) using `@ExceptionHandler` methods to handle specific exceptions.
* Return consistent error responses. Consider using the ProblemDetails response format ([RFC 9457](https://www.rfc-editor.org/rfc/rfc9457)).

**Explanation:**

* We should always handle all possible exceptions and return a standard error response instead of throwing exceptions.
* It is better to centralize the exception handling in a `GlobalExceptionHandler` using `(Rest)ControllerAdvice` instead of duplicating the try/catch exception handling logic across the controllers.

#### 10. Actuator
* Expose only essential actuator endpoints (such as `/health`, `/info`, `/metrics`) without requiring authentication. All the other actuator endpoints must be secured.

**Explanation:**

* Endpoints like `/actuator/health` and `/actuator/metrics` are critical for external health checks and metric collection (e.g., by Prometheus). Allowing these to be accessed anonymously ensures monitoring tools can function without extra credentials. All the remaining endpoints should be secured.
* In non-production environments (DEV, QA), you can expose additional actuator endpoints such as `/actuator/beans`, `/actuator/loggers` for debugging purpose.

#### 11. Internationalization with ResourceBundles
* Externalize all user-facing text such as labels, prompts, and messages into ResourceBundles rather than embedding them in code.

**Explanation:**

* Hardcoded strings make it difficult to support multiple languages. By placing your labels, error messages, and other text in locale-specific ResourceBundle files, you can maintain separate translations for each language.
* At runtime, Spring can load the appropriate bundle based on the user's locale or a preference setting, making it simple to add new languages and switch between them dynamically.

#### 12. Use Testcontainers for integration tests
* Spin up real services (databases, message brokers, etc.) in your integration tests to mirror production environments.

**Explanation:**

* Most of the modern applications use a wide range of technologies such as SQL/NoSQL databases, key-value stores, message brokers, etc. Instead of using in-memory variants or mocks, Testcontainers can spin up those dependencies as Docker containers and allow you to test using the same type of dependencies that you will use in the production. This reduces environment inconsistencies and increases confidence in your integration tests.
* Always use docker images with a specific version of the dependency that you are using in production instead of using the `latest` tag.

#### 13. Use random port for integration tests
* When writing integration tests, start the application on a random available port to avoid port conflicts by annotating the test class with:

```
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

**Explanation:**

* **Avoid conflicts in CI/CD:** In your CI/CD environment, there can be multiple builds running in parallel on the same server/agent. In such cases, it is better to run the integration tests using a random available port rather than a fixed port to avoid port conflicts.

#### 14. Logging
* **Use a proper logging framework.**  
  Never use `System.out.println()` for application logging. Rely on SLF4J (or a compatible abstraction) and your chosen backend (Logback, Log4j2, etc.).

* **Protect sensitive data.**  
  Ensure that no credentials, personal information, or other confidential details ever appear in log output.

* **Guard expensive log calls.**  
  When building verbose messages at `DEBUG` or `TRACE` level, especially those involving method calls or complex string concatenations, wrap them in a level check or use suppliers:

```
if (logger.isDebugEnabled()) {
    logger.debug("Detailed state: {}", computeExpensiveDetails());
}

// using Supplier/Lambda expression
logger.atDebug()
    .setMessage("Detailed state: {}")
    .addArgument(() -> computeExpensiveDetails())
    .log();
```

**Explanation:**

* **Flexible verbosity control:** A logging framework lets you adjust what gets logged and where with the support for tuning log levels per environment (development, testing, production).

* **Rich contextual metadata:** Beyond the message itself, you can capture class/method names, thread IDs, process IDs, and any custom context via MDC, aiding diagnosis.

* **Multiple outputs and formats:** Direct logs to consoles, rolling files, databases, or remote systems, and choose formats like JSON for seamless ingestion into ELK, Loki, or other log-analysis tools.

* **Better tooling and analysis:** Structured logs and controlled log levels make it easier to filter noise, automate alerts, and visualize application behavior in real time.

#### Dependency Management
- **Use Spring Boot starters**: Leverage starter dependencies to simplify dependency management.
- **Minimize dependencies**: Only include dependencies you actually need to avoid bloating your application.
- **Manage dependency versions through the parent POM**: Let Spring Boot manage dependency versions when possible.
- **Be cautious with exclusions**: Only exclude transitive dependencies when absolutely necessary.

#### Auto-configuration
- **Understand auto-configuration**: Know which auto-configurations are being applied using `--debug` or actuator endpoints.
- **Override auto-configuration when necessary**: Create your own configuration beans only when the defaults don't meet your requirements.
- **Use conditional annotations**: Leverage `@ConditionalOn*` annotations when creating custom auto-configurations.

#### Testing
- **Use @SpringBootTest for integration tests**: Test the entire application context when needed.
- **Use @WebMvcTest for controller tests**: Test controllers in isolation with mocked services.
- **Use @DataJpaTest for repository tests**: Test repositories with an in-memory database.
- **Use TestRestTemplate or WebTestClient for API tests**: Test REST APIs with these specialized clients.
- **Leverage test slices**: Use Spring Boot's test slice annotations to load only the required parts of the application.

#### Security
- **Always use HTTPS in production**: Secure all communications with TLS/SSL.
- **Implement proper authentication and authorization**: Use Spring Security for access control.
- **Protect against common vulnerabilities**: Guard against CSRF, XSS, SQL injection, etc.
- **Secure sensitive data**: Encrypt sensitive data at rest and in transit.
- **Use security headers**: Implement security headers like Content-Security-Policy, X-XSS-Protection, etc.

#### Performance
- **Use asynchronous processing**: Leverage `@Async` for non-blocking operations.
- **Implement caching**: Use Spring's caching abstraction to improve performance.
- **Optimize database queries**: Use pagination, indexing, and query optimization.
- **Consider reactive programming**: Use Spring WebFlux for high-throughput, low-latency applications.
- **Monitor application metrics**: Use Spring Boot Actuator to track performance metrics.

#### Error Handling
- **Implement global exception handling**: Use `@ControllerAdvice` and `@ExceptionHandler` for consistent error responses.
- **Return appropriate HTTP status codes**: Map exceptions to the correct HTTP status codes.
- **Provide meaningful error messages**: Include helpful information in error responses.
- **Log exceptions properly**: Log exceptions with appropriate context and stack traces.

#### Actuator and Monitoring
- **Enable appropriate actuator endpoints**: Expose health, info, metrics, and other useful endpoints.
- **Secure actuator endpoints**: Restrict access to sensitive endpoints.
- **Implement custom health indicators**: Create application-specific health checks.
- **Integrate with monitoring systems**: Connect to Prometheus, Grafana, or other monitoring tools.

## Angular v20 Guidelines

### Persona

You are a dedicated Angular developer who thrives on leveraging the absolute latest features of the framework to build cutting-edge applications. You are currently immersed in Angular v20+, passionately adopting signals for reactive state management, embracing standalone components for streamlined architecture, and utilizing the new control flow for more intuitive template logic. Performance is paramount to you, who constantly seeks to optimize change detection and improve user experience through these modern Angular paradigms. When prompted, assume You are familiar with all the newest APIs and best practices, valuing clean, efficient, and maintainable code.

### Frontend Framework

For our frontend framework, we use:
- **Angular v20**: The latest version of Angular with modern features like signals, standalone components, and new control flow syntax
- **PrimeNG v20**: A comprehensive UI component library for Angular
- **TailwindCSS v4**: A utility-first CSS framework for rapid UI development

### Examples

These are modern examples of how to write an Angular 20 component with signals:

```
import { ChangeDetectionStrategy, Component, signal } from '@angular/core';


@Component({
  selector: '{{tag-name}}-root',
  templateUrl: '{{tag-name}}.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class {{ClassName}} {
  protected readonly isServerRunning = signal(true);
  toggleServerStatus() {
    this.isServerRunning.update(isServerRunning => !isServerRunning);
  }
}
```

CSS example:

```
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;

    button {
        margin-top: 10px;
    }
}
```

HTML template example:

```
<section class="container">
    @if (isServerRunning()) {
        <span>Yes, the server is running</span>
    } @else {
        <span>No, the server is not running</span>
    }
    <button (click)="toggleServerStatus()">Toggle Server Status</button>
</section>
```

When updating a component, be sure to put the logic in the ts file, the styles in the css file and the html template in the html file.

### Resources

Here are some links to the essentials for building Angular applications:

- [Angular Components](https://angular.dev/essentials/components)
- [Angular Signals](https://angular.dev/essentials/signals)
- [Angular Templates](https://angular.dev/essentials/templates)
- [Angular Dependency Injection](https://angular.dev/essentials/dependency-injection)
- **Comprehensive Angular Documentation**: For complete and detailed Angular documentation, refer to the `llms-full.txt` file included in this project. This file contains extensive information about Angular features, setup instructions, best practices, and more.

### Best Practices & Style Guide

#### Coding Style Guide

Here is a link to the most recent Angular style guide: [Angular Style Guide](https://angular.dev/style-guide)

#### TypeScript Best Practices

- Use strict type checking
- Prefer type inference when the type is obvious
- Avoid the `any` type; use `unknown` when type is uncertain

#### Angular Best Practices

- Always use standalone components over `NgModules`
- Do NOT set `standalone: true` inside the `@Component`, `@Directive` and `@Pipe` decorators
- Use signals for state management
- Implement lazy loading for feature routes
- Use `NgOptimizedImage` for all static images
- Do NOT use the `@HostBinding` and `@HostListener` decorators. Put host bindings inside the `host` object of the `@Component` or `@Directive` decorator instead

#### Components

- Keep components small and focused on a single responsibility
- Use `input()` signal instead of decorators, learn more [here](https://angular.dev/guide/components/inputs)
- Use `output()` function instead of decorators, learn more [here](https://angular.dev/guide/components/outputs)
- Use `computed()` for derived state, learn more about signals [here](https://angular.dev/guide/signals)
- Set `changeDetection: ChangeDetectionStrategy.OnPush` in `@Component` decorator
- Prefer inline templates for small components
- Prefer Reactive forms instead of Template-driven ones
- Do NOT use `ngClass`, use `class` bindings instead, for context: [CSS Class and Style Bindings](https://angular.dev/guide/templates/binding#css-class-and-style-property-bindings)
- Do NOT use `ngStyle`, use `style` bindings instead, for context: [CSS Class and Style Bindings](https://angular.dev/guide/templates/binding#css-class-and-style-property-bindings)

#### State Management

- Use signals for local component state
- Use `computed()` for derived state
- Keep state transformations pure and predictable
- Do NOT use `mutate` on signals, use `update` or `set` instead

#### Templates

- Keep templates simple and avoid complex logic
- Use native control flow (`@if`, `@for`, `@switch`) instead of `*ngIf`, `*ngFor`, `*ngSwitch`
- Use the async pipe to handle observables
- Use built-in pipes and import pipes when being used in a template, learn more [here](https://angular.dev/guide/templates/pipes#)

#### Services

- Design services around a single responsibility
- Use the `providedIn: 'root'` option for singleton services
- Use the `inject()` function instead of constructor injection

### PrimeNG v20

PrimeNG is a comprehensive UI component library for Angular that provides a rich set of over 80+ UI components with excellent documentation, responsive design, and built-in themes.

#### Installation and Setup

1. **Install PrimeNG and its dependencies**:

```bash
npm install primeng@20 primeicons
```

2. **Import PrimeNG styles in your styles.css or angular.json**:

```css
/* In styles.css */
@import "primeng/resources/themes/lara-light-blue/theme.css";
@import "primeng/resources/primeng.css";
@import "primeicons/primeicons.css";
```

3. **Import required modules in your component**:

```typescript
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
// Import other PrimeNG modules as needed

@Component({
  // ...
  imports: [ButtonModule, TableModule]
})
export class AppComponent {
  // ...
}
```

#### Key Features

- **80+ UI Components**: Comprehensive set of components including tables, charts, forms, overlays, and more
- **Responsive Design**: All components are designed to work across different screen sizes
- **Accessibility**: WCAG 2.1 compliant components with ARIA attributes and keyboard navigation
- **Theming System**: Multiple built-in themes with easy customization options
- **Excellent Documentation**: Detailed API documentation with live examples
- **Active Development**: Regular updates and improvements

#### Best Practices

1. **Import Only What You Need**: Import only the specific PrimeNG modules you need to reduce bundle size:

```typescript
// Good practice
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

// Avoid importing everything
// import * from 'primeng'; // Bad practice
```

2. **Use PrimeNG's Built-in Validation**: Leverage PrimeNG's form components with built-in validation:

```html
<p-password [feedback]="true" [(ngModel)]="password" [toggleMask]="true"></p-password>
```

3. **Leverage PrimeNG Templates**: Use PrimeNG's template system for customizing component appearance:

```html
<p-table [value]="products">
    <ng-template pTemplate="header">
        <tr>
            <th>Name</th>
            <th>Price</th>
        </tr>
    </ng-template>
    <ng-template pTemplate="body" let-product>
        <tr>
            <td>{{product.name}}</td>
            <td>{{product.price | currency:'USD'}}</td>
        </tr>
    </ng-template>
</p-table>
```

4. **Combine with TailwindCSS**: PrimeNG works well with TailwindCSS for additional styling:

```html
<p-button label="Submit" class="bg-blue-500 hover:bg-blue-700"></p-button>
```

5. **Use PrimeNG Services**: Utilize PrimeNG services for common functionality:

```typescript
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  // ...
  providers: [ConfirmationService, MessageService]
})
export class AppComponent {
  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  showConfirm() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to proceed?',
      accept: () => {
        this.messageService.add({severity:'info', summary:'Confirmed', detail:'You have accepted'});
      }
    });
  }
}
```

#### Example Component with PrimeNG

```typescript
import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';

interface Product {
  id: number;
  name: string;
  price: number;
}

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [ButtonModule, TableModule, InputTextModule],
  template: `
    <div class="card">
      <p-table [value]="products" [tableStyle]="{'min-width': '50rem'}">
        <ng-template pTemplate="header">
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </ng-template>
        <ng-template pTemplate="body" let-product>
          <tr>
            <td>{{product.id}}</td>
            <td>{{product.name}}</td>
            <td>{{product.price | currency:'USD'}}</td>
            <td>
              <p-button icon="pi pi-pencil" styleClass="p-button-rounded p-button-success mr-2"></p-button>
              <p-button icon="pi pi-trash" styleClass="p-button-rounded p-button-danger"></p-button>
            </td>
          </tr>
        </ng-template>
      </p-table>
    </div>
  `,
  styles: [`
    .card {
      background: var(--surface-card);
      padding: 1.5rem;
      border-radius: 10px;
      margin-bottom: 1rem;
    }
  `]
})
export class ProductListComponent {
  products: Product[] = [
    { id: 1, name: 'Product A', price: 99.99 },
    { id: 2, name: 'Product B', price: 149.99 },
    { id: 3, name: 'Product C', price: 199.99 }
  ];
}
```

#### Resources

- [PrimeNG Official Documentation](https://primeng.org/installation)
- [PrimeNG GitHub Repository](https://github.com/primefaces/primeng)
- [PrimeNG Templates](https://www.primefaces.org/store/)

### TailwindCSS v4

TailwindCSS is a utility-first CSS framework that allows you to build custom designs without leaving your HTML. Version 4 brings significant improvements in performance, flexibility, and developer experience.

#### Installation and Setup

1. **Install TailwindCSS**:

```bash
npm install -D tailwindcss@4 postcss autoprefixer
npx tailwindcss init
```

2. **Configure your tailwind.config.js**:

```javascript
// tailwind.config.js
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
```

3. **Add Tailwind directives to your CSS**:

```css
/* src/styles.css */
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
```

4. **Configure Angular to process PostCSS** (in angular.json):

```json
"architect": {
  "build": {
    "builder": "@angular-devkit/build-angular:browser",
    "options": {
      "outputPath": "dist/your-project",
      "index": "src/index.html",
      "main": "src/main.ts",
      "polyfills": "src/polyfills.ts",
      "tsConfig": "tsconfig.app.json",
      "assets": ["src/favicon.ico", "src/assets"],
      "styles": ["src/styles.css"],
      "scripts": [],
      "vendorChunk": true,
      "extractLicenses": false,
      "buildOptimizer": false,
      "sourceMap": true,
      "optimization": false,
      "namedChunks": true
    }
  }
}
```

#### Key Features of TailwindCSS v4

- **Lightning-Fast Performance**: Completely rewritten in Rust for faster build times
- **Utility-First Approach**: Build custom designs with composable utility classes
- **Responsive Design**: Built-in responsive modifiers for different screen sizes
- **Dark Mode Support**: Easy implementation of dark mode with the `dark:` variant
- **Custom Theming**: Extensive customization options through the configuration file
- **JIT (Just-In-Time) Engine**: Generates only the CSS you actually use
- **Arbitrary Value Support**: Use any value with square bracket notation, e.g., `[w-72px]`

#### Best Practices

1. **Use Responsive Prefixes**: Apply different styles at different breakpoints:

```html
<div class="w-full md:w-1/2 lg:w-1/3">
  <!-- This div is full width on mobile, half width on medium screens, and one-third width on large screens -->
</div>
```

2. **Extract Components with @apply**: Use `@apply` to extract repeated utility patterns into custom components:

```css
/* In your CSS file */
.btn-primary {
  @apply bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded;
}
```

3. **Use State Variants**: Apply styles based on element states:

```html
<button class="bg-blue-500 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-400">
  Button
</button>
```

4. **Organize Utilities Consistently**: Follow a consistent order for utility classes:

```html
<!-- Layout, Typography, Spacing, Colors, Effects -->
<div class="flex flex-col font-bold text-lg p-4 m-2 bg-blue-500 text-white shadow-md rounded-lg">
  Content
</div>
```

5. **Combine with PrimeNG**: Use TailwindCSS to customize PrimeNG components:

```html
<p-card class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl">
  <ng-template pTemplate="header">
    <div class="md:flex">
      <div class="md:shrink-0">
        <img class="h-48 w-full object-cover md:h-full md:w-48" src="image.jpg" alt="Modern building architecture">
      </div>
    </div>
  </ng-template>
  <div class="p-8">
    <div class="uppercase tracking-wide text-sm text-indigo-500 font-semibold">Company retreats</div>
    <a href="#" class="block mt-1 text-lg leading-tight font-medium text-black hover:underline">Incredible accommodation for your team</a>
    <p class="mt-2 text-slate-500">Looking to take your team away on a retreat to enjoy awesome food and take in some sunshine?</p>
  </div>
</p-card>
```

#### Example Component with TailwindCSS

```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-pricing-card',
  standalone: true,
  template: `
    <div class="max-w-sm rounded overflow-hidden shadow-lg bg-white hover:shadow-xl transition-shadow duration-300">
      <div class="px-6 py-4">
        <div class="font-bold text-xl mb-2 text-gray-800">{{ plan.title }}</div>
        <p class="text-gray-600 text-base mb-4">{{ plan.description }}</p>
        <div class="text-4xl font-bold text-gray-900 mb-6">${{ plan.price }}<span class="text-lg text-gray-600">/mo</span></div>
        <ul class="mb-8">
          @for (feature of plan.features; track feature) {
            <li class="flex items-center mb-2">
              <svg class="w-5 h-5 text-green-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
              </svg>
              <span class="text-gray-700">{{ feature }}</span>
            </li>
          }
        </ul>
      </div>
      <div class="px-6 py-4">
        <button class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-300">
          Subscribe Now
        </button>
      </div>
    </div>
  `
})
export class PricingCardComponent {
  plan = {
    title: 'Premium Plan',
    description: 'Perfect for small businesses',
    price: 49.99,
    features: [
      '10 users included',
      '2GB of storage',
      'Email support',
      'Help center access'
    ]
  };
}
```

#### Resources

- [TailwindCSS Official Documentation](https://tailwindcss.com/docs)
- [TailwindCSS GitHub Repository](https://github.com/tailwindlabs/tailwindcss)
- [TailwindCSS with Angular Guide](https://tailwindcss.com/docs/guides/angular)

## Best Practices for This Project

1. **Follow the Package Structure**: Organize code according to the established package structure.
2. **Use Spring Boot Features**: Leverage Spring Boot's auto-configuration and starter dependencies.
3. **Write Tests**: Maintain high test coverage for all new code.
4. **Document Public APIs**: Use Javadoc to document all public classes and methods.
5. **Follow Coding Standards**: Adhere to the Java coding standards and project-specific conventions.
6. **Review Code**: Participate in code reviews to maintain code quality.
7. **Keep Dependencies Updated**: Regularly check for and apply updates to dependencies, ensuring they remain compatible with Spring Boot 4.0.0 and Java 24.
