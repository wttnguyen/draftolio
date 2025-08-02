# Draftolio AI - Hooks for AI Actions

This document defines hooks for AI actions in the Draftolio AI project. These hooks specify tasks to be performed on pre-determined AI actions to ensure that AI assistants like Junie follow the spec-driven development pattern.

## What are AI Hooks?

AI hooks are predefined tasks or workflows that should be triggered when an AI assistant performs specific actions during the development process. These hooks ensure consistency, quality, and adherence to project standards when working with AI assistants.

## Hook Types

### 1. Pre-Action Hooks

Pre-action hooks are executed before an AI assistant performs a specific action. They are used to prepare the environment, gather necessary information, or validate preconditions.

### 2. Post-Action Hooks

Post-action hooks are executed after an AI assistant performs a specific action. They are used to validate the results, perform additional tasks, or update related artifacts.

### 3. Error Hooks

Error hooks are executed when an AI assistant encounters an error or exception. They are used to handle errors gracefully, provide helpful feedback, or suggest recovery actions.

## Hook Definitions

### Feature Development Hooks

#### 1. Pre-Feature Implementation Hook

**Trigger**: Before implementing a new feature

**Tasks**:
1. Check if a feature specification exists in the `docs/specs` directory
2. If no specification exists, create one using the template from `docs/templates/feature-spec-template.md`
3. Ensure the specification has been reviewed and approved (status is "Approved")
4. Identify all affected components and services
5. Create a feature branch from the develop branch with the naming convention `feature/feature-name`

**Example**:
```
# Pre-Feature Implementation Hook
if [[ ! -f "docs/specs/feature-name.md" ]]; then
  cp docs/templates/feature-spec-template.md docs/specs/feature-name.md
  echo "Feature specification created. Please fill it out and get it approved before proceeding."
  exit 1
fi

if ! grep -q "Status: Approved" "docs/specs/feature-name.md"; then
  echo "Feature specification is not approved. Please get it approved before proceeding."
  exit 1
fi

git checkout -b feature/feature-name develop
```

#### 2. Post-Feature Implementation Hook

**Trigger**: After implementing a new feature

**Tasks**:
1. Run all tests related to the feature
2. Update the feature specification status to "Completed"
3. Update the changelog with the new feature
4. Create a pull request to merge the feature branch into develop

**Example**:
```
# Post-Feature Implementation Hook
mvn test -Dtest="*FeatureNameTest"
sed -i 's/Status: In Development/Status: Completed/' docs/specs/feature-name.md
echo "- Feature: [Feature Name](docs/specs/feature-name.md)" >> CHANGELOG.md
gh pr create --base develop --head feature/feature-name --title "Feature: Feature Name" --body "Implements the Feature Name feature as specified in docs/specs/feature-name.md"
```

### Code Generation Hooks

#### 1. Pre-Code Generation Hook

**Trigger**: Before generating code

**Tasks**:
1. Check if the code generation request aligns with an approved feature specification
2. Identify the appropriate templates and patterns to use
3. Validate that the code generation follows the project's architecture and design principles
4. Ensure that the code will be generated in the correct location

**Example**:
```
# Pre-Code Generation Hook
if [[ ! -f "docs/specs/feature-name.md" ]]; then
  echo "No feature specification found. Please create one before generating code."
  exit 1
fi

# Validate architecture alignment
if ! grep -q "Architecture: Microservices" "docs/specs/feature-name.md"; then
  echo "Warning: Feature specification does not explicitly mention microservices architecture."
fi

# Check for appropriate location
if [[ "$COMPONENT_TYPE" == "controller" && "$LOCATION" != "src/main/java/org/willwin/draftolioai/*/api/controller" ]]; then
  echo "Warning: Controllers should be placed in the api/controller package."
fi
```

#### 2. Post-Code Generation Hook

**Trigger**: After generating code

**Tasks**:
1. Run static code analysis to ensure the generated code meets quality standards
2. Ensure the generated code follows the project's coding conventions
3. Add appropriate tests for the generated code
4. Update documentation to reflect the new code

