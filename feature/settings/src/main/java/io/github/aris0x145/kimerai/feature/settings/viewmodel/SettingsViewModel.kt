package io.github.aris0x145.kimerai.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.domain.model.AppTheme
import io.github.aris0x145.kimerai.domain.model.Language
import io.github.aris0x145.kimerai.domain.repository.SettingsRepository
import io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.Effect
import io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.Intent
import io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 設置功能的 ViewModel
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()
    
    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()
    
    init {
        handleIntent(Intent.LoadSettings)
    }
    
    /**
     * 處理用戶意圖
     */
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadSettings -> loadSettings()
            is Intent.SetLanguage -> setLanguage(intent.language)
            is Intent.SetTheme -> setTheme(intent.theme)
            is Intent.SetDynamicColorEnabled -> setDynamicColorEnabled(intent.enabled)
            is Intent.RestoreDefaultSettings -> restoreDefaultSettings()
        }
    }
    
    /**
     * 加載設置
     */
    private fun loadSettings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            settingsRepository.getSettings()
                .onSuccess { settings ->
                    _state.update { 
                        it.copy(
                            language = settings.language,
                            theme = settings.theme,
                            isDynamicColorEnabled = settings.isDynamicColorEnabled,
                            isLoading = false
                        ) 
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    _effect.emit(Effect.ShowError(e.message ?: "無法加載設置"))
                }
        }
    }
    
    /**
     * 設置語言
     */
    private fun setLanguage(language: Language) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
                .onSuccess {
                    _state.update { it.copy(language = language) }
                    _effect.emit(Effect.ShowToast("語言設置已更新"))
                    if (language != _state.value.language) {
                        _effect.emit(Effect.RestartApp)
                    }
                }
                .onFailure { e ->
                    _effect.emit(Effect.ShowError(e.message ?: "設置語言失敗"))
                }
        }
    }
    
    /**
     * 設置主題
     */
    private fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
                .onSuccess {
                    _state.update { it.copy(theme = theme) }
                    _effect.emit(Effect.ShowToast("主題設置已更新"))
                }
                .onFailure { e ->
                    _effect.emit(Effect.ShowError(e.message ?: "設置主題失敗"))
                }
        }
    }
    
    /**
     * 設置動態顏色
     */
    private fun setDynamicColorEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDynamicColorEnabled(enabled)
                .onSuccess {
                    _state.update { it.copy(isDynamicColorEnabled = enabled) }
                    _effect.emit(Effect.ShowToast("動態顏色設置已更新"))
                }
                .onFailure { e ->
                    _effect.emit(Effect.ShowError(e.message ?: "設置動態顏色失敗"))
                }
        }
    }
    
    /**
     * 恢復默認設置
     */
    private fun restoreDefaultSettings() {
        viewModelScope.launch {
            settingsRepository.restoreDefaultSettings()
                .onSuccess {
                    loadSettings() // 重新加載默認設置
                    _effect.emit(Effect.ShowToast("已恢復默認設置"))
                    _effect.emit(Effect.RestartApp)
                }
                .onFailure { e ->
                    _effect.emit(Effect.ShowError(e.message ?: "恢復默認設置失敗"))
                }
        }
    }
}