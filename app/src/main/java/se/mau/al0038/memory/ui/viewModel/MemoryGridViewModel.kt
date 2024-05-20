package se.mau.al0038.memory.ui.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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

    fun cardFlippedFunction(x: Int, y: Int) {
        val newMatrix = gridState.matrix
        newMatrix[x][y].isFlipped = !newMatrix[x][y].isFlipped

        gridState = gridState.copy(
            matrix = newMatrix
        )
    }

    fun setGameSettingDifficulty(difficulty: Difficulty) {
        gameSettings = gameSettings.copy(
            difficulty = difficulty
        )
    }

    fun generateGrid() {
        val cells = getListOfCells()


        val index: Int
        gridState = Grid(Array(gameSettings.difficulty.x) {i ->
            Array(gameSettings.difficulty.y) {j ->
                cells[gameSettings.difficulty.x * i + j]
            }
        })
    }

    private fun getListOfCells(): List<Cell> {
        val length: Int = gameSettings.difficulty.x * gameSettings.difficulty.y
        val numberOfStylesNeeded = length/2

        val styles = listOf("Pixel", "Realistic", "Cool", "Cute", "Dark", "Bright", "Big Ears", "Cruudles", "Personas", "Botts", "Dogs")
            .shuffled().subList(0, numberOfStylesNeeded)
        val returnList: ArrayList<Cell> = ArrayList()

        styles.forEach{
            val img = randomImage()
            returnList.add(Cell(img, it, false))
            returnList.add(Cell(img, it, false))
        }

        return returnList.shuffled()
    }

    private fun randomImage(): ImageVector? {
        return setOf(
            Icons.Filled.Refresh,
            Icons.Filled.Star,
            null
        ).random()
    }
}