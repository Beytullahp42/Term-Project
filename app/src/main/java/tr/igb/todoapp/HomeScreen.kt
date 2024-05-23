package tr.igb.todoapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "To-Do List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AddTask") }) {
                Text(text = "Create Task")
            }
        }
    ) {
        val taskList = viewModel.getAllTasks.collectAsState(initial = listOf())
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(taskList.value, key = { task -> task.id }) { task ->
                TaskCard(task = task, viewModel)
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, viewModel: TaskViewModel) {
    Row {
        Checkbox(checked = viewModel.taskIsCompletedState, onCheckedChange = {
            viewModel.taskIsCompletedState = !viewModel.taskIsCompletedState
        })
        Text(text = task.title)
        Button(onClick = {
            viewModel.deleteTask(task)
        }) {
            Text(text = "Delete")
        }
    }
}