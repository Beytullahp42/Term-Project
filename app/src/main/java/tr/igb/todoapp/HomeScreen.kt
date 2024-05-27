package tr.igb.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, viewModel: TaskViewModel
) {

    var sortByPriority by remember { mutableStateOf(false) }
    var hideCompletedTasks by remember { mutableStateOf(false) }

    Scaffold(topBar = { TopAppBar(title = { Text(text = "To-Do List") }) }, floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("AddTask/${0L}") }) {
            Text(text = "Create Task")
        }
    }) { it ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(it), verticalArrangement = Arrangement.Top
        ) {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(checked = sortByPriority, onCheckedChange = {
                        sortByPriority = it
                    })
                    Text(text = "Sort by Priority")
                }
                Row {
                    Checkbox(checked = hideCompletedTasks, onCheckedChange = {
                        hideCompletedTasks = it
                    })
                    Text(text = "Hide Completed Tasks")
                }
            }
            val taskList = if (sortByPriority) viewModel.getAllTasksSortedByPriority()
                .collectAsState(initial = listOf()) else viewModel.getAllTasks.collectAsState(
                initial = listOf()
            )
            LazyColumn(
                Modifier.fillMaxSize()
            ) {
                items(taskList.value, key = { task -> task.id }) { task ->
                    if (!hideCompletedTasks || !task.isCompleted) TaskCard(
                        task = task, viewModel, navController
                    )
                }
            }
        }
    }
}

