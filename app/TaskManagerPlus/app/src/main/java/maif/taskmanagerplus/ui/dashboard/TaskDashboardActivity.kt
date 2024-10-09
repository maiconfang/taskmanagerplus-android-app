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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maif.taskmanagerplus.R
import maif.taskmanagerplus.model.Task
import maif.taskmanagerplus.ui.login.afterTextChanged

class TaskDashboardActivity : AppCompatActivity() {

    // Simulated task list with completion status
    private val taskList = mutableListOf(
        Task(
            "Update the October monthly expense spreadsheet",
            "Check the receipts and payment confirmation, review the credit card, ask Luana if there are more expenses I should include",
            isCompleted = false
        ),
        Task("Title 1", "Description 1", isCompleted = true),
        Task("Title 2", "Description 2", isCompleted = false),
        Task("Title 3", "Description 3", isCompleted = true)
    )

    private lateinit var adapter: TaskDashboardAdapter

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
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

        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.rv_task_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Show only pending tasks initially
        val pendingTasks = taskList.filter { !it.isCompleted }.toMutableList()
        adapter = TaskDashboardAdapter(pendingTasks) { task ->
            val intent = Intent(this, TaskDashboardDetailActivity::class.java).apply {
                putExtra("TASK_TITLE", task.title)
                putExtra("TASK_DESCRIPTION", task.description)
                putExtra("TASK_STATUS", task.isCompleted)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Handle search input dynamically
        val searchEditText = findViewById<EditText>(R.id.et_search_task)
        val completedCheckBox = findViewById<CheckBox>(R.id.cb_completed)
        val pendingCheckBox = findViewById<CheckBox>(R.id.cb_pending)

        // Set the initial state of the checkboxes
        completedCheckBox.isChecked = false // Uncheck Completed
        pendingCheckBox.isChecked = true // Check Pending

        searchEditText.afterTextChanged { searchText ->
            filterTaskList(searchText, completedCheckBox.isChecked, pendingCheckBox.isChecked, adapter)
        }

        // Filter by Completed and Pending status
        completedCheckBox.setOnCheckedChangeListener { _, _ ->
            filterTaskList(searchEditText.text.toString(), completedCheckBox.isChecked, pendingCheckBox.isChecked, adapter)
        }

        pendingCheckBox.setOnCheckedChangeListener { _, _ ->
            filterTaskList(searchEditText.text.toString(), completedCheckBox.isChecked, pendingCheckBox.isChecked, adapter)
        }

        // Button to add new task
        val addTaskButton = findViewById<ImageButton>(R.id.btn_add_task)
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
    }

    // Handle result from AddTaskActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            val newTaskTitle = data?.getStringExtra("TASK_TITLE") ?: ""
            val newTaskDescription = data?.getStringExtra("TASK_DESCRIPTION") ?: ""
            val isCompleted = data?.getBooleanExtra("TASK_STATUS", false) ?: false

            val newTask = Task(newTaskTitle, newTaskDescription, isCompleted)
            taskList.add(newTask) // Add new task to list
            filterTaskList("", false, true, adapter) // Update list showing pending tasks by default
        }
    }

    private fun filterTaskList(
        searchText: String,
        showCompleted: Boolean,
        showPending: Boolean,
        adapter: TaskDashboardAdapter
    ) {
        val filteredList = taskList.filter {
            it.title.contains(searchText, ignoreCase = true) &&
                    ((showCompleted && it.isCompleted) || (showPending && !it.isCompleted))
        }

        if (filteredList.isEmpty()) {
            val text = "No tasks found"
            val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
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
