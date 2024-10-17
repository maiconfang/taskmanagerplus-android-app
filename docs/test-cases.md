

# **TaskManagerPlus Android App - Test Cases**

## **1. Test Case: Login with Valid Credentials**

### **1.1 Test Case ID**
- TC001

### **1.2 Description**
- Verifies that a user can log in with valid credentials.

### **1.3 Preconditions**
- The user has an account with a valid email and password.
- The app is installed and running on the device.

### **1.4 Test Steps**
1. Open the app.
2. Enter the valid email and password.
3. Click the "Login" button.

### **1.5 Expected Result**
- The user is successfully logged in and redirected to the task list screen.

### **1.6 Postconditions**
- The user remains logged in for future sessions until they log out.

---

## **2. Test Case: Login with Invalid Credentials**

### **2.1 Test Case ID**
- TC002

### **2.2 Description**
- Verifies that the system displays an error message when the user enters invalid login credentials.

### **2.3 Preconditions**
- The app is installed and running on the device.

### **2.4 Test Steps**
1. Open the app.
2. Enter an invalid email and/or password.
3. Click the "Login" button.

### **2.5 Expected Result**
- An error message is displayed: "Invalid email or password."

### **2.6 Postconditions**
- The user remains on the login screen.

---

## **3. Test Case: Create a New Task**

### **3.1 Test Case ID**
- TC003

### **3.2 Description**
- Verifies that the user can create a new task and see it in the task list.

### **3.3 Preconditions**
- The user is logged in.
- The app is on the task list screen.

### **3.4 Test Steps**
1. Perform login using valid credentials.
2. Navigate to the "Task Manager" screen.
3. Click the "Add Task" button.
4. Enter "Study Kotlin" in the task title field.
5. Enter "Study about coroutines" in the task description field.
6. Click the "Save" button.

### **3.5 Expected Result**
- The task "Study Kotlin" appears in the task list.

### **3.6 Postconditions**
- The task is successfully added and persists in the task list.

---

## **4. Test Case: Filter Tasks by Status "Pending"**

### **4.1 Test Case ID**
- TC004

### **4.2 Description**
- Verifies that the user can filter tasks based on their status as "Pending."

### **4.3 Preconditions**
- The user is logged in.
- There are tasks in the "Pending" state.

### **4.4 Test Steps**
1. Perform login using valid credentials.
2. Navigate to the "Task Manager" screen.
3. Add a new task with title "Complete project report" and description "Finalize and submit the project report by Friday."
4. Enter "Complete project report" in the search bar.
5. Verify that the task appears with the status "Pending."

### **4.5 Expected Result**
- The task "Complete project report" with the status "Pending" is displayed in the task list.

### **4.6 Postconditions**
- The task list updates and shows only the "Pending" tasks based on the filter.

---

## **5. Test Case: Edit an Existing Task**

### **5.1 Test Case ID**
- TC005

### **5.2 Description**
- Verifies that the user can edit an existing task and update its title and description.

### **5.3 Preconditions**
- The user is logged in.
- There is an existing task to edit.

### **5.4 Test Steps**
1. Perform login using valid credentials.
2. Navigate to the "Task Manager" screen.
3. Add a new task with title "Clean the car" and description "Clean the car inside and outside."
4. Enter "Clean the car" in the search bar.
5. Click the "Edit" button for the task "Clean the car."
6. Update the task title to "Clean the car completely."
7. Update the task description to "Clean the car, including the interior and exterior."
8. Click the "Save" button.

### **5.5 Expected Result**
- The task title and description are updated in the task list.

### **5.6 Postconditions**
- The updated task persists in the task list.

---

## **6. Test Case: View Task Details**

### **6.1 Test Case ID**
- TC006

### **6.2 Description**
- Verifies that the user can view the details of a specific task by clicking on it.

### **6.3 Preconditions**
- The user is logged in.
- There is an existing task in the task list.

### **6.4 Test Steps**
1. Perform login using valid credentials.
2. Navigate to the "Task Manager" screen.
3. Add a new task with title "Study Espresso" and description "Learn how to write UI tests with Espresso."
4. Enter "Study Espresso" in the search bar.
5. Click on the task "Study Espresso."
6. Verify that the task details (title, description, status) are displayed.

### **6.5 Expected Result**
- The task details (title, description, and status) are correctly displayed on the task detail screen.

### **6.6 Postconditions**
- The task details remain visible and correctly updated in the task detail screen.

---

## **7. Test Case: Delete a Task**

### **7.1 Test Case ID**
- TC007

### **7.2 Description**
- Verifies that the user can delete an existing task from the task list.

### **7.3 Preconditions**
- The user is logged in.
- There is an existing task in the task list.

### **7.4 Test Steps**
1. Perform login using valid credentials.
2. Navigate to the "Task Manager" screen.
3. Add a new task with title "Plan the weekend trip" and description "Organize the itinerary and book hotels."
4. Enter "Plan the weekend trip" in the search bar.
5. Click the "Delete" button for the task "Plan the weekend trip."
6. Confirm the deletion in the dialog box.

### **7.5 Expected Result**
- The task "Plan the weekend trip" is deleted and no longer appears in the task list.

### **7.6 Postconditions**
- The task is permanently removed from the task list and the local database.

---

## **8. Test Case: Filter Tasks by Status "Completed"**

### **8.1 Test Case ID**
- TC008

### **8.2 Description**
- Verifies that the user can filter tasks based on their status as "Completed."

### **8.3 Preconditions**
- The user is logged in.
- There are tasks in the "Completed" state.

### **8.4 Test Steps**
1. Perform login using valid credentials.
2. Navigate to the "Task Manager" screen.
3. Add a new task with title "Buy birthday gift" and description "Choose and purchase a gift for Luana's birthday."
4. Mark the task as completed.
5. Select the "Completed" filter option.
6. Enter "Buy birthday gift" in the search bar.

### **8.5 Expected Result**
- The task "Buy birthday gift" with the status "Completed" is displayed in the task list.

### **8.6 Postconditions**
- The task list updates and shows only the "Completed" tasks based on the filter.

---

## **Exceção: Login with Invalid Password**

### **9.1 Test Case ID**
- TC009

### **9.2 Description**
- Verifies that the system correctly handles a login attempt with a valid email and an invalid password.

### **9.3 Preconditions**
- The user has an account with a valid email but enters an incorrect password.

### **9.4 Test Steps**
1. Open the app.
2. Enter a valid email.
3. Enter an invalid password (e.g., less than the required length).
4. Click the "Login" button.

### **9.5 Expected Result**
- An error message is displayed: "Invalid password. Please try again."

### **9.6 Postconditions**
- The user remains on the login screen.

---

