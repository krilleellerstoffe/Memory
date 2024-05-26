package se.mau.al0038.memory.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import se.mau.al0038.memory.data.Settings

class SettingsViewModel : ViewModel() {

    var gameSettings by mutableStateOf(Settings())

}