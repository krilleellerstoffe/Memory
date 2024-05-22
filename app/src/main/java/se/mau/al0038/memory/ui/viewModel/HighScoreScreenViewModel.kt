package se.mau.al0038.memory.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import se.mau.al0038.memory.data.highscore.HighScore
import se.mau.al0038.memory.data.highscore.HighScoreRepository
import javax.inject.Inject

@HiltViewModel
class HighScoreScreenViewModel @Inject constructor(
    repository: HighScoreRepository
) : ViewModel() {

    val highScores = mutableStateListOf<HighScore>()

    init {
        viewModelScope.launch {
            repository.getHighScoreHistory().collect { highScores.addAll(it) }
        }
    }
}