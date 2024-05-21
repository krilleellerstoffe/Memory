package se.mau.al0038.memory.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.data.highscore.HighScoreRepository
import javax.inject.Inject


@HiltViewModel
class HighScoreInputViewModel @Inject constructor(
    //Hilt Impl
    private val highScoreRepository: HighScoreRepository
): ViewModel() {

    var newHighScore by mutableStateOf(false)
    private val highScores = mutableStateListOf<PlayerStats>()

    companion object {
        const val HIGH_SCORE_NUM_ENTRIES: Int = 10
    }

    init {
        addElementsToMutableList(highScoreRepository.getHighScoreHistory())
    }

    fun evaluateIfNewHighScore(newPlayerScore: Int) {
        if (highScores.count() < HIGH_SCORE_NUM_ENTRIES) {
            newHighScore = true
            return
        }
        newHighScore = highScores.find { it.score < newPlayerScore } !== null
    }

    private fun addElementsToMutableList(highScoreHistory: List<PlayerStats>) {
        highScoreHistory.forEach {
            highScores.add(it)
        }
    }
}