package tr.igb.todoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import tr.igb.todoapp.data.Graph
import tr.igb.todoapp.data.Task
import tr.igb.todoapp.data.TaskRepository

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.taskRepository
) : ViewModel() {
    var taskTitleState by mutableStateOf("")
    var taskDescriptionState by mutableStateOf("")
    var taskPriorityState by mutableIntStateOf(0)
    var taskIsCompletedState by mutableStateOf(false)

    fun onTaskTitleChanged(newString: String) {
        taskTitleState = newString
    }

    fun onTaskDescriptionChanged(newString: String) {
        taskDescriptionState = newString
    }

    fun onTaskPriorityChanged(newInt: Int) {
        taskPriorityState = newInt
    }

    fun onTaskIsCompletedChanged(newBoolean: Boolean) {
        taskIsCompletedState = newBoolean
    }

    lateinit var getAllTasks: Flow<List<Task>>

    init {
        viewModelScope.launch {
            getAllTasks = taskRepository.getAllTasks()
        }
    }

    fun getAllTasksSortedByPriority(): Flow<List<Task>> {
        return taskRepository.getAllTasksSortedByPriority()
    }



    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insert(task)
            taskTitleState = ""
            taskDescriptionState = ""
            taskPriorityState = 0
            taskIsCompletedState = false
        }
    }

    fun getTaskById(id: Long): Flow<Task> {
        return taskRepository.getTaskById(id)
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.delete(task)
            getAllTasks = taskRepository.getAllTasks()
        }
    }
}