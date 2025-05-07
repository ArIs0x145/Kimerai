package io.github.aris0x145.kimerai.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.github.aris0x145.kimerai.feature.conversation.navigation.CONVERSATION_ROUTE
import io.github.aris0x145.kimerai.feature.conversation.navigation.conversationScreen
import io.github.aris0x145.kimerai.feature.chathistory.navigation.CHAT_HISTORY_ROUTE
import io.github.aris0x145.kimerai.feature.chathistory.navigation.chatHistoryScreen
import io.github.aris0x145.kimerai.feature.conversation.navigation.navigateToConversation
import io.github.aris0x145.kimerai.feature.settings.navigation.settingsGraph
import io.github.aris0x145.kimerai.feature.toolhub.navigation.toolHubGraph

/**
 * 應用主導航圖 - 基於模塊化架構
 * 整合各個功能模塊的導航
 */
@Composable
fun KimeraiNavGraph(
    navController: NavHostController,
    startDestination: String = CONVERSATION_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 對話功能模塊
        conversationScreen(navController)
        
        // 聊天歷史功能模塊
        chatHistoryScreen(
            navController = navController,
            onNavigateToChat = { sessionId ->
                navController.navigateToConversation(sessionId)
            }
        )
        
        // 設置功能模塊
        settingsGraph(navController)
        
        // 工具中心功能模塊
        toolHubGraph(navController)
    }
}