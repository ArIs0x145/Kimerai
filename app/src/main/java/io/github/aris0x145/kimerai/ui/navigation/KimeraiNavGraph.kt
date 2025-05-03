package io.github.aris0x145.kimerai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.ui.features.chat.ChatScreen
import io.github.aris0x145.kimerai.ui.features.history.HistoryScreen
import io.github.aris0x145.kimerai.ui.features.models.ModelConfigScreen
import io.github.aris0x145.kimerai.ui.features.settings.about.AboutScreen
import io.github.aris0x145.kimerai.ui.features.settings.api.ApiSettingsScreen
import io.github.aris0x145.kimerai.ui.features.settings.data.DataManagementScreen
import io.github.aris0x145.kimerai.ui.features.settings.MoreOptionsScreen
import io.github.aris0x145.kimerai.ui.features.settings.personalization.PersonalizationScreen
import io.github.aris0x145.kimerai.ui.features.settings.plugins.PluginsScreen

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
        
        // 更多選項畫面
        composable(route = NavRoutes.MORE_OPTIONS) {
            MoreOptionsScreen(navController = navController)
        }
        
        // 模型配置畫面
        composable(route = NavRoutes.MODEL_CONFIG) {
            ModelConfigScreen(navController = navController)
        }
        
        // 個人化設定頁面
        composable(route = NavRoutes.PERSONALIZATION) {
            PersonalizationScreen(navController = navController)
        }
        
        // 模型 API 設定頁面
        composable(route = NavRoutes.API_SETTINGS) {
            ApiSettingsScreen(navController = navController)
        }
        
        // 插件與擴展頁面
        composable(route = NavRoutes.PLUGINS) {
            PluginsScreen(navController = navController)
        }
        
        // 資料管理頁面
        composable(route = NavRoutes.DATA_MANAGEMENT) {
            DataManagementScreen(navController = navController)
        }
        
        // 關於頁面
        composable(route = NavRoutes.ABOUT) {
            AboutScreen(navController = navController)
        }
    }
}