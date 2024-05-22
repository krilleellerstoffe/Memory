package se.mau.al0038.memory.ui.viewModel

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.data.Settings
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

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

    fun generateGrid(settings: Settings) {
        gameSettings = settings

        for (i in 0..<gameSettings.playerCount) {
            playerStats.add(PlayerStats())
        }

        getListOfCells()
    }

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



    private fun getListOfCells() {
        val length: Int = gameSettings.difficulty.x * gameSettings.difficulty.y
        val numberOfStylesNeeded = length/2

        //create random seeds to call api with and use for comparison
        val styles = listOf("Pixel", "Realistic", "Cool", "Cute", "Dark", "Bright", "Big Ears", "Cruudles", "Personas", "Botts", "Dogs")
            .shuffled().subList(0, numberOfStylesNeeded)
        //fetch images using seeds and add to cells
        val scope = viewModelScope
        scope.launch{
            getImages(styles).forEach { cellList.add(it) }
            Log.d("GameViewModel", "Finished getting images")
        }
    }

    private suspend fun getImages(styles: List<String>): List<Cell> {
        var cellsWithImages: ArrayList<Cell> = ArrayList()
        styles.forEach{
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data("https://api.dicebear.com/8.x/bottts/svg?seed=$it")
                .decoderFactory(SvgDecoder.Factory())
                .build()

            Log.d("Requester", "image requested with style: $it")
            val result = (loader.execute(request) as SuccessResult).drawable
            val img = (result as BitmapDrawable).bitmap
            val imgBitmap = img.asImageBitmap()
            cellsWithImages.add(Cell(imgBitmap, it, false))
            cellsWithImages.add(Cell(imgBitmap, it, false))
            Log.d("Requester", "image added to cell with style: $it")
        }
        return cellsWithImages.shuffled()
    }
    private fun randomImage(): ImageVector? {
        return setOf(
            Icons.Filled.Refresh,
            Icons.Filled.Star,
            null
        ).random()
    }

    fun resetGame() {
        isGameOver = !isGameOver
        //playerStats.clear()
        //cellList.clear()
    }
}