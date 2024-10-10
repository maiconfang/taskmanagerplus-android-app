package maif.taskmanagerplus.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import maif.taskmanagerplus.R
import maif.taskmanagerplus.data.model.Task
import maif.taskmanagerplus.data.model.TaskDatabase
import maif.taskmanagerplus.data.model.TaskRepository
import maif.taskmanagerplus.ui.login.afterTextChanged

class TaskDashboardActivity : AppCompatActivity() {

    private lateinit var adapter: TaskDashboardAdapter
    private lateinit var taskRepository: TaskRepository

    // Register for activity result to handle adding a new task
    private val addTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newTaskTitle = result.data?.getStringExtra("TASK_TITLE") ?: return@registerForActivityResult
            val newTaskDescription = result.data?.getStringExtra("TASK_DESCRIPTION") ?: return@registerForActivityResult
            val isCompleted = result.data?.getBooleanExtra("TASK_STATUS", false) ?: return@registerForActivityResult

            val newTask = Task(
                title = newTaskTitle,
                description = newTaskDescription,
                status = if (isCompleted) "Completed" else "Pending"
            )

            // Insert the new task within a coroutine
            lifecycleScope.launch(Dispatchers.IO) {
                taskRepository.insertTask(newTask)
                fetchTasks() // Refresh the task list from the database
            }
        }
    }

    // Register for activity result to handle the edited task
    private val editTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val updatedTitle = result.data?.getStringExtra("UPDATED_TITLE") ?: return@registerForActivityResult
            val updatedDescription = result.data?.getStringExtra("UPDATED_DESCRIPTION") ?: return@registerForActivityResult
            val updatedStatus = result.data?.getBooleanExtra("UPDATED_STATUS", false) ?: return@registerForActivityResult

            val taskId = result.data?.getIntExtra("TASK_ID", -1) ?: return@registerForActivityResult
            val taskPosition = result.data?.getIntExtra("TASK_POSITION", -1) ?: return@registerForActivityResult

            if (taskId != -1 && taskPosition != -1) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val updatedTask = Task(
                        id = taskId,
                        title = updatedTitle,
                        description = updatedDescription,
                        status = if (updatedStatus) "Completed" else "Pending"
                    )
                    taskRepository.updateTask(updatedTask)
                    runOnUiThread {
                        adapter.notifyItemChanged(taskPosition)
                        fetchTasks() // Fetch updated list from database
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_dashboard)

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the TaskRepository
        val taskDao = TaskDatabase.getInstance(applicationContext).taskDao()
        taskRepository = TaskRepository(taskDao)

        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.rv_task_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TaskDashboardAdapter(mutableListOf(),
            onTaskClick = { task: Task ->
                val intent = Intent(this, TaskDashboardDetailActivity::class.java).apply {
                    putExtra("TASK_TITLE", task.title)
                    putExtra("TASK_DESCRIPTION", task.description)
                    putExtra("TASK_STATUS", task.status)
                }
                startActivity(intent)
            },
            onEditTask = { task: Task, position ->
                val intent = Intent(this, EditTaskActivity::class.java).apply {
                    putExtra("TASK_ID", task.id)
                    putExtra("TASK_TITLE", task.title)
                    putExtra("TASK_DESCRIPTION", task.description)
                    // Convert status string to boolean for the CheckBox
                    putExtra("TASK_STATUS", task.status == "Completed")
                    putExtra("TASK_POSITION", position)
                }
                editTaskLauncher.launch(intent)
            },
            onDeleteTask = { task: Task, position ->
                lifecycleScope.launch(Dispatchers.IO) {
                    taskRepository.deleteTask(task) // Remove task from the database
                    runOnUiThread {
                        adapter.removeTaskAt(position) // Remove task from the adapter and update UI
                        Toast.makeText(this@TaskDashboardActivity, "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        recyclerView.adapter = adapter

        // Load tasks from the database
        fetchTasks()

        // Handle search input dynamically
        val searchEditText = findViewById<EditText>(R.id.et_search_task)
        val completedCheckBox = findViewById<CheckBox>(R.id.cb_completed)
        val pendingCheckBox = findViewById<CheckBox>(R.id.cb_pending)

        // Set the initial state of the checkboxes
        completedCheckBox.isChecked = false
        pendingCheckBox.isChecked = true

        searchEditText.afterTextChanged { searchText ->
            filterTaskList(searchText, completedCheckBox.isChecked, pendingCheckBox.isChecked)
        }

        completedCheckBox.setOnCheckedChangeListener { _, _ ->
            filterTaskList(searchEditText.text.toString(), completedCheckBox.isChecked, pendingCheckBox.isChecked)
        }

        pendingCheckBox.setOnCheckedChangeListener { _, _ ->
            filterTaskList(searchEditText.text.toString(), completedCheckBox.isChecked, pendingCheckBox.isChecked)
        }

        // Button to add new task
        val addTaskButton = findViewById<ImageButton>(R.id.btn_add_task)
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }
    }

    // Fetch tasks from the database
    private fun fetchTasks() {
        lifecycleScope.launch(Dispatchers.IO) {
            val tasks = taskRepository.getAllTasks()
            runOnUiThread {
                adapter.updateTaskList(tasks)
            }
        }
    }

    private fun filterTaskList(
        searchText: String,
        showCompleted: Boolean,
        showPending: Boolean
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val tasks = taskRepository.getAllTasks()
            val filteredList = tasks.filter {
                it.title.contains(searchText, ignoreCase = true) &&
                        ((showCompleted && it.status == "Completed") || (showPending && it.status == "Pending"))
            }

            runOnUiThread {
                if (filteredList.isEmpty()) {
                    val toast = Toast.makeText(this@TaskDashboardActivity, "No tasks found", Toast.LENGTH_SHORT)
                    toast.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        toast.cancel()
                    }, 2000)

                    adapter.updateTaskList(emptyList())
                } else {
                    adapter.updateTaskList(filteredList)
                }
            }
        }
    }
}
