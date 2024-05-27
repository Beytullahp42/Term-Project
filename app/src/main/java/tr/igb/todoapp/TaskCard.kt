package tr.igb.todoapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task

@Composable
fun TaskCard(task: Task, viewModel: TaskViewModel, navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("AddTask/${task.id}")
            }
            .background(color = if (task.priority == 1) Color.Green else if (task.priority == 2) Color.Yellow else Color.Red)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = { isChecked ->
            viewModel.updateTask(task.copy(isCompleted = isChecked))
        })
        Text(text = task.title)
        Button(onClick = {
            showDialog.value = true
        }) {
            Text(text = "Delete")
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text(text = "Confirmation") },
            text = { Text("Are you sure you want to delete this task?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteTask(task)
                    showDialog.value = false
                    Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog.value = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}