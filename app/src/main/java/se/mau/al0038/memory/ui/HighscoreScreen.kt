package se.mau.al0038.memory.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.data.highscore.HighScore
import se.mau.al0038.memory.ui.viewModel.HighScoreScreenViewModel

@Composable
fun HighScoreScreen(
    highScoreScreenViewModel: HighScoreScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    BackHandler(
        enabled = true,
        onBack = onBackClick
    )
    Scaffold(
        topBar = {
            MemoryTopBar(
                onBackClick = onBackClick,
                true,
                title = { Text(text = "High Scores") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            items(highScoreScreenViewModel.highScores) {
                HighScoreItem(highScore = it)
            }
        }
    }
}

@Composable
fun HighScoreItem(
    highScore: HighScore
) {
    Card {
        Column {
            Text(text = highScore.name)
            Text(text = highScore.score.toString())
            Text(text = highScore.attempts.toString())
            Text(text = highScore.maxStreak.toString())
        }
    }
}