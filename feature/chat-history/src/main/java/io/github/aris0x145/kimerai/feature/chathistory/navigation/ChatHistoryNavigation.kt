package io.github.aris0x145.kimerai.feature.chathistory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.feature.chathistory.ui.ChatHistoryScreen

/**
 * 聊天歷史功能模組的路由定義
 */
object ChatHistoryRoutes {
    const val CHAT_HISTORY_ROUTE = "chat_history"
}

/**
 * 為聊天歷史功能註冊導航目標
 */
fun NavGraphBuilder.chatHistoryNavigation(
    onNavigateUp: () -> Unit,
    onNavigateToNewConversation: () -> Unit,
    onNavigateToSession: (String) -> Unit
) {
    composable(route = ChatHistoryRoutes.CHAT_HISTORY_ROUTE) {
        ChatHistoryScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToNewConversation = onNavigateToNewConversation,
            onNavigateToSession = onNavigateToSession
        )
    }
}

/**
 * 導航到聊天歷史頁面
 */
fun NavController.navigateToChatHistory() {
    navigate(ChatHistoryRoutes.CHAT_HISTORY_ROUTE)
}