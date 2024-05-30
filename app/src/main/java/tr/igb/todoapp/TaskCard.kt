package tr.igb.todoapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tr.igb.todoapp.data.Task
import tr.igb.todoapp.ui.theme.Green
import tr.igb.todoapp.ui.theme.Red
import tr.igb.todoapp.ui.theme.Yellow

@Composable
fun TaskCard(task: Task, viewModel: TaskViewModel, navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .background(
                color = if (task.priority == 1) Green else if (task.priority == 2) Yellow else Red,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                navController.navigate("AddTask/${task.id}")
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = task.isCompleted, onCheckedChange = { isChecked ->
                viewModel.updateTask(task.copy(isCompleted = isChecked))
            }, colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Black,
                checkedColor = Color.Transparent,
                checkmarkColor = Color.Black
            )
        )
        Text(text = task.title, color = Color.Black, fontWeight = FontWeight.Bold)
        IconButton(onClick = {
            showDialog.value = true
        }) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Delete Task",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(onDismissRequest = {
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
            })
    }
}