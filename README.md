---

# TaskManagerPlus Android App

## Overview
**TaskManagerPlus Android App** is a mobile application built using Kotlin for task management. As part of the TaskManagerPlus series, this app provides a user-friendly interface for creating, editing, deleting, and filtering tasks by their status. The tasks are stored locally on the device using the Room database for persistence, allowing users to manage their tasks efficiently.

## Features
- Create, edit, and delete tasks.
- Store tasks locally using the Room database for offline persistence.
- Filter tasks by their status: Pending or Completed.
- Intuitive user interface (UI) utilizing RecyclerView for displaying tasks.
- Kotlin-based architecture, following the MVVM (Model-View-ViewModel) pattern for clean code separation.

## Project Structure
```bash
/taskmanagerplus-android-app
│
├── /app/                     # Source code for the Android application
│   ├── /src/main/java/        # Kotlin source files
│   ├── /src/main/res/         # UI resources (layouts, drawables, strings)
│   ├── /src/main/assets/      # App assets (icons, images)
│   └── /src/test/             # Unit tests
├── /docs/                    # Project documentation (Requirements, Test cases, etc.)
├── /assets/                  # Project assets (e.g., images, icons)
├── README.md                 # Project overview and setup instructions
├── LICENSE                   # License file
└── build.gradle              # Gradle configuration file
```

## Installation and Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/taskmanagerplus-android-app.git
    cd taskmanagerplus-android-app
    ```

2. Open the project in **Android Studio**.

3. Sync Gradle and build the project:
    - Android Studio will detect the `build.gradle` file and install all necessary dependencies.

4. Run the app on an emulator or physical device:
    - Go to **Run > Run 'app'** in Android Studio.

## Screenshots
> (Add screenshots here once the app is fully developed)

## Documentation
- The full project documentation is located in the `/docs/` directory, including:
  - **Requirements**: Functional and non-functional requirements of the application.
  - **Architecture Design**: Overview of the system architecture and key components.
  - **Test Cases**: Documented test cases used to validate application functionality.
  - **Diagrams**: Visual diagrams explaining app structure, flow, and interactions.
  - **Wireframes**: Available in both PDF and markdown formats in English (EN-CA) and Portuguese (PT-BR) for reference.

## Contributing
If you'd like to contribute, feel free to open an issue or submit a pull request. Contributions are welcome, and we encourage collaboration.

## License
This project is licensed under the MIT License. For more details, see the [LICENSE](LICENSE) file.

---

**TaskManagerPlus Android App** is a project within the TaskManagerPlus series, aimed at exploring various development techniques for creating effective task management solutions.

---
