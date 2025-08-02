# Draftolio AI - Testing Standards

This document establishes the minimal testing approach for the Draftolio AI project, focusing exclusively on code testing.

## Testing Philosophy

Our testing approach is guided by the following principles:

1. **Minimal, Focused Testing**: Focus on testing code only, with an emphasis on unit tests for business logic.
2. **Test Early, Test Often**: Testing should be integrated into the development process from the beginning, not added as an afterthought.
3. **Test Behavior, Not Implementation**: Focus on testing the behavior of the system rather than its internal implementation details.
4. **Maintainable Tests**: Tests should be easy to understand, maintain, and extend.
5. **Fast Feedback**: Tests should run quickly to provide fast feedback to developers.
6. **Reliable Tests**: Tests should be deterministic and not flaky.

## Testing Approach

Our testing strategy focuses primarily on unit tests with minimal integration tests:

```
    /\
   /  \
  /    \
 /      \
/----------\
/            \
/ Unit Tests   \
/----------------\
```

1. **Unit Tests**: Form the foundation of our testing strategy. They test individual components in isolation and should be the most numerous.
2. **Basic Integration Tests**: Limited to essential component interactions, particularly for critical business flows.

## Test Types and Responsibilities

### Unit Tests

Unit tests verify the behavior of individual components in isolation.

**Characteristics**:
- Fast execution (milliseconds)
- No external dependencies (databases, file systems, networks)
- Test a single unit of work
- Use mocks or stubs for dependencies

**Responsibilities**:
- Verify business logic
- Test edge cases and error handling
- Ensure individual components work as expected

### Basic Integration Tests

Limited integration tests verify that critical components work together correctly.

**Characteristics**:
- May involve minimal external dependencies
- Test the interaction between key components
- Focus on critical business flows only

**Responsibilities**:
- Verify essential component interactions
- Test critical database interactions
- Validate core API contracts

## Testing Tools

### Backend Testing

#### Unit Testing
- **JUnit 5**: Primary testing framework for Java
- **AssertJ**: Fluent assertions library
- **Mockito**: Mocking framework

#### Integration Testing
- **Spring Boot Test**: Testing Spring Boot applications
- **Testcontainers**: For essential database integration tests

## Test Organization and Naming

### Backend Test Organization

Tests should mirror the structure of the main source code:

```
src/
├── main/java/org/willwin/draftolioai/
│   └── [module]/
│       ├── domain/
│       ├── api/
│       └── infrastructure/
└── test/java/org/willwin/draftolioai/
    └── [module]/
        ├── domain/
        │   ├── entity/
        │   ├── service/
        │   └── repository/
        ├── api/
        │   ├── controller/
        │   └── mapper/
        └── infrastructure/
            ├── client/
            └── messaging/
```

### Test Naming Conventions

#### Unit Tests

Follow the pattern: `[ClassUnderTest][MethodUnderTest][Scenario][ExpectedResult]Test`

Examples:
- `DocumentServiceCreateDocumentWithValidInputSuccessTest`
- `UserRepositoryFindByEmailWhenEmailDoesNotExistReturnsEmptyTest`

#### Integration Tests

Follow the pattern: `[ComponentA][ComponentB]IntegrationTest`

Examples:
- `DocumentServiceRepositoryIntegrationTest`
- `AuthControllerServiceIntegrationTest`

### Test Method Naming

Use descriptive method names that clearly indicate what is being tested:

- `shouldCreateDocumentWhenValidInputProvided()`
- `shouldThrowExceptionWhenTitleIsEmpty()`
- `shouldReturnNotFoundWhenDocumentDoesNotExist()`

## Unit Testing Standards

### Test Structure

Follow the Arrange-Act-Assert (AAA) pattern:

```java
@Test
void shouldCreateDocumentWhenValidInputProvided() {
    // Arrange
    DocumentCreateRequest request = new DocumentCreateRequest("Test Document", "content");
    when(documentRepository.save(any(Document.class))).thenReturn(new Document(1L, "Test Document", "content"));
    
    // Act
    DocumentResponse response = documentService.createDocument(request);
    
    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getTitle()).isEqualTo("Test Document");
    verify(documentRepository).save(any(Document.class));
}
```

### Mocking Guidelines

