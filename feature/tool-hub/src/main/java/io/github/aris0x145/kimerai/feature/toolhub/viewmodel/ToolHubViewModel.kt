package io.github.aris0x145.kimerai.feature.toolhub.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.core.ui.base.MviViewModel
import io.github.aris0x145.kimerai.feature.toolhub.ui.contract.ToolHubContract
import io.github.aris0x145.kimerai.feature.toolhub.ui.contract.ToolHubContract.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 工具中心功能的 ViewModel，實現 MVI 架構
 */
@HiltViewModel
class ToolHubViewModel @Inject constructor(
    // TODO: 注入必要的資料源
) : MviViewModel<Intent, State, Effect>() {

    override fun createInitialState(): State = State()
    
    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadTools -> loadTools()
            is Intent.SelectTool -> selectTool(intent.toolId)
            is Intent.InstallTool -> installTool(intent.toolId)
            is Intent.UninstallTool -> uninstallTool(intent.toolId)
            is Intent.EnableTool -> enableTool(intent.toolId, intent.enabled)
            is Intent.OpenToolDetails -> openToolDetails(intent.toolId)
        }
    }
    
    private fun loadTools() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            
            try {
                // 暫時使用模擬數據
                val mockTools = listOf(
                    Tool(
                        id = "web-search",
                        name = "網路搜索",
                        description = "使用搜索引擎獲取最新資訊",
                        isInstalled = true
                    ),
                    Tool(
                        id = "code-analysis",
                        name = "程式碼分析",
                        description = "分析和理解程式碼片段",
                        isInstalled = true
                    ),
                    Tool(
                        id = "image-generation",
                        name = "圖像生成",
                        description = "根據描述生成圖像",
                        isInstalled = false
                    ),
                    Tool(
                        id = "file-manager",
                        name = "檔案管理器",
                        description = "上傳和管理檔案",
                        isInstalled = true,
                        isEnabled = false
                    )
                )
                
                updateState { 
                    copy(
                        tools = mockTools,
                        isLoading = false
                    ) 
                }
            } catch (e: Exception) {
                updateState { 
                    copy(
                        isLoading = false,
                        error = "無法加載工具列表: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    private fun selectTool(toolId: String) {
        updateState { copy(selectedToolId = toolId) }
    }
    
    private fun installTool(toolId: String) {
        viewModelScope.launch {
            try {
                // TODO: 實際實現工具安裝邏輯
                
                // 更新工具狀態
                val updatedTools = state.value.tools.map { tool ->
                    if (tool.id == toolId) tool.copy(isInstalled = true) else tool
                }
                
                updateState { copy(tools = updatedTools) }
                sendEffect(Effect.ShowToast("工具安裝成功"))
            } catch (e: Exception) {
                sendEffect(Effect.ShowToast("工具安裝失敗: ${e.message}"))
            }
        }
    }
    
    private fun uninstallTool(toolId: String) {
        viewModelScope.launch {
            try {
                // TODO: 實際實現工具卸載邏輯
                
                // 更新工具狀態
                val updatedTools = state.value.tools.map { tool ->
                    if (tool.id == toolId) tool.copy(isInstalled = false) else tool
                }
                
                updateState { copy(tools = updatedTools) }
                sendEffect(Effect.ShowToast("工具卸載成功"))
            } catch (e: Exception) {
                sendEffect(Effect.ShowToast("工具卸載失敗: ${e.message}"))
            }
        }
    }
    
    private fun enableTool(toolId: String, enabled: Boolean) {
        viewModelScope.launch {
            try {
                // TODO: 實際實現工具啟用/禁用邏輯
                
                // 更新工具狀態
                val updatedTools = state.value.tools.map { tool ->
                    if (tool.id == toolId) tool.copy(isEnabled = enabled) else tool
                }
                
                updateState { copy(tools = updatedTools) }
                
                val status = if (enabled) "啟用" else "禁用"
                sendEffect(Effect.ShowToast("工具已$status"))
            } catch (e: Exception) {
                sendEffect(Effect.ShowToast("操作失敗: ${e.message}"))
            }
        }
    }
    
    private fun openToolDetails(toolId: String) {
        sendEffect(Effect.NavigateToToolDetails(toolId))
    }
}