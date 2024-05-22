package se.mau.al0038.memory.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

data class Cell(
    val image : ImageBitmap?,
    val style : String,
    val isFlipped: Boolean
)