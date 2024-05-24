package se.mau.al0038.memory.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.data.Settings

class StartScreenViewModel: ViewModel() {

    var gameSettings by mutableStateOf(Settings())

    fun setGameSettingDifficulty(difficulty: Difficulty) {
        gameSettings = gameSettings.copy(
            difficulty = difficulty
        )
    }

}