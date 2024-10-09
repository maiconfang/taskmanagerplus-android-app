# TaskManagerPlus Android App - Use Cases

## 1. Use Case: User Login

### 1.1 Description
This use case describes how a user logs into the app using an email and password.

### 1.2 Actors
- **Primary Actor**: User

### 1.3 Preconditions
- The user must have an existing account.
- The app must be installed and running on the device.

### 1.4 Main Flow
1. The user opens the app.
2. The user enters their email and password in the login form.
3. The user clicks the "Login" button.
4. The system verifies the email and password.
5. If the credentials are valid, the user is redirected to the task list screen.

### 1.5 Alternative Flows
- **Invalid Credentials**:
  1. If the email or password is incorrect, the system displays an error message: "Invalid email or password."
  2. The user is prompted to try again.
  

---

## 2. Use Case: Create Task

### 2.1 Description
This use case describes how a user creates a new task in the app.

### 2.2 Actors
- **Primary Actor**: User

### 2.3 Preconditions
- The user must be logged in.
- The user must be on the task list screen.

### 2.4 Main Flow
1. The user clicks the "Add Task" button.
2. The user enters the task title (mandatory) and task description (optional).
3. The user clicks the "Save" button.
4. The system saves the task in the local database.
5. The task appears in the task list.

### 2.5 Alternative Flows
- **Missing Title**:
  1. If the task title is missing, the system displays an error message: "Task title is required."
  2. The user must enter a title before saving the task.

---

## 3. Use Case: Edit Task

### 3.1 Description
This use case describes how a user edits an existing task.

### 3.2 Actors
- **Primary Actor**: User

### 3.3 Preconditions
- The user must be logged in.
- The user must be on the task list screen.
- The task to be edited must exist in the task list.

### 3.4 Main Flow
1. The user selects a task from the list.
2. The user clicks the "Edit" button.
3. The user modifies the task title and/or description.
4. The user clicks the "Save" button.
5. The system updates the task in the local database.

---

## 4. Use Case: Delete Task

### 4.1 Description
This use case describes how a user deletes a task.

### 4.2 Actors
- **Primary Actor**: User

### 4.3 Preconditions
- The user must be logged in.
- The user must be on the task list screen.
- The task to be deleted must exist in the task list.

### 4.4 Main Flow
1. The user selects a task from the list.
2. The user clicks the "Delete" button.
3. The system prompts the user for confirmation.
4. The user confirms the deletion.
5. The system deletes the task from the local database.

---

## 5. Use Case: View Task List

### 5.1 Description
This use case describes how a user views the list of tasks.

### 5.2 Actors
- **Primary Actor**: User

### 5.3 Preconditions
- The user must be logged in.

### 5.4 Main Flow
1. The user opens the task list screen after logging in.
2. The system retrieves and displays all tasks from the local database.

---

## 6. Use Case: Filter Tasks

### 6.1 Description
This use case describes how a user filters tasks based on their status (completed or pending).

### 6.2 Actors
- **Primary Actor**: User

### 6.3 Preconditions
- The user must be logged in.
- The user must have tasks in the task list.

### 6.4 Main Flow
1. The user selects a filter option (e.g., "Completed" or "Pending").
2. The system updates the task list to display only the tasks that match the selected filter.


## 7. Use Case: View Task Details

### 7.1 Description
This use case describes how a user views detailed information about a task.

### 7.2 Actors
- **Primary Actor**: User

### 7.3 Preconditions
- The user must be logged in.
- The user must have tasks in the task list.

### 7.4 Main Flow
1. The user selects a task from the task list.
2. The system displays the task title, description, and status (completed or pending) in a new screen.

