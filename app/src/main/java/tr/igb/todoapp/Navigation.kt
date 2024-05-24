package tr.igb.todoapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(
    viewModel: TaskViewModel = viewModel(),
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