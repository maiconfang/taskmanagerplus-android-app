package maif.taskmanagerplus.ui.task.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import maif.taskmanagerplus.R

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val titleEditText = findViewById<EditText>(R.id.et_task_title)
        val descriptionEditText = findViewById<EditText>(R.id.et_task_description)
        val completedCheckBox = findViewById<CheckBox>(R.id.cb_task_completed)
        val saveButton = findViewById<Button>(R.id.btn_save_task)

        saveButton.setOnClickListener {
            val taskTitle = titleEditText.text.toString()
            val taskDescription = descriptionEditText.text.toString()
            val isCompleted = completedCheckBox.isChecked

            val resultIntent = Intent().apply {
                putExtra("TASK_TITLE", taskTitle)
                putExtra("TASK_DESCRIPTION", taskDescription)
                putExtra("TASK_STATUS", isCompleted)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close the activity
        }
    }
}
