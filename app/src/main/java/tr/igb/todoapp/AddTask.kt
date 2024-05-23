package tr.igb.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    id: Long, viewModel: TaskViewModel, navController: NavController
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Add Task") }) },

        ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskTextField(label = "Title", value = viewModel.taskTitleState) {
                viewModel.onTaskTitleChanged(it)
            }
            TaskTextField(label = "Description", value = viewModel.taskDescriptionState) {
                viewModel.onTaskDescriptionChanged(it)
            }
            Text(text = "Priority")
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.taskPriorityState = 1 }) {
                    Text(text = "Low")
                }
                Button(onClick = { viewModel.taskPriorityState = 2 }) {
                    Text(text = "Medium")
                }
                Button(onClick = { viewModel.taskPriorityState = 3 }) {
                    Text(text = "High")
                }
            }
            Button(onClick = {
                viewModel.addTask(
                    Task(
                        title = viewModel.taskTitleState,
                        description = viewModel.taskDescriptionState,
                        priority = viewModel.taskPriorityState,
                        isCompleted = false
                    )
                )
                navController.navigateUp()
            }) {
                Text(text = "Create")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    }
}


@Composable
fun TaskTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) })
}
