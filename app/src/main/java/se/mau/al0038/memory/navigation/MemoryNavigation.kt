package se.mau.al0038.memory.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.data.PlayerCount
import se.mau.al0038.memory.data.Settings
import se.mau.al0038.memory.ui.GameScreen
import se.mau.al0038.memory.ui.HighScoreScreen
import se.mau.al0038.memory.ui.SettingsScreen
import se.mau.al0038.memory.ui.StartScreen
import se.mau.al0038.memory.ui.viewModel.SettingsViewModel

@Composable
fun MemoryNavHost(
    navController: NavHostController = rememberNavController(),
    settingsViewModel: SettingsViewModel = viewModel()
) {

    NavHost(
        navController = navController,
        startDestination = "Start"
    ) {

        composable(route = "Start") {
            StartScreen(
                settingsViewModel = settingsViewModel,
                onStartButtonClick = {
                    navController.navigate("Game/${it.difficulty.name}/${it.playerCount}")
                },
                onSettingsClick = { navController.navigate("Settings") },
                onHighScoreClick = { navController.navigate("HighScore") }
            )
        }

        composable(
            route = "Game/{difficulty}/{playerCount}",
            arguments = listOf(navArgument("difficulty") { type = NavType.StringType },
                navArgument("playerCount") { type = NavType.StringType }
            )
        ) {
            GameScreen(
                onBackButtonClick = { navController.popBackStack("Start", false) },
                onViewHighScore = { navController.navigate("HighScore") },
                settings = Settings(
                    PlayerCount.valueOf(it.arguments?.getString("playerCount") ?: "One"),
                    Difficulty.valueOf(it.arguments?.getString("difficulty") ?: "Easy")
                )
            )
        }

        composable(route = "Settings") {
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                onBackButtonClick = { navController.popBackStack("Start", false) }
            )
        }

        composable(route = "HighScore") {
            HighScoreScreen(onBackClick = { navController.popBackStack("Start", false) })
        }
    }
}