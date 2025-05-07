package io.github.aris0x145.kimerai.feature.toolhub.ui.contract

import io.github.aris0x145.kimerai.core.ui.base.MviEffect
import io.github.aris0x145.kimerai.core.ui.base.MviIntent
import io.github.aris0x145.kimerai.core.ui.base.MviState

/**
 * 工具中心功能的 MVI 契約類
 */
sealed class ToolHubContract {

    /**
     * 工具中心的 UI 狀態
     */
    data class State(
        val tools: List<Tool> = emptyList(),
        val selectedToolId: String? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : MviState

    /**
     * 工具模型
     */
    data class Tool(
        val id: String,
        val name: String,
        val description: String,
        val iconUrl: String? = null,
        val isInstalled: Boolean = false,
        val isEnabled: Boolean = true
    )

    /**
     * 用戶意圖
     */
    sealed class Intent : MviIntent {
        object LoadTools : Intent()
        data class SelectTool(val toolId: String) : Intent()
        data class InstallTool(val toolId: String) : Intent()
        data class UninstallTool(val toolId: String) : Intent()
        data class EnableTool(val toolId: String, val enabled: Boolean) : Intent()
        data class OpenToolDetails(val toolId: String) : Intent()
    }
    
    /**
     * UI 效果
     */
    sealed class Effect : MviEffect {
        data class ShowToast(val message: String) : Effect()
        data class NavigateToToolDetails(val toolId: String) : Effect()
    }
}