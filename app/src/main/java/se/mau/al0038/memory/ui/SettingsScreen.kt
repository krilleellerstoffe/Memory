package se.mau.al0038.memory.ui

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
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
        SettingsBody(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun SettingsBody(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    LaunchedEffect(scrollState) {
        Log.d("SettingsLaunchedEffect", "Scroll to: ${scrollState.maxValue}")
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = tween(durationMillis = 10000, easing = EaseIn)
        )
    }

    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.languageHeader))
        val localeOptions = mapOf(
            R.string.en to "en",
            R.string.sv to "sv"
        ).mapKeys { stringResource(it.key) }

        localeOptions.keys.forEach { locale ->
            Button(
                onClick = {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(localeOptions[locale])
                    )
                }
            ) {
                Text(text = locale)
            }
        }
        Card(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(id = R.string.about),
                Modifier.verticalScroll(state = scrollState)
            )
        }
    }
}

@Composable
@Preview
fun PreviewSettingsBody() {
    SettingsBody()
}