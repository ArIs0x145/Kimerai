package io.github.aris0x145.kimerai.feature.chathistory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.domain.repository.ChatHistoryRepository
import io.github.aris0x145.kimerai.feature.chathistory.ui.contract.ChatHistoryContract.Effect
import io.github.aris0x145.kimerai.feature.chathistory.ui.contract.ChatHistoryContract.Intent
import io.github.aris0x145.kimerai.feature.chathistory.ui.contract.ChatHistoryContract.State
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
 * 聊天歷史界面的 ViewModel
 */
@HiltViewModel
class ChatHistoryViewModel @Inject constructor(
    private val chatHistoryRepository: ChatHistoryRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()
    
    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()
    
    init {
        // 初始加載聊天歷史
        loadChatSessions()
    }
    
    /**
     * 處理用戶意圖
     */
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadSessions -> loadChatSessions()
            is Intent.DeleteSession -> deleteSession(intent.sessionId)
            is Intent.CreateNewSession -> createNewSession()
        }
    }
    
    /**
     * 加載聊天會話列表
     */
    private fun loadChatSessions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            chatHistoryRepository.getChatSessions()
                .onSuccess { sessions ->
                    _state.update { it.copy(sessions = sessions, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    _effect.emit(Effect.ShowError(e.message ?: "無法載入聊天歷史"))
                }
        }
    }
    
    /**
     * 刪除聊天會話
     */
    private fun deleteSession(sessionId: String) {
        viewModelScope.launch {
            chatHistoryRepository.deleteSession(sessionId)
                .onSuccess {
                    loadChatSessions() // 重新加載會話列表
                    _effect.emit(Effect.ShowToast("已刪除聊天記錄"))
                }
                .onFailure { e ->
                    _effect.emit(Effect.ShowError(e.message ?: "刪除聊天記錄失敗"))
                }
        }
    }
    
    /**
     * 創建新的會話
     */
    private fun createNewSession() {
        viewModelScope.launch {
            chatHistoryRepository.createNewSession()
                .onSuccess {
                    _effect.emit(Effect.NavigateToNewConversation)
                }
                .onFailure { e ->
                    _effect.emit(Effect.ShowError(e.message ?: "創建新會話失敗"))
                }
        }
    }
}