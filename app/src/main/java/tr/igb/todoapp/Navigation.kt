package tr.igb.todoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import tr.igb.todoapp.data.TaskDatabase

@Composable
fun Navigation(
    viewModel: TaskViewModel = TaskViewModel(
        database = Room.databaseBuilder(
            LocalContext.current,
            TaskDatabase::class.java,
            "task.db"
        ).build()
    ),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = "Home"
    ) {
        composable("Home") {
            HomeScreen(navController, viewModel)
        }
        composable("AddTask/{id}", arguments = listOf(navArgument("id") {
            type = NavType.LongType
            defaultValue = 0L
            nullable = false
        })) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddTask(id, viewModel, navController)
        }
    }
}