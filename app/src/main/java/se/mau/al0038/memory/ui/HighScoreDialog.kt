package se.mau.al0038.memory.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "High score!") },
            text = {
                TextField(value = highScoreInputViewModel.playerName, onValueChange = { highScoreInputViewModel.playerName = it })
            },
            confirmButton = {
                Button(onClick = { highScoreInputViewModel.insertNewHighScore(playerStats); onViewHighScore() }) {
                    Text(text = "Save High Score")
                }
            },
            dismissButton = {
                Button(onClick = { onDismissRequest() }) {
                    Text(text = "Don't Save")
                }
            },
        )
    }
}