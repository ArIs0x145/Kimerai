package io.github.aris0x145.kimerai.ui.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aris0x145.kimerai.domain.model.ChatMessage
import io.github.aris0x145.kimerai.domain.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 聊天界面的 UI 狀態
 */
data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * 管理聊天界面的 ViewModel
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatState())
    val uiState: StateFlow<ChatState> = _uiState.asStateFlow()
    
    init {
        // 加載聊天消息
        viewModelScope.launch {
            chatRepository.getChatMessages().collect { messages ->
                _uiState.update { it.copy(messages = messages, isLoading = false) }
            }
        }
    }
    
    /**
     * 發送用戶消息
     */
    fun sendMessage(messageText: String) {
        if (messageText.isBlank()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val userMessage = ChatMessage(
                content = messageText,
                isUserMessage = true
            )
            
            chatRepository.sendMessage(userMessage)
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
    
    /**
     * 清空聊天記錄
     */
    fun clearMessages() {
        viewModelScope.launch {
            chatRepository.clearMessages()
        }
    }
}