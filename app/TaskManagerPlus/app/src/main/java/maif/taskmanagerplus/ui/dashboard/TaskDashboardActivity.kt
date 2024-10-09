package maif.taskmanagerplus.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.CheckBox
import android.widget.EditText
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
    private val taskList = listOf(
        Task("Update the October monthly expense spreadsheet",
            "Check the receipts and payment confirmation, review the credit card, ask Luana if there are more expenses I should include",
            isCompleted = false),
        Task("Title 1", "Description 1", isCompleted = true),
        Task("Title 2", "Description 2", isCompleted = false),
        Task("Title 3", "Description 3", isCompleted = true)
    )

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
        recyclerView.layoutManager = LinearLayoutManager(this) // Set layout manager

        // Show only pending tasks initially
        val pendingTasks = taskList.filter { !it.isCompleted }.toMutableList() // Converts to MutableList

        val adapter = TaskDashboardAdapter(pendingTasks) { task ->
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
            // Show a "No tasks found" message
            val text = "No tasks found"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(this, text, duration) // in Activity
            toast.show()

            // Create a Handler to dismiss the toast after a specific delay
            Handler(Looper.getMainLooper()).postDelayed({
                toast.cancel() // Cancel the toast after 2 seconds
            }, 2000) // 2000 milliseconds

            adapter.updateTaskList(emptyList()) // Clear the list in the adapter
        } else {
            // Update the adapter with the filtered list
            adapter.updateTaskList(filteredList)
        }

    }


}