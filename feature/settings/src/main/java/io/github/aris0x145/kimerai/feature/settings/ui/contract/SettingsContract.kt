package io.github.aris0x145.kimerai.feature.settings.ui.contract

import io.github.aris0x145.kimerai.domain.model.AppTheme
import io.github.aris0x145.kimerai.domain.model.Language

/**
 * 設置功能的 MVI 契約
 */
class SettingsContract {
    /**
     * 設置界面的狀態
     */
    data class State(
        val language: Language = Language.SYSTEM_DEFAULT,
        val theme: AppTheme = AppTheme.SYSTEM_DEFAULT,
        val isDynamicColorEnabled: Boolean = true,
        val isLoading: Boolean = false,
        val error: String? = null
    )
    
    /**
     * 用戶對設置界面的意圖
     */
    sealed interface Intent {
        object LoadSettings : Intent
        data class SetLanguage(val language: Language) : Intent
        data class SetTheme(val theme: AppTheme) : Intent
        data class SetDynamicColorEnabled(val enabled: Boolean) : Intent
        object RestoreDefaultSettings : Intent
    }
    
    /**
     * 設置界面的 UI 效果
     */
    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data class ShowToast(val message: String) : Effect
        object RestartApp : Effect
    }
}