package tr.igb.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    id: Long, viewModel: TaskViewModel, navController: NavController
) {
    var task = Task(0L, "", "", 0, false)
    val topAppText: String
    if (id != 0L) {
        val taskState =
            viewModel.getTaskById(id).collectAsState(initial = Task(0L, "", "", 0, false))
        task = taskState.value
        viewModel.taskTitleState = taskState.value.title
        viewModel.taskDescriptionState = taskState.value.description
        viewModel.taskPriorityState = taskState.value.priority
        viewModel.taskIsCompletedState = taskState.value.isCompleted

        topAppText = "Update"
    } else {
        viewModel.taskTitleState = ""
        viewModel.taskDescriptionState = ""
        viewModel.taskPriorityState = 1
        topAppText = "Create"
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "$topAppText Task") }) },

        ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskTextField(label = "Title", value = viewModel.taskTitleState) {
                viewModel.onTaskTitleChanged(it)
            }
            TaskTextField(label = "Description", value = viewModel.taskDescriptionState) {
                viewModel.onTaskDescriptionChanged(it)
            }
            Text(text = "Priority")
            RadioGroup(options = listOf("Low", "Medium", "High"),
                selectedOption = when (viewModel.taskPriorityState) {
                    1 -> "Low"
                    2 -> "Medium"
                    3 -> "High"
                    else -> "Low"
                },
                onSelectedChange = { selectedOption ->
                    viewModel.onTaskPriorityChanged(
                        when (selectedOption) {
                            "Low" -> 1
                            "Medium" -> 2
                            "High" -> 3
                            else -> 1
                        }
                    )
                })
            if (id != 0L) {
                Row {
                    Checkbox(checked = viewModel.taskIsCompletedState,
                        onCheckedChange = { isChecked ->
                            viewModel.onTaskIsCompletedChanged(isChecked)
                        })
                    Text(text = "Is Completed?")
                }
            }
            Button(onClick = {
                run {
                    if (id == 0L) {
                        viewModel.addTask(
                            Task(
                                title = viewModel.taskTitleState,
                                description = viewModel.taskDescriptionState,
                                priority = viewModel.taskPriorityState,
                                isCompleted = false
                            )
                        )
                    } else {
                        viewModel.updateTask(
                            Task(
                                id = id,
                                title = viewModel.taskTitleState,
                                description = viewModel.taskDescriptionState,
                                priority = viewModel.taskPriorityState,
                                isCompleted = viewModel.taskIsCompletedState
                            )
                        )
                    }
                    navController.navigateUp()
                }
            }) {
                Text(text = topAppText)
            }
            Button(onClick = { navController.navigateUp() }) {
                Text(text = "Cancel")
            }
        }
    }
}


@Composable
fun TaskTextField(
    label: String, value: String, onValueChanged: (String) -> Unit
) {
    OutlinedTextField(value = value, onValueChange = onValueChanged, label = { Text(text = label) })
}

@Composable
fun RadioGroup(
    options: List<String>, selectedOption: String, onSelectedChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            Column {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onSelectedChange(option) },
                )
                Text(text = option)
            }
        }
    }
}