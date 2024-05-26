package se.mau.al0038.memory

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import se.mau.al0038.memory.navigation.MemoryNavHost

@Composable
fun MemoryApp(

) {
    MemoryNavHost()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryTopBar(
    onBackClick: () -> Unit,
    canNavigateBack: Boolean,
    title: @Composable () -> Unit = { Text(stringResource(id = R.string.app_name)) }
) {
    TopAppBar(
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        title = title,
    )
}