package se.mau.al0038.memory

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import se.mau.al0038.memory.ui.theme.MemoryTheme

// Hilt Impl
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryTheme {
                MemoryApp()
            }
        }
    }
}
