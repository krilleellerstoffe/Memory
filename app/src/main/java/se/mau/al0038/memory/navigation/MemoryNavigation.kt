package se.mau.al0038.memory.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.data.Settings
import se.mau.al0038.memory.ui.GameScreen
import se.mau.al0038.memory.ui.HighScoreScreen
import se.mau.al0038.memory.ui.StartScreen

@Composable
fun MemoryNavHost(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = "Start"
    ) {

        composable(route = "Start") {
            StartScreen(
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
                navArgument("playerCount") { type = NavType.IntType }
            )
        ) {
            GameScreen(
                onBackButtonClick = { navController.popBackStack("Start", false) },
                onViewHighScore = { navController.navigate("HighScore") },
                settings = Settings(
                    it.arguments?.getInt("playerCount") ?: 0,
                    Difficulty.valueOf(it.arguments?.getString("difficulty") ?: "Easy")
                )
            )
        }

        composable(route = "Settings") {
            SettingsScreen(
                onBackButtonClick = { navController.popBackStack("Start", false) }
            )
        }

        composable(route = "HighScore") {
            HighScoreScreen(onBackClick = { navController.popBackStack("Start", false) })
        }
    }
}