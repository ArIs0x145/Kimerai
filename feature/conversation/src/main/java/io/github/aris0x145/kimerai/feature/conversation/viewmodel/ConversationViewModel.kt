package io.github.aris0x145.kimerai.feature.conversation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.domain.model.ChatMessage
import io.github.aris0x145.kimerai.domain.repository.ChatRepository
import io.github.aris0x145.kimerai.feature.conversation.ui.contract.ConversationContract
import io.github.aris0x145.kimerai.feature.conversation.ui.contract.ConversationContract.Effect
import io.github.aris0x145.kimerai.feature.conversation.ui.contract.ConversationContract.Intent
import io.github.aris0x145.kimerai.feature.conversation.ui.contract.ConversationContract.State
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
 * 管理對話界面的 ViewModel，使用 MVI 架構模式
 */
@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()
    
    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()
    
    init {
        // 加載聊天消息
        loadChatMessages()
    }
    
    /**
     * 處理用戶意圖
     */
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SendMessage -> sendMessage(intent.text)
            is Intent.ClearConversation -> clearMessages()
            is Intent.LoadMessages -> loadChatMessages()
            is Intent.RetryLastMessage -> retryLastMessage()
        }
    }
    
    /**
     * 加載對話消息
     */
    private fun loadChatMessages() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            chatRepository.getChatMessages().collect { messages ->
                _state.update { it.copy(messages = messages, isLoading = false) }
            }
        }
    }
    
    /**
     * 發送用戶消息
     */
    private fun sendMessage(messageText: String) {
        if (messageText.isBlank()) return
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val userMessage = ChatMessage(
                content = messageText,
                isUserMessage = true
            )
            
            chatRepository.sendMessage(userMessage)
                .onSuccess {
                    // 消息發送成功後不需要特別處理，因為會通過 flow 更新
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    _effect.emit(Effect.ShowError(e.message ?: "發送消息失敗"))
                }
        }
    }
    
    /**
     * 清空聊天記錄
     */
    private fun clearMessages() {
        viewModelScope.launch {
            chatRepository.clearMessages()
            _effect.emit(Effect.ShowToast("對話已清空"))
        }
    }
    
    /**
     * 重試最後一條消息
     */
    private fun retryLastMessage() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            chatRepository.retryLastMessage()
                .onSuccess {
                    // 重試成功後不需要特別處理，因為會通過 flow 更新
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    _effect.emit(Effect.ShowError(e.message ?: "重試消息失敗"))
                }
        }
    }
}