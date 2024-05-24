package se.mau.al0038.memory.ui.viewModel

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import se.mau.al0038.memory.data.Cell
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.data.Settings
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

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

    private var imageLoader: ImageLoader = ImageLoader(context)
    private val svgDecoderFactory = SvgDecoder.Factory()

    fun generateGrid(settings: Settings) {
        gameSettings = settings

        for (i in 0..< gameSettings.playerCount.count) {
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

                currentPlayer = (currentPlayer + 1) % gameSettings.playerCount.count
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
        val numberOfStylesNeeded = length / 2

        //create random seeds to call api with and use for comparison
        val seeds = listOf(
            "Pixel",
            "Realistic",
            "Cool",
            "Cute",
            "Dark",
            "Bright",
            "Big Ears",
            "Cruudles",
            "Personas",
            "Botts",
            "Dogs"
        )
            .shuffled().subList(0, 2)
        val styles = listOf(
            "adventurer",
            "avataaars",
            "big-ears",
            "big-smile",
            "bottts",
            "croodles",
            "lorelei",
            "micah",
            "miniavs",
            "notionists",
            "open-peeps",
            "personas",
            "pixel-art",
            "thumbs"
        )
            .shuffled().subList(0, numberOfStylesNeeded)
        //fetch images using seeds and add to cells
        val scope = viewModelScope
        scope.launch {
            generateCellsWithApiImages(seeds, styles).forEach { cellList.add(it) }
            Log.d("GameViewModel", "Finished getting images")
        }
    }

//    private suspend fun generateCellsWithApiImages(
//        seeds: List<String>,
//        styles: List<String>,
//        ): List<Cell> {
//        val cellsWithImages: ArrayList<Cell> = ArrayList()
//        styles.forEach{
//            val imgOne = getImage(it, seeds[0])
//            val imgTwo = getImage(it, seeds[1])
//
//            cellsWithImages.add(Cell(imgOne, it, false))
//            cellsWithImages.add(Cell(imgTwo, it, false))
//            Log.d("Requester", "image added to cell with style: $it")
//        }
//        return cellsWithImages.shuffled()
//    }
    /**
     * Fetch async from api
     */
    private suspend fun generateCellsWithApiImages(
        seeds: List<String>,
        styles: List<String>
    ): List<Cell> = coroutineScope {
        val cellsWithImages: ArrayList<Cell> = ArrayList()
        val deferredCells = styles.map { style ->
            async {
                val imgOne = getImage(style, seeds[0])
                val imgTwo = getImage(style, seeds[1])

                listOf(
                    Cell(imgOne, style, false),
                    Cell(imgTwo, style, false)
                )
            }
        }
        deferredCells.forEach { deferred ->
            cellsWithImages.addAll(deferred.await())
        }
        cellsWithImages.shuffled()
    }

    private suspend fun getImage(style: String, seed: String): ImageBitmap? {
        val request = ImageRequest.Builder(context)
            .data("https://api.dicebear.com/8.x/$style/svg?seed=$seed")
            .decoderFactory(SvgDecoder.Factory())
            .build()

        Log.d("Requester", "image requested with style: $style")
        return try {
            val result = (imageLoader.execute(request) as SuccessResult).drawable
            val img = (result as BitmapDrawable).bitmap
            img.asImageBitmap()
        } catch (e: ClassCastException) {
            Log.d("Requester", "Image request failed for Style: $style, Seed: $seed")
            null
        }
    }

    fun resetGame() {
        isGameOver = !isGameOver
        //playerStats.clear()
        //cellList.clear()
    }
}