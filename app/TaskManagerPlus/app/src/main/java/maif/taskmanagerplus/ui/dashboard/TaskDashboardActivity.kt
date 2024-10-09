package maif.taskmanagerplus.ui.dashboard

import android.os.Bundle
import android.widget.EditText
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

    // Simulated task list
    private val taskList = listOf(
        Task("Title 1", "Description 1"),
        Task("Title 2", "Description 2"),
        Task("Title 3", "Description 3")
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
        val adapter = TaskDashboardAdapter(taskList)
        recyclerView.adapter = adapter

        // Handle search input dynamically
        val searchEditText = findViewById<EditText>(R.id.et_search_task)
        searchEditText.afterTextChanged { searchText ->
            // Filter task list
            val filteredList = taskList.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
            adapter.updateTaskList(filteredList)
        }

    }
}