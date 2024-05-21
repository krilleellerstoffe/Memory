package se.mau.al0038.memory.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import se.mau.al0038.memory.R
import se.mau.al0038.memory.data.Difficulty
import se.mau.al0038.memory.ui.viewModel.GameViewModel

@Composable
fun StartScreen(
    memoryGridViewModel: GameViewModel,
    onStartButtonClick: () -> Unit
){
    Scaffold(

    ) {innerPadding->

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ){

                var showPlayerCountDropDown by remember{ mutableStateOf(false) }

                Box {
                    OutlinedTextField(
                        value = memoryGridViewModel.gameSettings.playerCount.toString(),
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.clickable { showPlayerCountDropDown = !showPlayerCountDropDown }
                    )
                    DropdownMenu(expanded = showPlayerCountDropDown, onDismissRequest = { showPlayerCountDropDown = !showPlayerCountDropDown }) {
                       for(i in (1..4)) {
                           DropdownMenuItem(text = { Text(text = i.toString()) }, onClick = {
                               memoryGridViewModel.gameSettings = memoryGridViewModel.gameSettings.copy(
                                   playerCount = i
                               )
                               showPlayerCountDropDown = false
                           })
                       }
                    }
                }

                var showDifficultyDropdown by remember{ mutableStateOf(false) }

                Box {
                    OutlinedTextField(
                        value = memoryGridViewModel.gameSettings.difficulty.toString(),
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.clickable { showDifficultyDropdown = !showDifficultyDropdown }
                    )
                    DropdownMenu(
                        expanded = showDifficultyDropdown,
                        onDismissRequest = { showDifficultyDropdown = !showDifficultyDropdown }) {
                        DropdownMenuItem(text = {Text(text = Difficulty.Easy.toString()) }, onClick = {
                            memoryGridViewModel.setGameSettingDifficulty(Difficulty.Easy)
                            showDifficultyDropdown = !showDifficultyDropdown
                        })
                        DropdownMenuItem(text = { Text(text = Difficulty.Intermediate.toString()) }, onClick = {
                            memoryGridViewModel.setGameSettingDifficulty(Difficulty.Intermediate)
                            showDifficultyDropdown = !showDifficultyDropdown
                        })
                        DropdownMenuItem(text = { Text(text = Difficulty.Hard.toString()) }, onClick = {
                            memoryGridViewModel.setGameSettingDifficulty(Difficulty.Hard)
                            showDifficultyDropdown = !showDifficultyDropdown
                        })
                    }
                }
            }

            Button(
                onClick = {
                    memoryGridViewModel.generateGrid()
                    onStartButtonClick()
                }
            ) {
                Text(text = stringResource(id = R.string.start_game))

            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.highscore))
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.settings))
            }

        }

    }
}