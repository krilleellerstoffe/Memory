package se.mau.al0038.memory.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import se.mau.al0038.memory.R
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.data.Settings
import se.mau.al0038.memory.ui.viewModel.StartScreenViewModel

@Composable
fun StartScreen(
    startScreenViewModel: StartScreenViewModel = viewModel(),
    onStartButtonClick: (Settings) -> Unit,
    onSettingsClick: () -> Unit,
    onHighScoreClick: () -> Unit
) {
    Scaffold { innerPadding ->

        Background()
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .zIndex(1f),
        ) {

            Image(
                painter = painterResource(id = R.drawable.memorylogofore),
                contentDescription = "Memory Logo",
                modifier = Modifier
                    .padding(8.dp)
                    .size(200.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(.5f))
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(160.dp)
                ) {
                    SettingsDropdown(
                        label = "Players",
                        items = listOf("1", "2", "3"),
                        selectedItem = startScreenViewModel.gameSettings.playerCount.toString(),
                        onItemSelected = {
                            startScreenViewModel.gameSettings =
                                startScreenViewModel.gameSettings.copy(playerCount = it.toInt())
                        })

                    SettingsDropdown(
                        label = "Difficulty",
                        items = Difficulty.entries.map { it.name },
                        selectedItem = startScreenViewModel.gameSettings.difficulty.name,
                        onItemSelected = {
                            startScreenViewModel.gameSettings =
                                startScreenViewModel.gameSettings.copy(
                                    difficulty = Difficulty.valueOf(
                                        it
                                    )
                                )
                        })
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onStartButtonClick(startScreenViewModel.gameSettings)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.start_game),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                }
                Spacer(modifier = Modifier.weight(.5f))

            }

            Row {
                Spacer(modifier = Modifier.weight(.5f))

                Button(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(80.dp)
                        .width(150.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(.5f))
                Button(
                    onClick = onHighScoreClick,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(80.dp)
                        .width(150.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.highscore),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(.5f))

            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDropdown(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = label, color = MaterialTheme.colorScheme.onPrimary) },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    expanded = !expanded
                },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
            modifier = Modifier.width(160.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = !expanded
                    }
                )
            }
        }
    }
}

@Composable
fun Background() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.start_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen(
        onStartButtonClick = {},
        onSettingsClick = {},
        onHighScoreClick = {}
    )
}