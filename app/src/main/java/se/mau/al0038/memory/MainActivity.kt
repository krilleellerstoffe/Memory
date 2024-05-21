package se.mau.al0038.memory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import se.mau.al0038.memory.navigation.MemoryNavHost
import se.mau.al0038.memory.ui.theme.MemoryTheme
import se.mau.al0038.memory.ui.viewModel.GameViewModel

// Hilt Impl
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryTheme {
                MemoryApp()
            }
        }
    }
}
