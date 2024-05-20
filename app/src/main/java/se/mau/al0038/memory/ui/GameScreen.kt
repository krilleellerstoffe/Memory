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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.ui.viewModel.MemoryGridViewModel

@Composable
fun GameScreen(
    memoryGridViewModel: MemoryGridViewModel = viewModel(),
)
{
    val gridState = memoryGridViewModel.gridState

    Scaffold(
        topBar = {
            MemoryTopBar(
                onBackClick = {},
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

            for(i in gridState.matrix.indices) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    for (j in gridState.matrix[i].indices) {
                        MemoryButton(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                                gridState.matrix[i][j],
                                {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemoryButton(
    modifier: Modifier,
    cell:Cell,
    onButtonClick: (Cell) -> Unit
){
    Card(
        modifier = modifier.clickable {
            onButtonClick(cell)
        }
    ){
        if(cell.image != null){
            Image(
                imageVector = cell.image,
                contentDescription = null
            )
        }else{
            Image(
                imageVector = Icons.Default.Done,
                contentDescription = null
            )
        }
    }
}