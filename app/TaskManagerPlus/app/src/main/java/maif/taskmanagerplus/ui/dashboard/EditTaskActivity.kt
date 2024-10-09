package maif.taskmanagerplus.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import maif.taskmanagerplus.R

class EditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val titleEditText = findViewById<EditText>(R.id.et_edit_task_title)
        val descriptionEditText = findViewById<EditText>(R.id.et_edit_task_description)
        val completedCheckBox = findViewById<CheckBox>(R.id.cb_edit_task_completed)
        val saveButton = findViewById<Button>(R.id.btn_save_task)

        // Get the task data passed from the previous activity
        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION")
        val taskStatus = intent.getBooleanExtra("TASK_STATUS", false)
        val taskPosition = intent.getIntExtra("TASK_POSITION", -1) // Recebe a posição da tarefa

        // Pre-fill the fields with the current task data
        titleEditText.setText(taskTitle)
        descriptionEditText.setText(taskDescription)
        completedCheckBox.isChecked = taskStatus

        saveButton.setOnClickListener {
            val updatedTitle = titleEditText.text.toString()
            val updatedDescription = descriptionEditText.text.toString()
            val updatedStatus = completedCheckBox.isChecked
            val taskId = intent.getIntExtra("TASK_ID", -1) // Pegue o ID da tarefa

            val resultIntent = Intent().apply {
                putExtra("UPDATED_TITLE", updatedTitle)
                putExtra("UPDATED_DESCRIPTION", updatedDescription)
                putExtra("UPDATED_STATUS", updatedStatus)
                putExtra("TASK_POSITION", taskPosition)
                putExtra("TASK_ID", taskId) // Envie o ID da tarefa de volta
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
}
