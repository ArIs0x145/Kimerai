package io.github.aris0x145.kimerai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.ui.features.chat.ChatScreen
import io.github.aris0x145.kimerai.ui.features.history.HistoryScreen
import io.github.aris0x145.kimerai.ui.features.models.ModelConfigScreen
import io.github.aris0x145.kimerai.ui.features.tools.ToolsScreen

/**
 * 應用主導航圖
 */
@Composable
fun KimeraiNavGraph(
    navController: NavHostController,
    startDestination: String = NavRoutes.CHAT
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 聊天畫面
        composable(route = NavRoutes.CHAT) {
            ChatScreen(navController = navController)
        }
        
        // 聊天歷史畫面
        composable(route = NavRoutes.HISTORY) {
            HistoryScreen(navController = navController)
        }
        
        // 多功能工具畫面
        composable(route = NavRoutes.TOOLS) {
            ToolsScreen(navController = navController)
        }
        
        // 模型配置畫面
        composable(route = NavRoutes.MODEL_CONFIG) {
            ModelConfigScreen(navController = navController)
        }
    }
}