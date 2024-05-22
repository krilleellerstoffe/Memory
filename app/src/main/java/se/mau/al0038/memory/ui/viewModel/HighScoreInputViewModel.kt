package se.mau.al0038.memory.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import se.mau.al0038.memory.data.PlayerStats
import se.mau.al0038.memory.data.highscore.HighScore
import se.mau.al0038.memory.data.highscore.HighScoreRepository
import javax.inject.Inject


@HiltViewModel
class HighScoreInputViewModel @Inject constructor(
    //Hilt Impl
    private val repository: HighScoreRepository
): ViewModel() {

    var newHighScore by mutableStateOf(false)
    var playerName by mutableStateOf("")

    private val highScores = mutableStateListOf<HighScore>()

    companion object {
        const val HIGH_SCORE_NUM_ENTRIES: Int = 10
    }

    init {
        viewModelScope.launch {
            repository.getHighScoreHistory().collect { highScores.addAll(it) }
        }
    }

    fun evaluateIfNewHighScore(newPlayerScore: Int) {
        if (highScores.count() < HIGH_SCORE_NUM_ENTRIES) {
            newHighScore = true
            return
        }
        newHighScore = highScores.find { it.score < newPlayerScore } !== null
    }

    fun insertNewHighScore(playerStats: PlayerStats) {
        val highScore: HighScore = HighScore.fromPlayerStats(playerName, playerStats)

        viewModelScope.launch {
            repository.insertHighScore(highScore)
        }
    }
}