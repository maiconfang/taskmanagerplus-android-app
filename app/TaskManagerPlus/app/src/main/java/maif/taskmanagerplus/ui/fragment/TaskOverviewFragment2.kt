package maif.taskmanagerplus.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import maif.taskmanagerplus.R
import maif.taskmanagerplus.data.model.Task
import maif.taskmanagerplus.data.model.TaskDatabase
import maif.taskmanagerplus.data.model.TaskRepository
import maif.taskmanagerplus.ui.dashboard.AddTaskActivity
import maif.taskmanagerplus.ui.dashboard.EditTaskActivity
import maif.taskmanagerplus.ui.dashboard.TaskDashboardAdapter
import maif.taskmanagerplus.ui.dashboard.TaskDashboardDetailActivity

class TaskOverviewFragment2 : Fragment() {

    private lateinit var adapter: TaskDashboardAdapter
    private lateinit var taskRepository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o TaskRepository
        val taskDao = TaskDatabase.getInstance(requireContext()).taskDao()
        taskRepository = TaskRepository(taskDao)

        // Setup do RecyclerView
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_task_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TaskDashboardAdapter(mutableListOf(),
            onTaskClick = { task: Task ->
                val intent = Intent(requireContext(), TaskDashboardDetailActivity::class.java).apply {
                    putExtra("TASK_TITLE", task.title)
                    putExtra("TASK_DESCRIPTION", task.description)
                    putExtra("TASK_STATUS", task.status)
                }
                startActivity(intent)
            },
            onEditTask = { task: Task, position ->
                val intent = Intent(requireContext(), EditTaskActivity::class.java).apply {
                    putExtra("TASK_ID", task.id)
                    putExtra("TASK_TITLE", task.title)
                    putExtra("TASK_DESCRIPTION", task.description)
                    putExtra("TASK_STATUS", task.status == "Completed")
                    putExtra("TASK_POSITION", position)
                }
                startActivity(intent)
            },
            onDeleteTask = { task: Task, position ->
                lifecycleScope.launch(Dispatchers.IO) {
                    taskRepository.deleteTask(task)
                    requireActivity().runOnUiThread {
                        adapter.removeTaskAt(position)
                        Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        recyclerView.adapter = adapter

        // Carregar as tarefas do banco de dados
        fetchTasks()

        // Botão para adicionar nova tarefa
        val addTaskButton = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btn_add_task)
        addTaskButton.setOnClickListener {
            val intent = Intent(requireContext(), AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    // Fetch tasks from the database
    private fun fetchTasks() {
        lifecycleScope.launch(Dispatchers.IO) {
            val tasks = taskRepository.getAllTasks()
            requireActivity().runOnUiThread {
                adapter.updateTaskList(tasks)
            }
        }
    }
}
