package tr.igb.todoapp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    id: Long, viewModel: TaskViewModel, navController: NavController
) {
    val topAppText: String
    if (id != 0L) {
        val taskState =
            viewModel.getTaskById(id).collectAsState(initial = Task(0L, "", "", 0, false))
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
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "$topAppText Task") }) },

        ) { it ->
        val context = LocalContext.current
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        TaskTextField(label = "Title", value = viewModel.taskTitleState) {
                            viewModel.onTaskTitleChanged(it)
                        }
                        TaskTextField(
                            label = "Description", value = viewModel.taskDescriptionState
                        ) {
                            viewModel.onTaskDescriptionChanged(it)
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Priority", fontWeight = FontWeight.Bold)
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
                    }
                    Column {
                        if (id != 0L) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = viewModel.taskIsCompletedState,
                                    onCheckedChange = { isChecked ->
                                        viewModel.onTaskIsCompletedChanged(isChecked)
                                    })
                                Text(text = "Is Completed?")
                            }
                        }
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        run {
                            if (viewModel.taskTitleState.trim().isNotEmpty()) {
                                if (id == 0L) {
                                    viewModel.addTask(
                                        Task(
                                            title = viewModel.taskTitleState.trim(),
                                            description = viewModel.taskDescriptionState,
                                            priority = viewModel.taskPriorityState,
                                            isCompleted = false
                                        )
                                    )
                                    Toast.makeText(context, "Task created", Toast.LENGTH_SHORT)
                                        .show()
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
                                    Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                navController.navigateUp()
                            } else {
                                Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = topAppText)
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { navController.navigateUp() },
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = "Cancel")
                }
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onSelectedChange(option) },
                )
                Text(text = option)
            }
        }
    }
}