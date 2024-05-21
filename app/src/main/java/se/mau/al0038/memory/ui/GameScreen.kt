package se.mau.al0038.memory.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.ui.viewModel.MemoryGridViewModel

@Composable
fun GameScreen(
    memoryGridViewModel: MemoryGridViewModel,
    onBackButtonClick: () -> Unit
)
{
    val cells = remember {
        (memoryGridViewModel.cellList)
    }


    Scaffold(
        topBar = {
            MemoryTopBar(
                onBackClick = onBackButtonClick,
                true,
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
            for(i in (0..<memoryGridViewModel.gameSettings.difficulty.x)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    for (j in (0..<memoryGridViewModel.gameSettings.difficulty.y)) {
                        val clickIndex = index
                        MemoryButton(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                                cell = cells[index],
                                onButtonClick = { memoryGridViewModel.cardFlippedFunction(clickIndex) }
                        )
                        index++
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
    onButtonClick: () -> Unit
){
    Card(

        modifier = modifier.clickable {
            onButtonClick()

        }
    ) {
        if (cell.isFlipped) {
            if (cell.image != null) {
                Image(
                    imageVector = cell.image!!,
                    contentDescription = null
                )
            } else {
                Image(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            }
            Text(text = cell.style)
        } else {
            Image(imageVector = Icons.Default.Menu, contentDescription = null)
        }
    }
}