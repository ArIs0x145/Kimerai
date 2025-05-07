package io.github.aris0x145.kimerai.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.aris0x145.kimerai.core.ui.components.KimeraiAppBar
import io.github.aris0x145.kimerai.domain.model.AppTheme
import io.github.aris0x145.kimerai.domain.model.Language
import io.github.aris0x145.kimerai.feature.settings.R
import io.github.aris0x145.kimerai.feature.settings.ui.components.AppearanceSettings
import io.github.aris0x145.kimerai.feature.settings.ui.components.LanguageSettings
import io.github.aris0x145.kimerai.feature.settings.ui.components.OtherSettings
import io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.Effect
import io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.Intent
import io.github.aris0x145.kimerai.feature.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * 設置畫面
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onRestartApp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is Effect.ShowToast -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is Effect.RestartApp -> {
                    onRestartApp()
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            KimeraiAppBar(
                title = stringResource(id = R.string.settings),
                onNavigateBack = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            SettingsContent(
                state = state,
                onLanguageChange = { language -> 
                    viewModel.handleIntent(Intent.SetLanguage(language)) 
                },
                onThemeChange = { theme -> 
                    viewModel.handleIntent(Intent.SetTheme(theme)) 
                },
                onDynamicColorChange = { enabled -> 
                    viewModel.handleIntent(Intent.SetDynamicColorEnabled(enabled)) 
                },
                onRestoreDefaults = {
                    viewModel.handleIntent(Intent.RestoreDefaultSettings)
                }
            )
        }
    }
}

@Composable
private fun SettingsContent(
    state: io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.State,
    onLanguageChange: (Language) -> Unit,
    onThemeChange: (AppTheme) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit,
    onRestoreDefaults: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 語言設置
        LanguageSettings(
            currentLanguage = state.language,
            onLanguageChange = onLanguageChange
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        
        // 外觀設置
        AppearanceSettings(
            currentTheme = state.theme,
            isDynamicColorEnabled = state.isDynamicColorEnabled,
            onThemeChange = onThemeChange,
            onDynamicColorChange = onDynamicColorChange
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        
        // 其他設置
        OtherSettings(
            onRestoreDefaults = onRestoreDefaults
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsContent(
            state = io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.State(),
            onLanguageChange = {},
            onThemeChange = {},
            onDynamicColorChange = {},
            onRestoreDefaults = {}
        )
    }
}