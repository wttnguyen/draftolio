# Draftolio Development Guidelines

This document provides guidelines for development on the Draftolio project. It covers build/configuration instructions, testing information, and additional
development principles.

## Build/Configuration Instructions

### Lombok Usage

Lombok is used extensively in this project to reduce boilerplate code. Follow these guidelines when using Lombok:

- Use `@Getter` for class fields that need accessor methods
- Use `@RequiredArgsConstructor` for dependency injection via constructor
- Use `@Builder` for classes that benefit from the builder pattern
- Always apply `@NonNull` to parameters that should not be null

**Good Example:**

```java

@Getter
@RequiredArgsConstructor
public class RateLimit
{

    @NonNull
    private final Integer tokensAvailable;

    @NonNull
    private final Integer tokensConsumed;

    @NonNull
    private final Duration refreshPeriod;

}
```

**Non-Example:**

```java
public class RateLimit
{

    private final Integer tokensAvailable;

    private final Integer tokensConsumed;

    private final Duration refreshPeriod;

    public RateLimit(Integer tokensAvailable, Integer tokensConsumed, Duration refreshPeriod)
    {
        this.tokensAvailable = tokensAvailable;
        this.tokensConsumed = tokensConsumed;
        this.refreshPeriod = refreshPeriod;
    }

    // Getters...
}
```

### Nullability Annotations

- Use `@NonNull` from Lombok for parameters that should not be null
- Apply `@NonNull` consistently across interfaces and implementations
- Consider using `Optional<T>` for return values that might be absent

**Good Example:**

```java
public interface RateLimitParser<T>
{

    List<RateLimit> parse(
            @NonNull T input);

}
```

## Testing Information

### Dependencies

Add the following dependencies to your pom.xml for testing:

```xml
<!-- JUnit 5 is already included via junit-jupiter from Testcontainers -->
<!-- Add Mockito for mocking in tests -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
        <!-- Add Hamcrest for readable assertions -->
<dependency>
<groupId>org.hamcrest</groupId>
<artifactId>hamcrest</artifactId>
<scope>test</scope>
</dependency>
```

### JUnit 5

All tests should use JUnit 5 (Jupiter).

**Good Example:**

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateLimitTest
{

    @Test
    @DisplayName("Should create rate limit with correct values")
    void shouldCreateRateLimit()
    {
        // Test implementation
    }

}
```

### Unit Tests vs Integration Tests

- Prefer unit tests over integration tests for faster feedback
- Use integration tests only when necessary to test interactions between components
- Keep unit tests focused on a single unit of functionality

**Good Example (Unit Test):**

```java

@ExtendWith(MockitoExtension.class)
class RateLimitHeaderParserTest
{

    @Mock
    private HeaderService headerService;

    @InjectMocks
    private RateLimitHeaderParser parser;

    @Test
    void shouldParseValidHeaders()
    {
        // Test with mocked dependencies
    }

}
```

### Mockito

Use Mockito for mocking dependencies in unit tests.

**Good Example:**

```java

@ExtendWith(MockitoExtension.class)
class ServiceTest
{

    @Mock
    private Dependency dependency;

    @InjectMocks
    private Service service;

    @Test
    void shouldDoSomething()
    {
        when(dependency.method()).thenReturn("result");
        assertEquals("expected", service.methodUnderTest());
        verify(dependency).method();
    }

}
```

### Hamcrest for Assertions

Use Hamcrest matchers for more readable assertions.

**Good Example:**

```java
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Test
void shouldReturnCorrectValues()
{
    List<RateLimit> limits = parser.parse(input);

    assertThat(limits, hasSize(2));
    assertThat(limits.get(0).getTokensAvailable(), is(100));
    assertThat(limits.get(0).getRefreshPeriod(), is(Duration.ofSeconds(60)));
}
```

**Non-Example:**

```java

@Test
void shouldReturnCorrectValues()
{
    List<RateLimit> limits = parser.parse(input);

    assertEquals(2, limits.size());
    assertEquals(100, limits.get(0).getTokensAvailable());
    assertEquals(Duration.ofSeconds(60), limits.get(0).getRefreshPeriod());
}
```

### Testcontainers

Use Testcontainers for integration tests that require external services like MongoDB.

**Good Example:**

```java

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration
{

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer()
    {
        return new MongoDBContainer(DockerImageName.parse("mongo:latest"));
    }

}

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class IntegrationTest
{
    // Test implementation
}
```

## Additional Development Information

### SOLID Principles

- **Single Responsibility**: Each class should have only one reason to change
- **Open/Closed**: Classes should be open for extension but closed for modification
- **Liskov Substitution**: Subtypes must be substitutable for their base types
- **Interface Segregation**: Many client-specific interfaces are better than one general-purpose interface
- **Dependency Inversion**: Depend on abstractions, not concretions

**Good Example (Dependency Inversion):**

```java
// Depend on the interface
private final RateLimitParser<Map<String, Collection<String>>> parser;

// Constructor injection
public Service(RateLimitParser<Map<String, Collection<String>>> parser)
{
    this.parser = parser;
}
```

### DRY (Don't Repeat Yourself)

- Extract common code into reusable methods or classes
- Use inheritance and composition to share behavior
- Create utility classes for common operations

**Good Example:**

```java
// Utility class for common operations
public final class HeaderUtils
{

    private HeaderUtils()
    {
        // Prevent instantiation
    }

    public static Map<String, String> extractHeaders(HttpHeaders headers, List<String> headerNames)
    {
        // Implementation
    }

}
```

### Clean Code Principles

- Use meaningful names for variables, methods, and classes
- Keep methods small and focused on a single task
- Break up bigger functions into smaller functions when appropriate
- Limit method parameters (prefer 3 or fewer)
- Write self-documenting code with clear intent
- Follow consistent formatting and style

**Good Example:**

```java
// Clear method name, limited parameters, single responsibility
public Optional<RateLimit> findMostRestrictiveLimit(List<RateLimit> limits)
{
    return limits.stream().min(Comparator.comparing(RateLimit::getTokensAvailable));
}
```

**Non-Example:**

```java
// Unclear name, multiple responsibilities
public RateLimit process(List<RateLimit> l, boolean flag1, boolean flag2)
{
    // Complex implementation with multiple responsibilities
}
```
