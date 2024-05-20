package se.mau.al0038.memory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import se.mau.al0038.memory.R

@Composable
fun StartScreen(
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
                TextField(value = "", onValueChange = {})
            }

            Button(
                onClick = {
                    onStartButtonClick(
                        //TODO
                    )
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