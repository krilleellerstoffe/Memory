package se.mau.al0038.memory.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.mau.al0038.memory.ui.GameScreen
import se.mau.al0038.memory.ui.StartScreen

@Composable
fun MemoryNavHost(
    navController: NavHostController = rememberNavController()
){

    NavHost(
        navController = navController,
        startDestination = "Start"
    ){

        composable(route = "Start"){
            StartScreen(onStartButtonClick = {navController.navigate("Game")})
        }

        composable(route = "Game"){
            GameScreen()
        }

        composable(route = "Settings"){

        }

        composable(route = "Summary"){

        }

        composable(route = "Highscore"){

        }
    }
}