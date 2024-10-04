# TaskManagerPlus Android App - Test Cases

## 1. Test Case: Login with Valid Credentials

### 1.1 Test Case ID
- TC001

### 1.2 Description
- This test case verifies that a user can log in with valid credentials.

### 1.3 Preconditions
- The user has an account with a valid email and password.
- The app is installed and running on the device.

### 1.4 Test Steps
1. Open the app.
2. Enter the valid email and password.
3. Click the "Login" button.

### 1.5 Expected Result
- The user is successfully logged in and redirected to the task list screen.

### 1.6 Postconditions
- The user remains logged in for future sessions until they log out.

---

## 2. Test Case: Login with Invalid Credentials

### 2.1 Test Case ID
- TC002

### 2.2 Description
- This test case verifies that the system displays an error message when the user enters invalid login credentials.

### 2.3 Preconditions
- The app is installed and running on the device.

### 2.4 Test Steps
1. Open the app.
2. Enter an invalid email and/or password.
3. Click the "Login" button.

### 2.5 Expected Result
- An error message is displayed: "Invalid email or password."

### 2.6 Postconditions
- The user remains on the login screen.

---

## 3. Test Case: Create a New Task

### 3.1 Test Case ID
- TC003

### 3.2 Description
- This test case verifies that the user can create a new task.

### 3.3 Preconditions
- The user is logged in.
- The app is on the task list screen.

### 3.4 Test Steps
1. Click the "Add Task" button.
2. Enter a valid task title and description.
3. Click the "Save" button.

### 3.5 Expected Result
- The task is saved and appears in the task list.

### 3.6 Postconditions
- The new task persists in the task list.

---

## 4. Test Case: Edit an Existing Task

### 4.1 Test Case ID
- TC004

### 4.2 Description
- This test case verifies that the user can edit an existing task.

### 4.3 Preconditions
- The user is logged in.
- There is at least one task in the task list.

### 4.4 Test Steps
1. Select an existing task from the task list.
2. Click the "Edit" button.
3. Modify the task title and/or description.
4. Click the "Save" button.

### 4.5 Expected Result
- The task is updated in the task list.

### 4.6 Postconditions
- The updated task persists in the task list.

---

## 5. Test Case: Delete a Task

### 5.1 Test Case ID
- TC005

### 5.2 Description
- This test case verifies that the user can delete a task.

### 5.3 Preconditions
- The user is logged in.
- There is at least one task in the task list.

### 5.4 Test Steps
1. Select an existing task from the task list.
2. Click the "Delete" button.
3. Confirm the deletion.

### 5.5 Expected Result
- The task is deleted and no longer appears in the task list.

### 5.6 Postconditions
- The task is permanently removed from the local database.

---

## 6. Test Case: View Task List

### 6.1 Test Case ID
- TC006

### 6.2 Description
- This test case verifies that the user can view the list of tasks after logging in.

### 6.3 Preconditions
- The user is logged in.
- There are tasks stored in the database.

### 6.4 Test Steps
1. Open the app and log in.
2. Navigate to the task list screen.

### 6.5 Expected Result
- All tasks stored in the local database are displayed in the task list.

### 6.6 Postconditions
- The task list is correctly loaded and displayed.

---

## 7. Test Case: Filter Tasks by Status

### 7.1 Test Case ID
- TC007

### 7.2 Description
- This test case verifies that the user can filter tasks based on their status (completed or pending).

### 7.3 Preconditions
- The user is logged in.
- There are tasks in both completed and pending states.

### 7.4 Test Steps
1. Open the task list screen.
2. Select the filter option for "Completed" tasks.

### 7.5 Expected Result
- Only tasks marked as completed are displayed.

### 7.6 Postconditions
- The task list updates based on the selected filter.