1. **Mock External Dependencies**: Mock external services, repositories, and other dependencies.
2. **Don't Mock Value Objects**: Don't mock simple value objects or data structures.
3. **Verify Interactions**: Verify that the expected interactions with mocks occurred.
4. **Be Specific with Matchers**: Use specific matchers rather than `any()` when possible.

Example:

```java
@Test
void shouldSendNotificationWhenDocumentShared() {
    // Arrange
    Document document = new Document(1L, "Test Document", "content");
    User user = new User(2L, "test@example.com");
    
    // Act
    documentService.shareDocument(document, user);
    
    // Assert
    verify(notificationService).sendNotification(
        eq(user.getEmail()),
        eq("Document Shared"),
        contains("Test Document")
    );
}
```

### Test Data

1. **Use Test Factories**: Create factory methods or builder patterns for test data.
2. **Avoid Magic Values**: Explain the significance of test values with named constants or comments.
3. **Minimize Test Data**: Include only the data relevant to the test.

Example:

```java
class DocumentTestFactory {
    static Document createValidDocument() {
        return Document.builder()
            .id(1L)
            .title("Test Document")
            .content("Test content")
            .status(DocumentStatus.DRAFT)
            .createdBy("user-123")
            .createdAt(LocalDateTime.now())
            .build();
    }
    
    static DocumentCreateRequest createValidRequest() {
        return new DocumentCreateRequest(
            "Test Document",
            "Test content",
            DocumentStatus.DRAFT
        );
    }
}
```

### Assertions

1. **Use AssertJ**: Prefer AssertJ for fluent assertions.
2. **Be Specific**: Assert exactly what you expect, not more, not less.
3. **Meaningful Error Messages**: Provide meaningful error messages for assertions.

Example:

```java
@Test
void shouldReturnCorrectDocumentDetails() {
    // Arrange & Act
    DocumentResponse response = documentService.getDocument(1L);
    
    // Assert
    assertThat(response)
        .as("Document response should not be null")
        .isNotNull();
        
    assertThat(response.getTitle())
        .as("Document title should match expected value")
        .isEqualTo("Test Document");
        
    assertThat(response.getStatus())
        .as("New document should have DRAFT status")
        .isEqualTo(DocumentStatus.DRAFT);
}
```

## Basic Integration Testing Standards

### Database Integration Tests

Use Testcontainers for essential database integration tests:

```java
@SpringBootTest
@Testcontainers
class DocumentRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");
        
    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Test
    void shouldSaveAndRetrieveDocument() {
        // Arrange
        Document document = new Document(null, "Test Document", "content");
        
        // Act
        Document saved = documentRepository.save(document);
        Optional<Document> retrieved = documentRepository.findById(saved.getId());
        
        // Assert
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getTitle()).isEqualTo("Test Document");
    }
}
```

### API Integration Tests

Test critical REST APIs using `@WebMvcTest`:

```java
@WebMvcTest(DocumentController.class)
class DocumentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DocumentService documentService;
    
    @Test
    void shouldReturnDocumentWhenExists() throws Exception {
        // Arrange
        DocumentResponse response = new DocumentResponse(1L, "Test Document", "content");
        when(documentService.getDocument(1L)).thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(get("/api/v1/documents/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Document"));
    }
}
```

## Test Coverage Expectations

### Coverage Targets

| Type of Code | Minimum Coverage |
|--------------|------------------|
| Domain Logic | 80%              |
| Services     | 70%              |
| Controllers  | 60%              |
| DTOs/Mappers | 50%              |
| Overall      | 65%              |

### Coverage Measurement

- Use JaCoCo for Java code coverage
- Configure coverage reports in CI/CD pipeline

### Coverage Exclusions

Some code may be excluded from coverage calculations:

- Generated code
- Configuration classes
- Main application class
- Exception classes
- Simple getters and setters

## Test Execution

Configure test execution in build tools:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <includes>
            <include>**/*Test.java</include>
        </includes>
        <excludes>
            <exclude>**/*IntegrationTest.java</exclude>
        </excludes>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <includes>
            <include>**/*IntegrationTest.java</include>
        </includes>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
                <goal>verify</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Conclusion

This testing approach provides a minimal but effective framework for ensuring the quality of the Draftolio AI application's code. By focusing on unit tests with limited integration tests, we can maintain code quality while keeping testing efforts streamlined and efficient.

Remember that even with a minimal testing approach, tests should help us improve the design and maintainability of our code, not just verify that it works.
