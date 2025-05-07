package io.github.aris0x145.kimerai.feature.chathistory.ui.contract

import io.github.aris0x145.kimerai.domain.model.ChatSession

/**
 * 聊天歷史功能的 MVI 契約
 */
class ChatHistoryContract {
    /**
     * 聊天歷史界面的狀態
     */
    data class State(
        val sessions: List<ChatSession> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
    
    /**
     * 用戶對聊天歷史界面的意圖
     */
    sealed interface Intent {
        object LoadSessions : Intent
        data class DeleteSession(val sessionId: String) : Intent
        object CreateNewSession : Intent
    }
    
    /**
     * 聊天歷史界面的 UI 效果
     */
    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        data class ShowToast(val message: String) : Effect
        object NavigateToNewConversation : Effect
        data class NavigateToSession(val sessionId: String) : Effect
    }
}