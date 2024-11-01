package maif.taskmanagerplus.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_manager.db"
                )
                    .addCallback(TaskDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TaskDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                // Use CoroutineScope with Dispatcher.IO for database operations
                CoroutineScope(Dispatchers.IO).launch {
                    prePopulateDatabase(database.taskDao())
                }
            }
        }

        suspend fun prePopulateDatabase(taskDao: TaskDao) {
            // Create the 3 initial tasks
            val task1 = Task(
                title = "Task to Edit",
                description = "This task can be edited.",
                status = "Pending"
            )
            val task2 = Task(
                title = "Task to Delete",
                description = "This task can be deleted.",
                status = "Pending"
            )
            val task3 = Task(
                title = "Additional Task",
                description = "This is a random task.",
                status = "Completed"
            )

            // Insert the tasks into the database
            taskDao.insertTask(task1)
            taskDao.insertTask(task2)
            taskDao.insertTask(task3)
        }
    }
}
