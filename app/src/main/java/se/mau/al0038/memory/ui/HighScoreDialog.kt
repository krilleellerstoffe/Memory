package se.mau.al0038.memory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import se.mau.al0038.memory.R
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.ui.viewModel.HighScoreInputViewModel

@Composable
fun HighScoreDialog(
    //Hilt Impl
    highScoreInputViewModel: HighScoreInputViewModel = hiltViewModel(),
    playerStats: PlayerStats,
    onDismissRequest: () -> Unit,
    onViewHighScore: () -> Unit
) {
    highScoreInputViewModel.evaluateIfNewHighScore(playerStats.score)

    if (highScoreInputViewModel.newHighScore) {
        Box(
            contentAlignment = Alignment.Center
        ){
            AlertDialog(
                onDismissRequest = { onDismissRequest() },
                title = { Text(text = "High score!") },
                text = {
                    TextField(value = highScoreInputViewModel.playerName, onValueChange = { highScoreInputViewModel.playerName = it })
                },
                confirmButton = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Button(
                            onClick = { highScoreInputViewModel.insertNewHighScore(playerStats);onViewHighScore()},
                        ) {
                            Text(text = "Save High Score")
                        }
                    }
                },
                dismissButton = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { onDismissRequest() }
                        ) {
                            Text(text = "Don't Save")
                        }
                    }
                },
                containerColor = colorResource(id = R.color.green)
            )
        }
    }
}