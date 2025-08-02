# Draftolio AI - Code Conventions

This document specifies the coding standards, naming patterns, file organization, import ordering, and architectural decisions for the Draftolio AI project. Following these conventions ensures consistency, readability, and maintainability across the codebase.

## General Principles

1. **Readability**: Code should be easy to read and understand.
2. **Simplicity**: Prefer simple solutions over complex ones.
3. **Consistency**: Follow established patterns and conventions.
4. **DRY (Don't Repeat Yourself)**: Avoid duplication of code and knowledge.
5. **YAGNI (You Aren't Gonna Need It)**: Don't add functionality until it's necessary.
6. **SOLID Principles**: Follow SOLID principles for object-oriented design.

## Java Code Conventions

### Formatting

1. **Indentation**: Use 4 spaces for indentation, not tabs.
2. **Line Length**: Limit lines to 120 characters.
3. **Line Breaks**: Break lines at logical places, typically after operators.
4. **Braces**: Use the "Egyptian" style with opening braces on the same line.
5. **Whitespace**: Use whitespace to improve readability.

Example:

```java
public class Document {
    private final Long id;
    private String title;
    private String content;
    
    public Document(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    
    public String getFormattedTitle() {
        if (title == null || title.isEmpty()) {
            return "Untitled Document";
        }
        
        return title.substring(0, Math.min(title.length(), 50)) +
               (title.length() > 50 ? "..." : "");
    }
}
```

### Naming Conventions

1. **Classes and Interfaces**:
   - Use PascalCase (e.g., `DocumentService`, `UserRepository`)
   - Use nouns for classes (e.g., `Document`, `User`)
   - Use adjectives or nouns for interfaces (e.g., `Searchable`, `Repository`)
   - Avoid abbreviations unless widely understood

2. **Methods**:
   - Use camelCase (e.g., `createDocument`, `findById`)
   - Use verbs or verb phrases (e.g., `save`, `findByEmail`)
   - Prefix getters with "get" (e.g., `getName`)
   - Prefix boolean getters with "is" or "has" (e.g., `isActive`, `hasPermission`)
   - Prefix setters with "set" (e.g., `setName`)

3. **Variables and Parameters**:
   - Use camelCase (e.g., `documentId`, `userName`)
   - Use meaningful names that describe the purpose
   - Avoid single-letter variable names except for loop counters
   - Use plural names for collections (e.g., `documents`, `users`)

4. **Constants**:
   - Use UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE`)
   - Declare as `static final`

5. **Packages**:
   - Use lowercase with dots as separators (e.g., `org.willwin.draftolioai.document.service`)
   - Use reverse domain name convention (e.g., `org.willwin.draftolioai`)

6. **Enums**:
   - Use PascalCase for enum names (e.g., `DocumentStatus`)
   - Use UPPER_SNAKE_CASE for enum values (e.g., `DRAFT`, `PUBLISHED`)

7. **Generics**:
   - Use single uppercase letters for simple generic parameters (e.g., `T`, `E`)
   - Use descriptive names for complex generic parameters (e.g., `ElementType`, `ResponseType`)

### Documentation

1. **Javadoc**:
   - Write Javadoc for all public classes, interfaces, and methods
   - Include a brief description, parameter descriptions, return value description, and exception descriptions
   - Use HTML tags for formatting when necessary
   - Include code examples for complex APIs

Example:

```java
/**
 * Represents a document in the system.
 * <p>
 * Documents are the primary content type in the Draftolio AI system and can be
 * created, edited, shared, and analyzed.
 * </p>
 */
public class Document {
    // ...
    
    /**
     * Creates a new document with the specified title and content.
     *
     * @param title   the title of the document, must not be null
     * @param content the content of the document, may be null for empty documents
     * @return the created document
     * @throws IllegalArgumentException if title is null
     */
    public static Document create(String title, String content) {
        if (title == null) {
            throw new IllegalArgumentException("Title must not be null");
        }
        
        return new Document(null, title, content);
    }
}
```

2. **Comments**:
   - Use comments to explain why, not what
   - Keep comments up-to-date with code changes
   - Use TODO comments for temporary code or future improvements
   - Avoid commented-out code

### Code Organization

1. **Class Structure**:
   - Order class members logically:
     1. Static fields
     2. Instance fields
     3. Constructors
     4. Public methods
     5. Protected methods
     6. Private methods
     7. Inner classes
   - Group related methods together
   - Keep classes focused on a single responsibility

2. **Method Structure**:
   - Keep methods short and focused on a single task
   - Limit method parameters (prefer 3 or fewer)
   - Use method overloading judiciously
   - Extract complex logic into helper methods

3. **Field Declarations**:
   - Declare one field per line
   - Initialize fields where appropriate
   - Group related fields together
   - Use appropriate access modifiers

### Import Statements

1. **Ordering**:
   - Java standard library imports
   - Third-party library imports
   - Application imports

2. **No Wildcards**:
   - Avoid wildcard imports (e.g., `import java.util.*`)
   - Import specific classes explicitly

3. **Static Imports**:
   - Use static imports for constants and static methods when it improves readability
   - Group static imports separately

Example:

```java
// Java standard library imports
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Third-party library imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Application imports
import org.willwin.draftolioai.document.domain.Document;
import org.willwin.draftolioai.document.domain.DocumentStatus;

// Static imports
import static java.util.stream.Collectors.toList;
import static org.willwin.draftolioai.document.domain.DocumentStatus.DRAFT;
```

### Exception Handling

1. **Specific Exceptions**:
   - Throw specific exceptions rather than generic ones
   - Create custom exceptions for domain-specific errors

2. **Exception Hierarchy**:
   - Design a consistent exception hierarchy
   - Use runtime exceptions for programming errors
   - Use checked exceptions for recoverable conditions

3. **Try-with-Resources**:
   - Use try-with-resources for automatic resource management
   - Avoid finally blocks for resource cleanup

4. **Error Messages**:
   - Provide clear and informative error messages
   - Include relevant context in error messages

Example:

```java
public Document findById(Long id) {
    if (id == null) {
        throw new IllegalArgumentException("Document ID must not be null");
    }
    
    try {
        return documentRepository.findById(id)
            .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + id));
    } catch (DataAccessException e) {
        throw new DocumentServiceException("Failed to retrieve document with ID: " + id, e);
    }
}
```

### Logging

1. **Log Levels**:
   - ERROR: Use for errors that need immediate attention
   - WARN: Use for potential problems that don't prevent normal operation
   - INFO: Use for significant events in normal operation
   - DEBUG: Use for detailed information useful for debugging
   - TRACE: Use for the most detailed information

2. **Log Messages**:
   - Include relevant context in log messages
   - Use parameterized logging to avoid string concatenation
   - Don't log sensitive information

3. **Exception Logging**:
   - Log exceptions with stack traces
   - Include the cause of the exception

Example:

```java
public void processDocument(Document document) {
    logger.info("Processing document: {}", document.getId());
    
    try {
        // Process document
        logger.debug("Document processed successfully: {}", document.getId());
    } catch (Exception e) {
        logger.error("Failed to process document: {}", document.getId(), e);
        throw new DocumentProcessingException("Failed to process document", e);
    }
}
```

### Testing

1. **Test Naming**:
   - Use descriptive test names that explain what is being tested
   - Follow a consistent naming pattern

2. **Test Structure**:
   - Follow the Arrange-Act-Assert pattern
   - Keep tests focused on a single behavior
   - Use appropriate assertions

3. **Test Coverage**:
   - Write tests for all public methods
   - Test edge cases and error conditions
   - Aim for high test coverage

Example:

```java
@Test
void shouldReturnFormattedTitleWhenTitleExceeds50Characters() {
    // Arrange
    String longTitle = "This is a very long title that exceeds the maximum length of fifty characters";
    Document document = new Document(1L, longTitle, "content");
    
    // Act
    String formattedTitle = document.getFormattedTitle();
    
    // Assert
    assertEquals("This is a very long title that exceeds the maximum..." , formattedTitle);
}
```

## TypeScript/Angular Code Conventions

### Formatting

1. **Indentation**: Use 2 spaces for indentation, not tabs.
2. **Line Length**: Limit lines to 120 characters.
3. **Quotes**: Use single quotes for strings.
4. **Semicolons**: Use semicolons at the end of statements.
5. **Trailing Commas**: Use trailing commas in multi-line object literals and arrays.

Example:

```typescript
const document = {
  id: '123',
  title: 'Sample Document',
  content: 'This is a sample document content.',
  tags: [
    'sample',
    'document',
    'example',
  ],
};
```

### Naming Conventions

1. **Components, Directives, Pipes, Services**:
   - Use PascalCase for class names (e.g., `DocumentEditorComponent`)
   - Use kebab-case for file names (e.g., `document-editor.component.ts`)
   - Suffix files based on their type:
     - Components: `.component.ts`
     - Services: `.service.ts`
     - Directives: `.directive.ts`
     - Pipes: `.pipe.ts`
     - Guards: `.guard.ts`
     - Interceptors: `.interceptor.ts`

2. **Methods and Properties**:
   - Use camelCase (e.g., `getUserDocuments()`, `documentTitle`)
   - Use descriptive names that explain the purpose

3. **Interfaces and Types**:
   - Use PascalCase (e.g., `Document`, `UserProfile`)
   - Do not prefix with "I" (e.g., use `Document` instead of `IDocument`)

4. **Enums**:
   - Use PascalCase for enum names (e.g., `DocumentStatus`)
   - Use PascalCase for enum values (e.g., `Draft`, `Published`)

5. **Constants**:
   - Use UPPER_SNAKE_CASE for global constants
   - Use camelCase for local constants

6. **CSS/SCSS**:
   - Use kebab-case for class names (e.g., `document-card`, `user-avatar`)
   - Use BEM (Block Element Modifier) methodology for structuring CSS classes

### Angular-Specific Conventions

1. **Components**:
   - Keep components small and focused
   - Use OnPush change detection strategy
   - Use signals for state management
   - Use input() and output() functions instead of decorators
   - Use the host object in the component decorator for host bindings

Example:

```typescript
import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';
import { input, output } from '@angular/core';

