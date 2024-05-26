package se.mau.al0038.memory.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.R
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.data.Settings
import se.mau.al0038.memory.ui.viewModel.GameViewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit,
    onViewHighScore: () -> Unit,
    settings: Settings
) {
    LaunchedEffect(true) {
        gameViewModel.generateGrid(settings)
    }

    val cells = remember {
        (gameViewModel.cellList)
    }

    var showHighScore by remember {
        mutableStateOf(true)
    }

    if (gameViewModel.isGameOver) {
        AlertDialog(
            onDismissRequest = { /*DO NOTHING*/ },
            title = { Text(text = stringResource(id = R.string.game_over)) },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { onViewHighScore() }) {
                        Text(text = stringResource(id = R.string.highscore))
                    }
                }
            },
            dismissButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            onBackButtonClick()
                            gameViewModel.resetGame()
                            //clear viewmodel
                        },
                    ) {
                        Text(text = stringResource(id = R.string.back_to_menu))
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.tertiary
        )

        if (showHighScore && gameViewModel.gameSettings.playerCount.count == 1) {
            HighScoreDialog(
                playerStats = gameViewModel.playerStats[0],
                onDismissRequest = { showHighScore = false },
                onViewHighScore = onViewHighScore
            )
        }
    }


    Scaffold(
        topBar = {
            MemoryTopBar(
                onBackClick = onBackButtonClick,
                true,
                title = {
                    val playerStats =
                        gameViewModel.playerStats.getOrElse(gameViewModel.currentPlayer) { PlayerStats() }
                    Text(
                        text = stringResource(
                            R.string.current_player,
                            gameViewModel.currentPlayer + 1
                        ) + " " + stringResource(
                            R.string.attempts,
                            playerStats.attempts
                        ) + " " + stringResource(
                            R.string.score,
                            playerStats.score
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            var index = 0
            Log.d("Game Screen", "Cells count : ${cells.count()}")
            var informationDismissed by remember {
                mutableStateOf(false)
            }
            var loadingFinished by remember {
                mutableStateOf(false)
            }
            if (cells.count() == (gameViewModel.gameSettings.difficulty.x * gameViewModel.gameSettings.difficulty.y)) {
                loadingFinished = true
            }
            if (informationDismissed && loadingFinished) {

                for (i in (0..<gameViewModel.gameSettings.difficulty.x)) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        for (j in (0..<gameViewModel.gameSettings.difficulty.y)) {
                            val clickIndex = index
                            MemoryButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                cell = cells[index],
                                clickIndex = clickIndex,
                                cardFlipFunction = gameViewModel::cardFlippedFunction,
                                onFlipFinish = gameViewModel::checkIfMatch
                            )
                            index++
                        }
                    }
                }
            } else {
                LoadingInformation(
                    onDismissed = {
                        informationDismissed = true
                    },
                    loadingFinished = loadingFinished
                )
            }
        }
    }
}

@Composable
fun LoadingInformation(
    onDismissed: () -> Unit,
    loadingFinished: Boolean
) {
    Box {
        Background()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.info_title), fontSize = 25.sp)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.info_body), fontSize = 20.sp)
            Text(text = stringResource(id = R.string.info_examples), fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = stringResource(id = R.string.info_api_fetching), fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(20.dp))
            if (loadingFinished) {
                Button(onClick = onDismissed) {
                    Text(
                        text = stringResource(id = R.string.click_to_start),
                        style = MaterialTheme.typography.headlineSmall
                    )

                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun MemoryButton(
    modifier: Modifier,
    cell: Cell,
    clickIndex: Int,
    cardFlipFunction: (Int) -> Boolean,
    onFlipFinish: () -> Unit
) {
    val flipAnimation by animateFloatAsState(
        targetValue = if (cell.isFlipped) 360f else 0f,
        animationSpec = tween(500),
        finishedListener = {
            onFlipFinish()
        }, label = "flipAnimation"
    )
    ////adjust alpha
    val fadeImageOut by animateFloatAsState(
        targetValue = if (!cell.isFlipped) 1f else 0f,
        animationSpec = tween(500),
        label = "fadeImageOut"
    )
    val fadeImageIn by animateFloatAsState(
        targetValue = if (cell.isFlipped) 1f else 0f,
        animationSpec = tween(500),
        label = "fadeImageIn"
    )

    Card(
        modifier = modifier
            .clickable {
                if (cell.isFlipped) {
                    return@clickable
                }
                cardFlipFunction(clickIndex)
            }
            .graphicsLayer {
                rotationY = flipAnimation
            }
            .border(2.dp, Color.Black)
    ) {
        Box(
            Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )
                )
        )
        {
            if (cell.isFlipped) {
                if (cell.image != null) {
                    Image(
                        bitmap = cell.image,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = fadeImageIn
                            }
                            .fillMaxSize(),
                        contentScale = ContentScale.FillWidth,
                    )
                } else {
                    Text(
                        text = cell.style,
                        modifier = Modifier.graphicsLayer {
                            alpha = fadeImageIn
                        }
                    )
                }
            } else {
                val darkTheme = isSystemInDarkTheme()
                Image(
                    painter = painterResource(id = if (darkTheme) R.drawable.start_background_night else R.drawable.start_background),
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = fadeImageOut
                        }
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}