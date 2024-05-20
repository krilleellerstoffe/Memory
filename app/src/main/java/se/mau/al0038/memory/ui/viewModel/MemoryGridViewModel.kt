package se.mau.al0038.memory.ui.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.data.Grid
import se.mau.al0038.memory.data.Settings

class MemoryGridViewModel: ViewModel() {

    var gameSettings by mutableStateOf(Settings())
    var gridState by mutableStateOf(Grid(Array(4) {
        Array(4) {
            Cell(
                randomImage(),
                "",
                false,
            )
        }
    }))
        private set

    fun randomImage(): ImageVector? {
        return setOf(
            Icons.Filled.Refresh,
            Icons.Filled.Star,
            null
        ).random()
    }
}