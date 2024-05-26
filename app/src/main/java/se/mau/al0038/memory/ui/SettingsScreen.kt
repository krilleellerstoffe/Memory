package se.mau.al0038.memory.ui

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import se.mau.al0038.memory.MemoryTopBar
import se.mau.al0038.memory.R
import se.mau.al0038.memory.data.Language
import se.mau.al0038.memory.data.Mode
import se.mau.al0038.memory.ui.viewModel.SettingsViewModel

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
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
        SettingsBody(
            settingsViewModel = settingsViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SettingsBody(
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(scrollState) {
        val maxScroll = scrollState.maxValue
        Log.d("SettingsLaunchedEffect", "Scroll to: $maxScroll")
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = tween(
                durationMillis = (maxScroll / 25) * 1000,
                easing = EaseIn,
                delayMillis = 1000
            )
        )
    }

    Background()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LanguagePicker(settingsViewModel = settingsViewModel)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DarkModeSwitch(settingsViewModel)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background.copy(
                    alpha = 0.5f
                )
            ),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.about),
                Modifier.verticalScroll(state = scrollState)
            )
        }
    }
}

@Composable
fun LanguagePicker(
    settingsViewModel: SettingsViewModel
) {
    val localeOptions = mapOf(
        R.string.system_default to "",
        R.string.english to "en",
        R.string.swedish to "sv"
    )

    SettingsDropdown(
        label = stringResource(id = R.string.blank_string),
        items = localeOptions.keys.toList(),
        selectedItem = settingsViewModel.gameSettings.language.stringId,
        onItemSelected = {
            settingsViewModel.gameSettings = settingsViewModel.gameSettings.copy(
                language = Language.fromStringId(it)
            )
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeOptions[it])
            )
        }
    )
}

@Composable
fun DarkModeSwitch(settingsViewModel: SettingsViewModel) {
    val options = mapOf(
        R.string.system_default to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        R.string.light_mode to AppCompatDelegate.MODE_NIGHT_NO,
        R.string.dark_mode to AppCompatDelegate.MODE_NIGHT_YES,
    )

    SettingsDropdown(
        label = stringResource(id = R.string.blank_string),
        items = options.keys.toList(),
        selectedItem = settingsViewModel.gameSettings.mode.stringId,
        onItemSelected = {
            settingsViewModel.gameSettings = settingsViewModel.gameSettings.copy(
                mode = Mode.fromStringId(it)
            )
            options[it]?.let { it1 -> AppCompatDelegate.setDefaultNightMode(it1) }
        }
    )
}

@Composable
@Preview
fun PreviewSettingsBody() {
    SettingsBody(settingsViewModel = viewModel())
}