package maif.taskmanagerplus.data.model

import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE status = :status")
    suspend fun getTasksByStatus(status: String): List<Task>

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>
}
