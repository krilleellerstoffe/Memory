package se.mau.al0038.memory.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.ui.viewModel.HighScoreInputViewModel

@Composable
fun HighScoreDialog(
    highScoreInputViewModel: HighScoreInputViewModel = viewModel(),
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
                TextField(value = "", onValueChange = {})
            },
            confirmButton = {
                Button(onClick = { onViewHighScore() }) {
                    Text(text = "View High score")
                }
            },
            dismissButton = {
                Button(onClick = { onDismissRequest() }) {
                    Text(text = "Don't save")
                }
            },
        )
    }
}