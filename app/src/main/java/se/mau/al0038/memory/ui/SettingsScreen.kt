package se.mau.al0038.memory.ui

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        val maxScroll = scrollState.maxValue
        Log.d("SettingsLaunchedEffect", "Scroll to: $maxScroll")
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = tween(durationMillis = (maxScroll / 25) * 1000, easing = EaseIn, delayMillis = 1000)
        )
    }

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
            LanguagePicker()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        DarkModeSwitch()
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .weight(1f)) {
            Text(
                text = stringResource(id = R.string.about),
                Modifier.verticalScroll(state = scrollState)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagePicker() {
    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.sv to "sv"
    ).mapKeys { stringResource(it.key) }

    var expanded by remember { mutableStateOf(false) }
    Box {
        TextField(
            readOnly = true,
            enabled = false,
            value = stringResource(R.string.languageHeader),
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier.clickable {
                expanded = !expanded
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            localeOptions.keys.forEach { locale ->
                Log.d("LanguageList", locale)
                DropdownMenuItem(
                    text = { Text(text = locale) },
                    onClick = {
                        expanded = false
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(localeOptions[locale])
                        )
                    }
                )
            }
            DropdownMenuItem(text = { Text(text = stringResource(id = R.string.system_default)) },
                onClick = {
                    expanded = false
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags("")
                    )
                })
        }
    }
}

@Composable
fun DarkModeSwitch() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }, modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(text = stringResource(id = R.string.dark_mode))
        }
        Button(
            onClick = {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }, modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(text = stringResource(id = R.string.light_mode))
        }
        Button(
            onClick = {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }, modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(text = stringResource(id = R.string.system_default))
        }
    }
}

@Composable
@Preview
fun PreviewSettingsBody() {
    SettingsBody()
}