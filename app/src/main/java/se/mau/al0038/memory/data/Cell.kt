package se.mau.al0038.memory.data

import androidx.compose.ui.graphics.ImageBitmap

data class Cell(
    val image: ImageBitmap?,
    val style: String,
    val isFlipped: Boolean
)