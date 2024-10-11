package maif.taskmanagerplus.ui.task.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import maif.taskmanagerplus.R

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val title = intent.getStringExtra("TASK_TITLE")
        val description = intent.getStringExtra("TASK_DESCRIPTION")
        val status = intent.getBooleanExtra("TASK_STATUS", false)

        findViewById<TextView>(R.id.tv_task_detail_title).text = title
        findViewById<TextView>(R.id.tv_task_detail_description).text = description
        findViewById<TextView>(R.id.tv_task_detail_status).text = if (status) "Completed" else "Pending"
    }
}
