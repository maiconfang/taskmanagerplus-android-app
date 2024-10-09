package maif.taskmanagerplus.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
    }

    private lateinit var adapter: TaskDashboardAdapter
    private lateinit var taskRepository: TaskRepository

    // Register for activity result to handle the edited task
    private val editTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val updatedTitle = result.data?.getStringExtra("UPDATED_TITLE") ?: return@registerForActivityResult
            val updatedDescription = result.data?.getStringExtra("UPDATED_DESCRIPTION") ?: return@registerForActivityResult
            val updatedStatus = result.data?.getBooleanExtra("UPDATED_STATUS", false) ?: return@registerForActivityResult

            val position = result.data?.getIntExtra("TASK_POSITION", -1) ?: return@registerForActivityResult
            if (position != -1) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val updatedTask = Task(title = updatedTitle, description = updatedDescription, status = updatedStatus.toString())
                    taskRepository.updateTask(updatedTask)
                    fetchTasks() // Fetch updated list from database
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_dashboard)

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
            onEditTask = { task: Task, position -> // Explicitly specifying the Task type
                val intent = Intent(this, EditTaskActivity::class.java).apply {
                    putExtra("TASK_TITLE", task.title)
                    putExtra("TASK_DESCRIPTION", task.description)
                    putExtra("TASK_STATUS", task.status)
                    putExtra("TASK_POSITION", position)
                }
                editTaskLauncher.launch(intent)
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
        completedCheckBox.isChecked = false // Uncheck Completed
        pendingCheckBox.isChecked = true // Check Pending

        searchEditText.afterTextChanged { searchText ->
            filterTaskList(searchText, completedCheckBox.isChecked, pendingCheckBox.isChecked)
        }

        // Filter by Completed and Pending status
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
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handling task addition
            val newTaskTitle = data?.getStringExtra("TASK_TITLE") ?: ""
            val newTaskDescription = data?.getStringExtra("TASK_DESCRIPTION") ?: ""
            val isCompleted = data?.getBooleanExtra("TASK_STATUS", false) ?: false

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
        } else if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handling task editing
            val updatedTitle = data?.getStringExtra("UPDATED_TITLE") ?: ""
            val updatedDescription = data?.getStringExtra("UPDATED_DESCRIPTION") ?: ""
            val updatedStatus = data?.getBooleanExtra("UPDATED_STATUS", false) ?: false
            val position = data?.getIntExtra("TASK_POSITION", -1) ?: -1
            val taskId = data?.getIntExtra("TASK_ID", -1) ?: -1

            if (position != -1 && taskId != -1) {
                val updatedTask = Task(
                    id = taskId,
                    title = updatedTitle,
                    description = updatedDescription,
                    status = if (updatedStatus) "Completed" else "Pending"
                )

                // Update the task within a coroutine
                lifecycleScope.launch(Dispatchers.IO) {
                    taskRepository.updateTask(updatedTask)
                }
                adapter.notifyItemChanged(position)
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
                    val text = "No tasks found"
                    val toast = Toast.makeText(this@TaskDashboardActivity, text, Toast.LENGTH_SHORT)
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
