package se.mau.al0038.memory.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.R
import se.mau.al0038.memory.data.highscore.HighScore
import se.mau.al0038.memory.ui.viewModel.HighScoreScreenViewModel
import java.lang.StringBuilder

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
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(highScoreScreenViewModel.highScores) {
                HighScoreItem(highScore = it)
            }
        }
    }
}

@Composable
fun HighScoreItem(
    highScore: HighScore,
) {
    Column {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.light_blue)
            ),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Text(
                text = buildString {
                    append("Name: ${highScore.name}\n")
                    append("Score: ${highScore.score}\n")
                    append("Attempts: ${highScore.attempts}\n")
                    append("Highest Streak: ${highScore.maxStreak}")
                },
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
        Divider(modifier = Modifier.padding(vertical = 6.dp))
    }
}