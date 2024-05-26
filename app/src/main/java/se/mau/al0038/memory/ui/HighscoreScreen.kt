package se.mau.al0038.memory.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.R
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
                title = { Text(text = stringResource(id = R.string.highscore)) }
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
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Text(
                text = String.format(
                    stringResource(id = R.string.highscore_card),
                    highScore.name,
                    highScore.score,
                    highScore.attempts
                ),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
        Divider(modifier = Modifier.padding(vertical = 6.dp))
    }
}