package se.mau.al0038.memory.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.mau.al0038.memory.ui.GameScreen
import se.mau.al0038.memory.ui.HighScoreScreen
import se.mau.al0038.memory.ui.StartScreen
import se.mau.al0038.memory.ui.viewModel.GameViewModel

@Composable
fun MemoryNavHost(
    navController: NavHostController = rememberNavController(),
    memoryGridViewModel: GameViewModel = viewModel()
){

    NavHost(
        navController = navController,
        startDestination = "Start"
    ){

        composable(route = "Start") {
            StartScreen(memoryGridViewModel = memoryGridViewModel, onStartButtonClick = { navController.navigate("Game") })
        }

        composable(route = "Game") {
            GameScreen(
                gameViewModel = memoryGridViewModel,
                onBackButtonClick = { navController.popBackStack("Start", false) },
                onViewHighScore = { navController.navigate("HighScore") })
        }

        composable(route = "Settings"){

        }

        composable(route = "Summary"){

        }

        composable(route = "HighScore"){
            HighScoreScreen()
        }
    }
}