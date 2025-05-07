package io.github.aris0x145.kimerai.feature.conversation.ui.contract

import io.github.aris0x145.kimerai.domain.model.ChatMessage

/**
 * 對話功能的 MVI 契約，定義狀態、意圖和效果
 */
class ConversationContract {
    /**
     * 對話界面的狀態
     */
    data class State(
        val messages: List<ChatMessage> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
    
    /**
     * 用戶對對話界面的意圖
     */
    sealed interface Intent {
        data class SendMessage(val text: String) : Intent
        object ClearConversation : Intent
        object LoadMessages : Intent
        object RetryLastMessage : Intent
    }
    
    /**
     * 對話界面的 UI 效果
     */
    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data class ShowToast(val message: String) : Effect
        object NavigateToHistory : Effect
    }
}