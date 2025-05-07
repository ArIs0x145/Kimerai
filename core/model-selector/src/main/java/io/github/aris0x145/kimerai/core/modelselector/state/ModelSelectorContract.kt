package io.github.aris0x145.kimerai.core.modelselector.state

import io.github.aris0x145.kimerai.domain.entity.model.AiModel

/**
 * 模型選擇器的 MVI 契約類
 */
object ModelSelectorContract {
    /**
     * 模型選擇器的 UI 狀態
     */
    data class State(
        val availableModels: List<AiModel> = emptyList(),
        val selectedModel: AiModel? = null,
        val isModelMenuExpanded: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )
    
    /**
     * 用戶意圖
     */
    sealed class Intent {
        data class SelectModel(val modelId: String) : Intent()
        data class SetMenuExpanded(val isExpanded: Boolean) : Intent()
        object LoadModels : Intent()
    }
    
    /**
     * UI 效果
     */
    sealed class Effect {
        data class ShowModelChanged(val modelName: String) : Effect()
    }
}