package maif.taskmanagerplus.ui.task

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import maif.taskmanagerplus.R
import maif.taskmanagerplus.data.model.Task
import maif.taskmanagerplus.data.model.TaskDatabase
import maif.taskmanagerplus.data.model.TaskRepository
import maif.taskmanagerplus.ui.login.afterTextChanged
import maif.taskmanagerplus.ui.task.ui.AddTaskActivity
import maif.taskmanagerplus.ui.task.ui.EditTaskActivity
import maif.taskmanagerplus.ui.task.ui.TaskAdapter
import maif.taskmanagerplus.ui.task.ui.TaskDetailActivity

class TaskFragment : Fragment() {

    private lateinit var adapter: TaskAdapter
    private lateinit var taskRepository: TaskRepository

    private lateinit var searchEditText: EditText
    private lateinit var completedCheckBox: CheckBox
    private lateinit var pendingCheckBox: CheckBox

    // Registro para adicionar uma nova tarefa
    private val addTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newTaskTitle = result.data?.getStringExtra("TASK_TITLE") ?: return@registerForActivityResult
            val newTaskDescription = result.data?.getStringExtra("TASK_DESCRIPTION") ?: return@registerForActivityResult
            val isCompleted = result.data?.getBooleanExtra("TASK_STATUS", false) ?: return@registerForActivityResult

            val newTask = Task(
                title = newTaskTitle,
                description = newTaskDescription,
                status = if (isCompleted) "Completed" else "Pending"
            )

            // Inserir a nova tarefa no banco de dados
            lifecycleScope.launch(Dispatchers.IO) {
                taskRepository.insertTask(newTask)
                fetchTasks() // Atualizar a lista de tarefas
            }
        }
    }

    // Registro para editar uma tarefa existente
    private val editTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val taskId = result.data?.getIntExtra("TASK_ID", -1) ?: return@registerForActivityResult
            val updatedTitle = result.data?.getStringExtra("UPDATED_TITLE") ?: return@registerForActivityResult
            val updatedDescription = result.data?.getStringExtra("UPDATED_DESCRIPTION") ?: return@registerForActivityResult
            val updatedStatus = result.data?.getBooleanExtra("UPDATED_STATUS", false) ?: return@registerForActivityResult

            // Atualizar a tarefa no banco de dados
            lifecycleScope.launch(Dispatchers.IO) {
                val updatedTask = Task(
                    id = taskId,
                    title = updatedTitle,
                    description = updatedDescription,
                    status = if (updatedStatus) "Completed" else "Pending"
                )
                taskRepository.updateTask(updatedTask)
                fetchTasks() // Atualizar a lista de tarefas
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o TaskRepository
        val taskDao = TaskDatabase.getInstance(requireContext()).taskDao()
        taskRepository = TaskRepository(taskDao)

        // Setup do RecyclerView
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_task_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TaskAdapter(mutableListOf(),
            onTaskClick = { task: Task ->
                val intent = Intent(requireContext(), TaskDetailActivity::class.java).apply {
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
                editTaskLauncher.launch(intent) // Lançar a EditTaskActivity e capturar o resultado
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

        // Setup search and filter functionality
        searchEditText = view.findViewById(R.id.et_search_task)
        completedCheckBox = view.findViewById(R.id.cb_completed)
        pendingCheckBox = view.findViewById(R.id.cb_pending)

        // Initial checkbox states
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


        // Botão para adicionar nova tarefa
        val addTaskButton = view.findViewById<ImageButton>(R.id.btn_add_task)
        addTaskButton.setOnClickListener {
            val intent = Intent(requireContext(), AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)  // Lançar a AddTaskActivity e capturar o resultado
        }
    }

    // Fetch tasks from the database
    private fun fetchTasks() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val tasks = taskRepository.getAllTasks()
            val filteredList = tasks.filter {
                // Aplicar o filtro de acordo com o estado inicial dos checkboxes
                (pendingCheckBox.isChecked && it.status == "Pending") ||
                        (completedCheckBox.isChecked && it.status == "Completed")
            }

            withContext(Dispatchers.Main) {
                adapter.updateTaskList(filteredList)
            }
        }
    }

    // Filter tasks based on search and checkbox states
    private fun filterTaskList(
        searchText: String,
        showCompleted: Boolean,
        showPending: Boolean
    ) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val tasks = taskRepository.getAllTasks()
            val filteredList = tasks.filter {
                it.title.contains(searchText, ignoreCase = true) &&
                        ((showCompleted && it.status == "Completed") || (showPending && it.status == "Pending"))
            }

            withContext(Dispatchers.Main) {
                if (filteredList.isEmpty()) {
                    val toast = Toast.makeText(requireContext(), "No tasks found", Toast.LENGTH_SHORT)
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

    override fun onPause() {
        super.onPause()
        // Limpar o campo de busca ao sair da tela
        searchEditText.setText("")
    }
}
