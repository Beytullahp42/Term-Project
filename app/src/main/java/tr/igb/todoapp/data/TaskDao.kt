package tr.igb.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(task: Task)

    @Query("SELECT * FROM task_table")
    abstract fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    abstract fun getTaskById(id: Long): Flow<Task>

    @Update
    abstract suspend fun update(task: Task)

    @Delete
    abstract suspend fun delete(task: Task)

}