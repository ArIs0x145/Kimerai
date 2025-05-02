package io.github.aris0x145.kimerai.ui.features.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.domain.model.AiModel
import io.github.aris0x145.kimerai.domain.repository.ModelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 模型選擇界面的 UI 狀態
 */
data class ModelSelectionState(
    val availableModels: List<AiModel> = emptyList(),
    val selectedModel: AiModel? = null,
    val isModelMenuExpanded: Boolean = false
)

/**
 * 管理模型選擇的 ViewModel
 */
@HiltViewModel
class ModelSelectionViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ModelSelectionState())
    val uiState: StateFlow<ModelSelectionState> = _uiState.asStateFlow()
    
    init {
        // 載入可用模型
        viewModelScope.launch {
            modelRepository.getAllModels().collect { models ->
                _uiState.update { it.copy(availableModels = models) }
            }
        }
        
        // 載入當前選中的模型
        viewModelScope.launch {
            modelRepository.getSelectedModel().collect { model ->
                _uiState.update { it.copy(selectedModel = model) }
            }
        }
    }
    
    /**
     * 選擇一個新的模型
     */
    fun selectModel(modelId: String) {
        viewModelScope.launch {
            modelRepository.setSelectedModel(modelId)
        }
    }
    
    /**
     * 設置模型選單的展開狀態
     */
    fun setModelMenuExpanded(isExpanded: Boolean) {
        _uiState.update { it.copy(isModelMenuExpanded = isExpanded) }
    }
}