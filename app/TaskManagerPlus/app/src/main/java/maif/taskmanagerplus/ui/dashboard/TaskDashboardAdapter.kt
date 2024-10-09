package maif.taskmanagerplus.ui.dashboard

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import maif.taskmanagerplus.R
import maif.taskmanagerplus.model.Task

class TaskDashboardAdapter(
    private var tasks: MutableList<Task>, // MutableList to allow task modifications
    private val onTaskClick: (Task) -> Unit // Lambda to handle task click for detail view
) : RecyclerView.Adapter<TaskDashboardAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_dashboard, parent, false)
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
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete") { _, _ ->
                    removeTask(position) // Remove task from list upon confirmation
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun getItemCount(): Int = tasks.size

    // Function to update the task list with new data
    fun updateTaskList(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged() // Refresh the RecyclerView with the new list
    }

    // Function to remove a task from the list
    private fun removeTask(position: Int) {
        tasks.removeAt(position) // Remove the task at the given position
        notifyItemRemoved(position) // Notify RecyclerView of the removal
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_task)

        fun bind(task: Task) {
            val titleTextView = itemView.findViewById<TextView>(R.id.tv_task_title)
            val statusTextView = itemView.findViewById<TextView>(R.id.tv_task_status)
            val editButton = itemView.findViewById<ImageButton>(R.id.btn_edit_task)

            // Set the task details in the view
            titleTextView.text = task.title
            statusTextView.text = if (task.isCompleted) "Completed" else "Pending"

            // Handle edit button click (this can be customized further)
            editButton.setOnClickListener {
                // Logic to edit the task can be implemented here
            }
        }
    }
}