@Component({
  selector: 'app-document-card',
  templateUrl: './document-card.component.html',
  styleUrls: ['./document-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    'class': 'document-card',
    '[class.document-card--selected]': 'isSelected()',
    '(click)': 'handleClick()'
  }
})
export class DocumentCardComponent {
  document = input.required<Document>();
  isEditable = input(false);
  select = output<Document>();
  
  private isSelectedSignal = signal(false);
  
  isSelected = computed(() => this.isSelectedSignal());
  
  formattedDate = computed(() => {
    const date = new Date(this.document().createdAt);
    return date.toLocaleDateString();
  });
  
  handleClick(): void {
    this.isSelectedSignal.update(value => !value);
    if (this.isSelected()) {
      this.select.emit(this.document());
    }
  }
}
```

2. **Templates**:
   - Use native control flow (`@if`, `@for`, `@switch`) instead of structural directives
   - Use the async pipe to handle observables
   - Use built-in pipes and import pipes when being used in a template
   - Use class bindings instead of ngClass
   - Use style bindings instead of ngStyle

Example:

```html
<div class="document-list">
  @if (documents().length > 0) {
    @for (doc of documents(); track doc.id) {
      <app-document-card
        [document]="doc"
        [isEditable]="canEdit(doc)"
        (select)="onDocumentSelect($event)"
        class="mb-4"
      ></app-document-card>
    }
  } @else {
    <div class="empty-state">
      <p>No documents found.</p>
      <button (click)="createNewDocument()" class="btn-primary">Create Document</button>
    </div>
  }
