# TaskManagerPlus Android App - System Architecture

## 1. Overview
The TaskManagerPlus Android App is based on a modular architecture using the **MVVM (Model-View-ViewModel)** design pattern. This architecture separates the user interface logic from the business logic, making the app more maintainable and scalable.

## 2. Architecture Diagram
> (Include a diagram here, showing the interaction between Activities, Fragments, ViewModel, and the Room database)

## 3. Components

### 3.1 Model (Data Layer)
The **Model** represents the data layer of the application. It is responsible for managing the data and interacting with the local database (Room) to store and retrieve tasks.

- **Room Database**: The app uses Room as the local database for storing tasks. It provides an abstraction layer over SQLite, making data persistence easier to implement and maintain.
- **Entities**: Represent the data structure stored in the database (e.g., `TaskEntity`).
- **DAO (Data Access Object)**: Interfaces that define the database operations for interacting with the Room database (e.g., `TaskDao`).

### 3.2 View (UI Layer)
The **View** is responsible for rendering the UI components and displaying the data to the user. It consists of **Activities** and **Fragments** that display the task list and forms for creating/editing tasks.

- **Activities**:
  - `LoginActivity`: Handles the login interface where the user enters their email and password.
  - `TaskListActivity`: Displays the list of tasks and allows the user to navigate to task creation/edit screens.
  
- **Fragments**:
  - `TaskListFragment`: Displays the list of tasks.
  - `TaskFormFragment`: Contains the form to add or edit tasks.

### 3.3 ViewModel (Logic Layer)
The **ViewModel** handles the business logic and data management between the View and Model. It provides data to the UI and responds to user interactions.

- **LoginViewModel**: Manages user authentication and validation logic.
- **TaskViewModel**: Manages the task creation, retrieval, and deletion logic, communicating with the Room database through repositories.

### 3.4 Repositories
Repositories act as intermediaries between the ViewModel and the data source (Room database). They abstract the data access logic and make it easier to test and maintain the code.

- **TaskRepository**: Contains methods to interact with the DAO and return task data to the ViewModel.

## 4. Data Flow
1. **User Interaction**: The user interacts with the UI (View) through activities or fragments.
2. **ViewModel**: The ViewModel listens to user actions and updates the UI accordingly by interacting with the data layer (Model).
3. **Model**: The data is fetched from or stored in the Room database through DAOs and repositories.

## 5. Third-Party Libraries
The application uses the following third-party libraries:
- **Room**: For data persistence.
- **Kotlin Coroutines**: For asynchronous operations, such as fetching tasks from the database.
- **Android Jetpack**: For managing lifecycle-aware components like ViewModel and LiveData.
- **Material Components**: For modern UI components, like buttons and text fields.

## 6. Error Handling
The app uses the following strategies for error handling:
- **Login Errors**: If the user enters invalid credentials, the app displays an error message on the login screen.
- **Database Errors**: If there is an issue with saving or retrieving tasks, an error message is displayed to the user.

## 7. Future Extensions
The architecture is designed to be modular and extensible. Future features that can be added without significantly impacting the current architecture include:
- **API Integration**: Adding an external API for synchronizing tasks.
- **Push Notifications**: Sending reminders to the user about upcoming or overdue tasks.
