package io.github.aris0x145.kimerai.feature.conversation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.feature.conversation.ui.ConversationScreen

/**
 * 對話功能模組的路由定義
 */
object ConversationRoutes {
    const val CONVERSATION_ROUTE = "conversation"
}

/**
 * 為對話功能註冊導航目標
 */
fun NavGraphBuilder.conversationNavigation(
    onNavigateToHistory: () -> Unit
) {
    composable(route = ConversationRoutes.CONVERSATION_ROUTE) {
        ConversationScreen(
            onNavigateToHistory = onNavigateToHistory
        )
    }
}

/**
 * 導航到對話頁面
 */
fun NavController.navigateToConversation() {
    navigate(ConversationRoutes.CONVERSATION_ROUTE)
}