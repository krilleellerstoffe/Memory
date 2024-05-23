package se.mau.al0038.memory.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.R

@Composable
fun SettingsScreen(
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            MemoryTopBar(
                onBackClick = onBackButtonClick,
                true,
                title = {
                    Text(
                        text = stringResource(R.string.settings)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = stringResource(id = R.string.languageHeader))
            Button(onClick = {
            }) {
                Text("Eng/Sv")
            }
            Text(
                text = stringResource(id = R.string.about),
                Modifier.verticalScroll(ScrollState(0))
            )
        }
    }
}