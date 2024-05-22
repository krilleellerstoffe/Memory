package se.mau.al0038.memory.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.ui.viewModel.GameViewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    onBackButtonClick: () -> Unit,
    onViewHighScore: () -> Unit
)
{
    val cells = remember {
        (gameViewModel.cellList)
    }

    var showHighScore by remember {
        mutableStateOf(true)
    }

    if (gameViewModel.isGameOver) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(text = "GameOver") },
            confirmButton = { Button(onClick = { onViewHighScore() }) {
                Text(text = "View High score")
            }},
            dismissButton = {
                Button(onClick = {
                    onBackButtonClick()
                    gameViewModel.resetGame()
                    //clear viewmodel
                }

                ) {
                Text(text = "Back to menu")
            }},
        )

        if (showHighScore && gameViewModel.gameSettings.playerCount == 1) {
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
                title = { Text(text = "Player:${gameViewModel.currentPlayer}" +
                        "  Attempts:${gameViewModel.playerStats[gameViewModel.currentPlayer].attempts}" +
                        "  Score:${gameViewModel.playerStats[gameViewModel.currentPlayer].score}")}
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
        ) {

            var index = 0
            Log.d("Game Screen", "Cells count : ${cells.count()}")
            if(cells.count() == (gameViewModel.gameSettings.difficulty.x * gameViewModel.gameSettings.difficulty.y)) {

            for(i in (0..<gameViewModel.gameSettings.difficulty.x)) {

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
                                cardFlipFunction =  gameViewModel::cardFlippedFunction,
                                onFlipFinish = gameViewModel::checkIfMatch

                        )
                        index++
                    }
                }
            }
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
){
    val flipAnimation by animateFloatAsState(
        targetValue = if (cell.isFlipped) 360f else 0f,
        animationSpec = tween(500),
        finishedListener = {
            onFlipFinish()
        }
    )
    ////adjust alpha
    val fadeImageOut by animateFloatAsState(
        targetValue = if(!cell.isFlipped) 1f else 0f,
        animationSpec = tween(500)
    )
    val fadeImageIn by animateFloatAsState(
        targetValue = if(cell.isFlipped) 1f else 0f,
        animationSpec = tween(500)
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
    ) {
        if (cell.isFlipped) {
            if (cell.image != null) {
                Image(
                    bitmap = cell.image,
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        alpha = fadeImageIn
                    }
                )
            } else {
                Image(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        alpha = fadeImageIn
                    }
                )
            }
            Text(text = cell.style,
                modifier = Modifier.graphicsLayer {
                    alpha = fadeImageIn
                })
        } else {
            Image(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    alpha = fadeImageOut
                }
            )
        }
    }
}