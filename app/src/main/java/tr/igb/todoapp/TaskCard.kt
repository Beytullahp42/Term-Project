package tr.igb.todoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task

@Composable
fun TaskCard(task: Task, viewModel: TaskViewModel, navController: NavController) {
    Row(
        Modifier.fillMaxWidth().clickable {
            navController.navigate("AddTask/${task.id}")
        },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = { isChecked ->
            viewModel.updateTask(task.copy(isCompleted = isChecked))
        })
        Text(text = task.title)
        Button(onClick = {
            viewModel.deleteTask(task)
        }) {
            Text(text = "Delete")
        }
    }
}