**Example**:
```
# Post-Code Generation Hook
mvn checkstyle:check
mvn sonar:sonar
echo "Generating tests for the new code..."
# Generate test stubs or templates
echo "Updating documentation..."
# Update relevant documentation
```

### Code Review Hooks

#### 1. Pre-Code Review Hook

**Trigger**: Before submitting code for review

**Tasks**:
1. Run all tests to ensure they pass
2. Run static code analysis to catch common issues
3. Ensure code follows project coding conventions
4. Check for security vulnerabilities
5. Verify that the code implements all requirements from the feature specification

**Example**:
```
# Pre-Code Review Hook
mvn clean test
mvn checkstyle:check
mvn sonar:sonar
mvn dependency-check:check
# Verify requirements implementation
for req in $(grep -o "US-[0-9]*" "docs/specs/feature-name.md"); do
  if ! grep -q "$req" src/main/java/org/willwin/draftolioai/*/; then
    echo "Warning: User story $req may not be implemented."
  fi
done
```

#### 2. Post-Code Review Hook

**Trigger**: After code review is completed

**Tasks**:
1. Address all review comments
2. Update tests if necessary
3. Run tests again to ensure they still pass
4. Update the feature specification if changes were made during review

**Example**:
```
# Post-Code Review Hook
# After addressing review comments
mvn clean test
# Update feature specification if needed
if [[ $(git diff -- docs/specs/feature-name.md) ]]; then
  echo "Feature specification was updated during review. Please ensure it's still accurate."
fi
```

### Deployment Hooks

#### 1. Pre-Deployment Hook

**Trigger**: Before deploying to an environment

**Tasks**:
1. Run all tests to ensure they pass
2. Check that the feature specification is complete and up-to-date
3. Verify that all required documentation is in place
4. Ensure that the deployment follows the deployment workflow defined in `docs/steering/deployment-workflow.md`

**Example**:
```
# Pre-Deployment Hook
mvn clean test
if ! grep -q "Status: Completed" "docs/specs/feature-name.md"; then
  echo "Feature specification is not marked as completed. Please update it before deploying."
  exit 1
fi
# Check for required documentation
if [[ ! -f "docs/user-guides/feature-name.md" ]]; then
  echo "User guide for the feature is missing. Please create it before deploying."
  exit 1
fi
# Verify deployment workflow
echo "Please ensure you're following the deployment workflow defined in docs/steering/deployment-workflow.md"
```

#### 2. Post-Deployment Hook

**Trigger**: After deploying to an environment

**Tasks**:
1. Run smoke tests to verify the deployment
2. Monitor application metrics and logs for any issues
3. Update the deployment status in the feature specification
4. Notify stakeholders about the deployment

**Example**:
```
# Post-Deployment Hook
mvn test -Dtest="*SmokeTest"
# Monitor metrics and logs
echo "Monitoring application metrics and logs..."
# Update deployment status
sed -i 's/Deployment Status: Pending/Deployment Status: Deployed to $ENVIRONMENT/' docs/specs/feature-name.md
# Notify stakeholders
echo "Notifying stakeholders about the deployment..."
```

## AI Assistant-Specific Hooks

### Junie Hooks

#### 1. Pre-Task Analysis Hook

**Trigger**: Before Junie analyzes a task

**Tasks**:
1. Check if the task is related to an existing feature specification
2. If not, suggest creating a feature specification first
3. Identify relevant steering files that apply to the task
4. Gather context from related code and documentation

**Example Implementation**:
```
function preTaskAnalysis() {
  # Check for feature specification
  local featureSpec=$(find docs/specs -name "*.md" -exec grep -l "$TASK_DESCRIPTION" {} \;)
  if [[ -z "$featureSpec" ]]; then
    echo "No feature specification found for this task. Consider creating one first."
  else
    echo "Related feature specification found: $featureSpec"
  fi
  
  # Identify relevant steering files
  if [[ "$TASK_DESCRIPTION" == *"API"* ]]; then
    echo "This task involves API development. Please refer to docs/steering/api-standards.md"
  fi
  if [[ "$TASK_DESCRIPTION" == *"security"* ]]; then
    echo "This task involves security considerations. Please refer to docs/steering/security-policies.md"
  fi
  # Add more steering file checks as needed
  
  # Gather context
  echo "Gathering context from related code and documentation..."
}
```

