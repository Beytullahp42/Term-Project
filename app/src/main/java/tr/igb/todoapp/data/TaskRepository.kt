package tr.igb.todoapp.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getAllTasksSortedByPriority(): Flow<List<Task>> = taskDao.getAllTasksSortedByPriority()

    fun getTaskById(id: Long): Flow<Task> {
        return taskDao.getTaskById(id)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

}