</div>
```

3. **Services**:
   - Design services around a single responsibility
   - Use the `providedIn: 'root'` option for singleton services
   - Use the `inject()` function instead of constructor injection

Example:

```typescript
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Document } from '../models/document.model';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  private http = inject(HttpClient);
  private apiUrl = '/api/v1/documents';
  
  getDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(this.apiUrl);
  }
  
  getDocument(id: string): Observable<Document> {
    return this.http.get<Document>(`${this.apiUrl}/${id}`);
  }
  
  createDocument(document: Partial<Document>): Observable<Document> {
    return this.http.post<Document>(this.apiUrl, document);
  }
  
  updateDocument(id: string, document: Partial<Document>): Observable<Document> {
    return this.http.patch<Document>(`${this.apiUrl}/${id}`, document);
  }
  
  deleteDocument(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

4. **State Management**:
   - Use signals for local component state
   - Use computed() for derived state
   - Keep state transformations pure and predictable
   - Use update() or set() instead of mutate() on signals

Example:

```typescript
import { Component, signal, computed } from '@angular/core';
import { inject } from '@angular/core';
import { DocumentService } from '../../services/document.service';
import { Document } from '../../models/document.model';

@Component({
  selector: 'app-document-list',
  templateUrl: './document-list.component.html',
  styleUrls: ['./document-list.component.scss']
})
export class DocumentListComponent {
  private documentService = inject(DocumentService);
  
  documents = signal<Document[]>([]);
  selectedDocument = signal<Document | null>(null);
  isLoading = signal(true);
  
  totalDocuments = computed(() => this.documents().length);
  hasSelectedDocument = computed(() => this.selectedDocument() !== null);
  
  ngOnInit(): void {
    this.loadDocuments();
  }
  
  loadDocuments(): void {
    this.isLoading.set(true);
    this.documentService.getDocuments().subscribe({
      next: (docs) => {
        this.documents.set(docs);
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Failed to load documents', error);
        this.isLoading.set(false);
      }
    });
  }
  
  selectDocument(document: Document): void {
    this.selectedDocument.set(document);
  }
  
  clearSelection(): void {
    this.selectedDocument.set(null);
  }
}
```

### Import Statements

1. **Ordering**:
   - Angular framework imports
   - Third-party library imports
   - Application imports
   - Relative imports

2. **Path Aliases**:
   - Use path aliases for imports from app modules (e.g., `@core`, `@shared`)
   - Configure path aliases in `tsconfig.json`

3. **Barrel Files**:
   - Use barrel files (index.ts) to simplify imports from directories

Example:

```typescript
// Angular imports
import { Component, OnInit, signal, computed } from '@angular/core';
import { inject } from '@angular/core';

// Third-party imports
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

// Application imports from aliases
import { Document } from '@core/models';
import { AuthService } from '@core/services';

// Relative imports
import { DocumentFilterComponent } from './document-filter/document-filter.component';
```

### Error Handling

1. **Observable Error Handling**:
   - Use the error callback in subscribe
   - Use catchError operator for error transformation
   - Provide meaningful error messages

2. **Global Error Handling**:
   - Implement a global error handler
   - Log errors to a monitoring service

Example:

```typescript
import { Injectable, ErrorHandler, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { LoggingService } from './logging.service';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {
  private loggingService = inject(LoggingService);
  private notificationService = inject(NotificationService);
  
  handleError(error: Error | HttpErrorResponse): void {
    let message = 'An unexpected error occurred';
    
    if (error instanceof HttpErrorResponse) {
      // Server or connection error
      message = this.getServerErrorMessage(error);
      this.loggingService.logError(`HTTP Error: ${message}`, error);
    } else {
      // Client error
      this.loggingService.logError(`Client Error: ${error.message}`, error);
    }
    
    this.notificationService.showError(message);
  }
  
  private getServerErrorMessage(error: HttpErrorResponse): string {
    if (error.status === 0) {
      return 'Unable to connect to the server. Please check your internet connection.';
    }
    
    if (error.status === 404) {
      return 'The requested resource was not found.';
    }
    
    if (error.status === 403) {
      return 'You do not have permission to access this resource.';
    }
    
    if (error.status === 500) {
      return 'A server error occurred. Please try again later.';
    }
    
    return error.error?.message || 'An unexpected server error occurred.';
  }
}
```

### Testing

1. **Unit Testing**:
   - Test components and services in isolation
   - Focus on business logic and core functionality
   - Keep tests simple and maintainable

2. **Basic Integration Testing**:
   - Test critical component interactions
   - Use TestBed for minimal Angular-specific testing
   - Use HttpClientTestingModule for basic HTTP service testing

Example:

```typescript
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DocumentCardComponent } from './document-card.component';
import { Document } from '../../models/document.model';

describe('DocumentCardComponent', () => {
  let component: DocumentCardComponent;
  let fixture: ComponentFixture<DocumentCardComponent>;
  
  const mockDocument: Document = {
    id: '123',
    title: 'Test Document',
    content: 'Test content',
    createdAt: '2025-08-01T12:00:00Z',
    updatedAt: '2025-08-01T12:00:00Z',
    createdBy: 'user-123'
  };
  
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentCardComponent]
    }).compileComponents();
    
    fixture = TestBed.createComponent(DocumentCardComponent);
    component = fixture.componentInstance;
    component.document = mockDocument;
    fixture.detectChanges();
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should display document title', () => {
    const titleElement = fixture.debugElement.query(By.css('.document-title'));
    expect(titleElement.nativeElement.textContent).toContain('Test Document');
  });
  
  it('should emit select event when clicked', () => {
    spyOn(component.select, 'emit');
    const cardElement = fixture.debugElement.query(By.css('.document-card'));
    cardElement.triggerEventHandler('click', null);
    expect(component.select.emit).toHaveBeenCalledWith(mockDocument);
  });
});
```

## SQL Conventions

### Naming Conventions

1. **Tables**:
   - Use plural nouns (e.g., `documents`, `users`)
   - Use snake_case (e.g., `document_versions`, `user_profiles`)

2. **Columns**:
   - Use singular nouns (e.g., `title`, `email`)
   - Use snake_case (e.g., `created_at`, `user_id`)
   - Use `id` for primary keys
   - Use `{table_name}_id` for foreign keys (e.g., `document_id`, `user_id`)

3. **Constraints**:
   - Use `pk_{table_name}` for primary key constraints
   - Use `fk_{table_name}_{referenced_table_name}` for foreign key constraints
   - Use `uq_{table_name}_{column_name}` for unique constraints
   - Use `ck_{table_name}_{column_name}` for check constraints

4. **Indexes**:
   - Use `idx_{table_name}_{column_name}` for single-column indexes
   - Use `idx_{table_name}_{column1}_{column2}` for multi-column indexes

### Query Formatting

1. **Keywords**:
   - Use UPPERCASE for SQL keywords (e.g., `SELECT`, `FROM`, `WHERE`)
   - Use lowercase for table and column names

2. **Indentation**:
   - Indent subqueries and joins
   - Align related clauses

3. **Aliasing**:
   - Use meaningful aliases for tables (e.g., `d` for `documents`, `u` for `users`)
   - Use AS keyword for column aliases

Example:

```sql
SELECT 
    d.id,
    d.title,
    d.content,
    u.name AS author_name,
    COUNT(c.id) AS comment_count
FROM 
    documents d
    INNER JOIN users u ON d.user_id = u.id
    LEFT JOIN comments c ON c.document_id = d.id
WHERE 
    d.status = 'PUBLISHED'
    AND d.created_at > '2025-01-01'
GROUP BY 
    d.id, d.title, d.content, u.name
HAVING 
    COUNT(c.id) > 0
ORDER BY 
    d.created_at DESC
LIMIT 10;
```

## Git Conventions

### Branching Strategy

1. **Main Branches**:
   - `main`: Production-ready code
   - `develop`: Integration branch for features

2. **Feature Branches**:
   - Use `feature/feature-name` for new features
   - Branch from `develop`
   - Merge back to `develop` via pull request

3. **Release Branches**:
   - Use `release/version` for release preparation
   - Branch from `develop`
   - Merge to `main` and `develop` when ready

4. **Hotfix Branches**:
   - Use `hotfix/issue-description` for urgent fixes
   - Branch from `main`
   - Merge to `main` and `develop` when ready

### Commit Messages

1. **Format**:
   - Use the conventional commits format
   - Format: `type(scope): description`
   - Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
   - Keep the first line under 72 characters
   - Use the imperative mood (e.g., "Add feature" not "Added feature")

2. **Examples**:
   - `feat(document): add version history feature`
   - `fix(auth): resolve token expiration issue`
   - `docs(api): update API documentation`
   - `style(ui): improve button styling`
   - `refactor(service): simplify document processing logic`
   - `test(user): add tests for user registration`
   - `chore(deps): update dependencies`

3. **Body**:
   - Separate the body from the subject with a blank line
   - Use the body to explain what and why, not how
   - Reference issues and pull requests

Example:

```
feat(document): add collaborative editing feature

Implement real-time collaborative editing using WebSockets and operational
transforms. This allows multiple users to edit the same document simultaneously
without conflicts.

Closes #123
```

### Pull Requests

1. **Title**:
   - Use the same format as commit messages
   - Be descriptive and concise

2. **Description**:
   - Explain the purpose of the changes
   - List the key changes made
   - Include any testing done
   - Reference related issues

3. **Size**:
   - Keep pull requests small and focused
   - Aim for less than 500 lines of code per PR

4. **Review**:
   - Require at least one approval before merging
   - Address all review comments
   - Ensure CI checks pass

## Conclusion

These code conventions provide a comprehensive guide for maintaining consistency and quality across the Draftolio AI codebase. By following these conventions, we ensure that our code is readable, maintainable, and follows best practices.

Remember that these conventions are guidelines, not rigid rules. Use your judgment and consider the specific context when applying them. The ultimate goal is to write clean, maintainable code that solves the problem at hand effectively.
