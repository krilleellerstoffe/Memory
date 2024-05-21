package se.mau.al0038.memory.data

import androidx.compose.ui.graphics.vector.ImageVector

data class Cell(
    val image : ImageVector?,
    val style : String,
    val isFlipped: Boolean
)