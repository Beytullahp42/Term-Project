package tr.igb.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, viewModel: TaskViewModel
) {

    var sortByPriority by remember { mutableStateOf(false) }
    var hideCompletedTasks by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "To-Do", textAlign = TextAlign.Center
            )
        })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { navController.navigate("AddTask/${0L}") }) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add Task",
                modifier = Modifier.size(32.dp)
            )
            Text(text = "Add Task", modifier = Modifier.padding(8.dp))
        }
    }) { it ->
        val taskList = if (sortByPriority) viewModel.getAllTasksSortedByPriority()
            .collectAsState(initial = listOf()) else viewModel.getAllTasks.collectAsState(
            initial = listOf()
        )
        if (taskList.value.isEmpty()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Empty List",
                    modifier = Modifier.size(128.dp),
                    tint = LocalContentColor.current
                )
                Text(
                    text = "No tasks added.",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = LocalContentColor.current
                )
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it), verticalArrangement = Arrangement.Top
            ) {
                Column(Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = FloatingActionButtonDefaults.containerColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(0.dp, 0.dp, 8.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = sortByPriority, onCheckedChange = {
                                sortByPriority = it
                            })
                            Text(text = "Sort by Priority")
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = hideCompletedTasks, onCheckedChange = {
                                hideCompletedTasks = it
                            })
                            Text(text = "Hide Completed Tasks")
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    LazyColumn(
                        Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
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
    }
}

