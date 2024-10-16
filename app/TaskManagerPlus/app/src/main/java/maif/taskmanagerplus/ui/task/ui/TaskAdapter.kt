package maif.taskmanagerplus.ui.task.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import maif.taskmanagerplus.R
import maif.taskmanagerplus.data.model.Task

class TaskAdapter(
    private var tasks: MutableList<Task>, // MutableList to allow task modifications
    private val onTaskClick: (Task) -> Unit, // Lambda to handle task click for detail view
    private val onEditTask: (Task, Int) -> Unit, // Lambda to handle task edit click
    private val onDeleteTask: (Task, Int) -> Unit // Callback for deleting task
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)

        // Handle the click event to trigger task detail view
        holder.itemView.setOnClickListener {
            onTaskClick(task)
        }

        // Handle delete button click with confirmation
        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Task: ${task.title}?") // Pass the task title dynamically
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete") { _, _ ->
                    onDeleteTask(task, position) // Call the delete callback to remove task from database
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Handle edit button click to open EditTaskActivity
        holder.editButton.setOnClickListener {
            onEditTask(task, position) // Trigger the edit callback to launch edit activity
        }

    }

    override fun getItemCount(): Int = tasks.size

    // Function to update the task list with new data
    fun updateTaskList(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged() // Refresh the RecyclerView with the new list
    }

    // Remove task from the list
    fun removeTaskAt(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_task)
        val editButton: ImageButton = itemView.findViewById(R.id.btn_edit_task)

        // Get the status color view
        val statusColorView: View = itemView.findViewById(R.id.status_color)

        fun bind(task: Task) {
            val titleTextView = itemView.findViewById<TextView>(R.id.tv_task_title)
            val statusTextView = itemView.findViewById<TextView>(R.id.tv_task_status)

            // Set the task details in the view
            titleTextView.text = task.title
            statusTextView.text = if (task.status == "Completed") "Completed" else "Pending"

            // Change the color of the status indicator based on task status
            when (task.status) {
                "Completed" -> statusColorView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.green)
                )
                "Pending" -> statusColorView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.red)
                )
                else -> statusColorView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.gray) // Default color for other statuses
                )
            }

        }
    }
}
