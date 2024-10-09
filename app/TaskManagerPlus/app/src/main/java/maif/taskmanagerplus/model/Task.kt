package maif.taskmanagerplus.model

data class Task(
    val title: String,
    val description: String,
    val isCompleted: Boolean // New attribute to track task completion
)