#### 2. Code Generation Hook

**Trigger**: When Junie generates code

**Tasks**:
1. Ensure the code follows the project's coding conventions
2. Add appropriate comments and documentation
3. Include necessary tests
4. Verify alignment with architecture and design principles

**Example Implementation**:
```
function onCodeGeneration() {
  # Check coding conventions
  echo "Verifying code against coding conventions in docs/steering/code-conventions.md..."
  
  # Add comments and documentation
  echo "Adding appropriate comments and documentation..."
  
  # Include tests
  echo "Generating tests for the code..."
  
  # Verify architecture alignment
  echo "Verifying alignment with architecture and design principles..."
}
```

#### 3. Post-Task Completion Hook

**Trigger**: After Junie completes a task

**Tasks**:
1. Summarize the changes made
2. Update related documentation
3. Suggest next steps
4. Provide references to relevant project guidelines

**Example Implementation**:
```
function postTaskCompletion() {
  # Summarize changes
  echo "Summary of changes made:"
  git diff --stat
  
  # Update documentation
  echo "Updating related documentation..."
  
  # Suggest next steps
  echo "Suggested next steps:"
  echo "1. Review the changes"
  echo "2. Run tests to verify functionality"
  echo "3. Submit for code review"
  
  # Provide references
  echo "Relevant project guidelines:"
  echo "- Code Conventions: docs/steering/code-conventions.md"
  echo "- Testing Standards: docs/steering/testing-standards.md"
}
```

## Integration with Development Workflow

### GitHub Actions Integration

To automate the execution of hooks, they can be integrated into GitHub Actions workflows:

```yaml
name: Feature Development Workflow

on:
  push:
    branches:
      - 'feature/**'

jobs:
  pre-implementation-check:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run Pre-Feature Implementation Hook
        run: |
          feature_name=$(echo ${{ github.ref }} | sed 's/refs\/heads\/feature\///')
          if [[ ! -f "docs/specs/$feature_name.md" ]]; then
            echo "::error::Feature specification not found for $feature_name"
            exit 1
          fi
          if ! grep -q "Status: Approved" "docs/specs/$feature_name.md"; then
            echo "::error::Feature specification is not approved"
            exit 1
          fi
  
  post-implementation-check:
    needs: pre-implementation-check
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 24
        uses: actions/setup-java@v3
        with:
          java-version: '24'
          distribution: 'temurin'
      - name: Run Tests
        run: mvn test
      - name: Check Code Quality
        run: |
          mvn checkstyle:check
          mvn sonar:sonar
```

### IDE Integration

Hooks can also be integrated into IDEs through plugins or scripts:

- **IntelliJ IDEA**: Use file watchers or custom plugins to trigger hooks on specific actions
- **VS Code**: Use tasks and launch configurations to execute hooks
- **Eclipse**: Use builders and natures to integrate hooks into the development workflow

## Custom Hook Development

### Creating New Hooks

To create a new hook:

1. Identify the action or event that should trigger the hook
2. Define the tasks that should be performed
3. Implement the hook as a script or function
4. Integrate the hook into the development workflow
5. Document the hook in this file

### Hook Template

```
#### [Hook Name]

**Trigger**: [When the hook should be triggered]

**Tasks**:
1. [Task 1]
2. [Task 2]
3. ...

**Example**:
```bash
# [Hook Name]
# Implementation goes here
```

## Conclusion

AI hooks provide a structured way to integrate AI assistants into the spec-driven development workflow. By defining clear tasks and expectations for different actions, hooks ensure that AI-assisted development follows the project's standards and practices.

These hooks should be regularly reviewed and updated as the project evolves and new requirements emerge. The goal is to create a seamless integration between human developers, AI assistants, and the development process.
