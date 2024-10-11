package maif.taskmanagerplus.ui.task.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import maif.taskmanagerplus.R

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        // Retrieve the extras from Intent to avoid multiple calls
        val title = intent.getStringExtra("TASK_TITLE") ?: "No Title"
        val description = intent.getStringExtra("TASK_DESCRIPTION") ?: "No Description"
        val status = intent.getStringExtra("TASK_STATUS") ?: "Pending" // Default value for undefined status

        // Set the text in TextViews
        findViewById<TextView>(R.id.tv_task_detail_title).text = title
        findViewById<TextView>(R.id.tv_task_detail_description).text = description

        // Set the status text and color based on the task status
        val statusTextView = findViewById<TextView>(R.id.tv_task_detail_status)
        statusTextView.text = status

        // Using when for a clearer control structure
        when (status) {
            "Completed" -> {
                statusTextView.setTextColor(ContextCompat.getColor(this, R.color.green)) // Green for completed
            }
            "Pending" -> {
                statusTextView.setTextColor(ContextCompat.getColor(this, R.color.red)) // Red for pending
            }
            else -> {
                statusTextView.setTextColor(ContextCompat.getColor(this, R.color.gray)) // Default color for other cases
            }
        }
    }
}
