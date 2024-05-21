package se.mau.al0038.memory.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import se.mau.al0038.memory.data.PlayerStats

class HighScoreInputViewModel: ViewModel() {

    var newHighScore by mutableStateOf(false)
    private val highScores = mutableStateListOf<PlayerStats>()

    companion object {
        const val HIGH_SCORE_NUM_ENTRIES: Int = 10
    }

    init {
        getStatsList()
    }

    fun evaluateIfNewHighScore(newPlayerScore: Int) {
        if (highScores.count() < HIGH_SCORE_NUM_ENTRIES) {
            newHighScore = true
            return
        }
        newHighScore = highScores.find { it.score < newPlayerScore } !== null
    }

    private fun getStatsList() {
        highScores.add(PlayerStats(1,1,1))
    }
}