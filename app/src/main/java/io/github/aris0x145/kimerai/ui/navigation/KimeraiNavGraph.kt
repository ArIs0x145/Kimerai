package io.github.aris0x145.kimerai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.ui.features.chat.ChatScreen
import io.github.aris0x145.kimerai.ui.features.models.ModelsScreen
import io.github.aris0x145.kimerai.ui.features.settings.SettingsScreen

/**
 * 主導航圖，處理應用程式的導航邏輯
 */
@Composable
fun KimeraiNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.CHAT.route,
        modifier = modifier
    ) {
        // 聊天畫面
        composable(BottomNavDestination.CHAT.route) {
            ChatScreen()
        }
        
        // 模型管理畫面
        composable(BottomNavDestination.MODELS.route) {
            ModelsScreen()
        }
        
        // 設定畫面
        composable(BottomNavDestination.SETTINGS.route) {
            SettingsScreen()
        }
    }
}