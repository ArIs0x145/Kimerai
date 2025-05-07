package io.github.aris0x145.kimerai.core.modelselector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.core.modelselector.state.ModelSelectorContract
import io.github.aris0x145.kimerai.core.modelselector.state.ModelSelectorContract.Effect
import io.github.aris0x145.kimerai.core.modelselector.state.ModelSelectorContract.Intent
import io.github.aris0x145.kimerai.core.modelselector.state.ModelSelectorContract.State
import io.github.aris0x145.kimerai.domain.repository.ModelRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 管理模型選擇的 ViewModel，實現 MVI 架構
 */
@HiltViewModel
class ModelSelectorViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()
    
    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()
    
    init {
        // 初始化時加載模型
        handleIntent(Intent.LoadModels)
    }
    
    /**
     * 處理用戶意圖
     */
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectModel -> selectModel(intent.modelId)
            is Intent.SetMenuExpanded -> setModelMenuExpanded(intent.isExpanded)
            is Intent.LoadModels -> loadModels()
        }
    }
    
    /**
     * 加載所有可用模型和當前選中的模型
     */
    private fun loadModels() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }
                
                // 載入可用模型
                modelRepository.getAllModels().collect { models ->
                    _state.update { it.copy(availableModels = models, isLoading = false) }
                }
                
                // 載入當前選中的模型
                modelRepository.getSelectedModel().collect { model ->
                    _state.update { it.copy(selectedModel = model) }
                }
                
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = "無法載入模型: ${e.message}" 
                    )
                }
            }
        }
    }
    
    /**
     * 選擇一個新的模型
     */
    private fun selectModel(modelId: String) {
        viewModelScope.launch {
            try {
                modelRepository.setSelectedModel(modelId)
                
                // 獲取模型名稱並發送成功通知
                val selectedModel = _state.value.availableModels.find { it.id == modelId }
                selectedModel?.let {
                    _effect.send(Effect.ShowModelChanged(it.name))
                }
                
                // 選擇後自動關閉選單
                setModelMenuExpanded(false)
                
            } catch (e: Exception) {
                _state.update { it.copy(error = "無法選擇模型: ${e.message}") }
            }
        }
    }
    
    /**
     * 設置模型選單的展開狀態
     */
    private fun setModelMenuExpanded(isExpanded: Boolean) {
        _state.update { it.copy(isModelMenuExpanded = isExpanded) }
    }
}