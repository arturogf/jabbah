```markdown
# jabbah Development Patterns

> Auto-generated skill from repository analysis

## Overview

This skill teaches you the core development patterns, coding conventions, and collaborative workflows used in the `jabbah` Java codebase. You'll learn how to contribute features, update documentation, manage licensing, expand core entities, and improve the UI or logging, all while following the project's established conventions.

## Coding Conventions

- **File Naming:**  
  Java source files use **PascalCase** (e.g., `Main.java`, `XpdlObjectMapping.java`).

- **Import Style:**  
  Use **relative imports** within the `jabbah` package.
  ```java
  import jabbah.Activity;
  import jabbah.Lane;
  ```

- **Export Style:**  
  Use **named exports** (public classes with explicit names).
  ```java
  public class Activity {
      // ...
  }
  ```

- **Commit Messages:**  
  Freeform, typically concise (~54 characters), often referencing the main change (e.g., "Add logging to XpdlObjectMapping").

## Workflows

### Update README

**Trigger:** When you want to update or add documentation in the README.  
**Command:** `/update-readme`

1. Edit `README.md` to reflect your changes or improvements.
2. Commit your changes with a message referencing the README.
   ```
   git add README.md
   git commit -m "Update README with new usage instructions"
   git push
   ```

---

### Add or Update License Files

**Trigger:** When you need to add a new license or update license headers in the codebase.  
**Command:** `/add-license`

1. Add or update license files in the `JABBAH/src/jabbah/license/` directory.
2. Add or update license comments in each source file as needed.
   ```java
   /*
    * Copyright (c) 2024 Jabbah contributors
    * Licensed under the MIT License
    */
   ```
3. Commit all changes with an appropriate message.
   ```
   git add JABBAH/src/jabbah/license/*
   git add JABBAH/src/jabbah/*.java
   git commit -m "Update license headers and files"
   git push
   ```

---

### Feature Development (Core Logic)

**Trigger:** When adding a new feature or modifying core logic.  
**Command:** `/feature`

1. Edit or add Java source files in `JABBAH/src/jabbah/`.
2. Update related classes to support the new feature.
3. Update main application logic if required.
4. Add or update utility/helper classes.
   ```java
   // Example: Adding a new method to Activity.java
   public void setStatus(String status) {
       this.status = status;
   }
   ```
5. Commit your changes.
   ```
   git add JABBAH/src/jabbah/*.java
   git commit -m "Add status setter to Activity"
   git push
   ```

---

### UI Improvement or Change

**Trigger:** When improving or modifying the application's UI.  
**Command:** `/ui-change`

1. Edit `Main.form` or `Main.java` for UI layout changes.
2. Add or update icon files in `JABBAH/src/icons/`.
3. Commit your changes with a message referencing UI or icons.
   ```
   git add JABBAH/src/jabbah/Main.form
   git add JABBAH/src/jabbah/Main.java
   git add JABBAH/src/icons/*
   git commit -m "Update UI layout and add new icons"
   git push
   ```

---

### Add or Update Logging

**Trigger:** When increasing observability or debugging parsing logic.  
**Command:** `/add-logging`

1. Edit `Main.java` or `XpdlObjectMapping.java` to add or improve logging.
   ```java
   System.out.println("Parsing started for file: " + fileName);
   ```
2. Commit your changes with a message referencing logging.
   ```
   git add JABBAH/src/jabbah/Main.java
   git commit -m "Add parsing start log"
   git push
   ```

---

### Core Entity or Structure Expansion

**Trigger:** When introducing a new domain concept or expanding the data model.  
**Command:** `/add-entity`

1. Add new Java class files for entities in `JABBAH/src/jabbah/` (e.g., `Activity.java`, `Lane.java`).
   ```java
   public class Parameter {
       private String name;
       private String value;
       // getters and setters
   }
   ```
2. Edit related files to integrate new entities.
3. Update mapping or parsing logic as needed (e.g., in `XpdlObjectMapping.java`).
4. Commit your changes.
   ```
   git add JABBAH/src/jabbah/Parameter.java
   git commit -m "Add Parameter entity and update mapping"
   git push
   ```

## Testing Patterns

- **Framework:** Unknown (not detected).
- **Test File Pattern:** Test files follow the pattern `*.test.*` (e.g., `Activity.test.java`).
- **Location:** Typically alongside source files or in a dedicated test directory.
- **Style:** Standard Java test conventions (if present).

## Commands

| Command         | Purpose                                                         |
|-----------------|-----------------------------------------------------------------|
| /update-readme  | Update or improve documentation in README.md                    |
| /add-license    | Add or update license files and license headers                 |
| /feature        | Implement or update core features and logic                     |
| /ui-change      | Make changes or improvements to the user interface              |
| /add-logging    | Add or improve logging for observability and debugging          |
| /add-entity     | Add new core entities or expand the data model                  |
```
