package tr.igb.todoapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    viewModel: TaskViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") {
            HomeScreen(navController, viewModel)
        }
        composable("AddTask") {
            AddTask(0, viewModel, navController)
        }
    }
}