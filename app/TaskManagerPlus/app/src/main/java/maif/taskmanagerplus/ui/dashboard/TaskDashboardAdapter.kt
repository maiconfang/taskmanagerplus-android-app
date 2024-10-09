package maif.taskmanagerplus.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import maif.taskmanagerplus.R
import maif.taskmanagerplus.model.Task

class TaskDashboardAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<TaskDashboardAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_dashboard, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    // Function to update the task list
    fun updateTaskList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged() // Refresh the RecyclerView with the new list
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            val titleTextView = itemView.findViewById<TextView>(R.id.tv_task_title)
            val descriptionTextView = itemView.findViewById<TextView>(R.id.tv_task_description)
            val editButton = itemView.findViewById<ImageButton>(R.id.btn_edit_task)
            val deleteButton = itemView.findViewById<ImageButton>(R.id.btn_delete_task)

            titleTextView.text = task.title
            descriptionTextView.text = task.description

            // Handle edit button click
            editButton.setOnClickListener {
                // Add logic to edit task
            }

            // Handle delete button click
            deleteButton.setOnClickListener {
                // Add logic to delete task
            }
        }
    }
}
