package se.mau.al0038.memory.ui.viewModel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.data.Settings

class GameViewModel: ViewModel() {

    var gameSettings by mutableStateOf(Settings())

    var currentPlayer by mutableIntStateOf(0)
    var playerStats = mutableStateListOf<PlayerStats>()
        private set
    var cellList = mutableStateListOf<Cell>()
        private set

    var isGameOver by mutableStateOf(false)
        private set

    private var firstCard: Cell? = null
    private var secondCard: Cell? = null

    fun cardFlippedFunction(i: Int): Boolean {
        //if already two cards flipped
        if (firstCard != null && secondCard != null) {
            Log.d("MemoryGridViewModel", "Already two cards flipped")
            return true
        }
        //else flip card an save it
        val flippedCell = cellList[i].copy(isFlipped = !cellList[i].isFlipped)
        cellList[i] = flippedCell
        if (firstCard == null) {
            Log.d("MemoryGridViewModel", "First card flipped")
            firstCard = flippedCell
        } else {
            secondCard = flippedCell
        }
        return false

    }

    fun checkIfMatch() {
        if (firstCard != null && secondCard != null) {
            playerStats[currentPlayer] = playerStats[currentPlayer].copy(
                attempts = playerStats[currentPlayer].attempts + 1
            )

            if (firstCard!!.style == secondCard!!.style) {
                Log.d("MemoryGridViewModel", "Match")

                playerStats[currentPlayer] = playerStats[currentPlayer].copy(
                    score = playerStats[currentPlayer].score + 1
                )
                resetCards()
            } else {
                Log.d("MemoryGridViewModel", "No match")
                val firstIndex = cellList.indexOf(firstCard)
                val secondIndex = cellList.indexOf(secondCard)
                cellList[firstIndex] = firstCard!!.copy(isFlipped = false)
                cellList[secondIndex] = secondCard!!.copy(isFlipped = false)
                resetCards()

                currentPlayer = (currentPlayer + 1) % gameSettings.playerCount
            }
        }

        isGameOver = cellList.find { !it.isFlipped } == null
    }

    fun resetCards() {
        firstCard = null
        secondCard = null
    }

    fun setGameSettingDifficulty(difficulty: Difficulty) {
        gameSettings = gameSettings.copy(
            difficulty = difficulty
        )
    }

    fun generateGrid() {
        playerStats.clear()

        for (i in 0..<gameSettings.playerCount) {
            playerStats.add(PlayerStats())
        }

        cellList.clear()
        getListOfCells().forEach { cellList.add(it) }
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