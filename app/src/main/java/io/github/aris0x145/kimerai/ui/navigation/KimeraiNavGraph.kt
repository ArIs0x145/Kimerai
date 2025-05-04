package io.github.aris0x145.kimerai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.ui.features.chat.ChatScreen
import io.github.aris0x145.kimerai.ui.features.history.HistoryScreen
import io.github.aris0x145.kimerai.ui.features.settings.about.AboutScreen
import io.github.aris0x145.kimerai.ui.features.settings.models.api.ApiKeysScreen
import io.github.aris0x145.kimerai.ui.features.settings.data.DataManagementScreen
import io.github.aris0x145.kimerai.ui.features.settings.models.parameters.ModelParametersScreen
import io.github.aris0x145.kimerai.ui.features.settings.SettingsScreen
import io.github.aris0x145.kimerai.ui.features.settings.personalization.PersonalizationScreen
import io.github.aris0x145.kimerai.ui.features.settings.plugins.PluginsScreen
import io.github.aris0x145.kimerai.ui.features.settings.models.ModelsSettingsScreen

/**
 * 應用主導航圖
 * 使用類型安全的路由結構
 */
@Composable
fun KimeraiNavGraph(
    navController: NavHostController,
    startDestination: String = NavRoutes.Main.Chat.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // --- 主要導航路由 ---
        composable(route = NavRoutes.Main.Chat.route) {
            ChatScreen(navController = navController)
        }

        composable(route = NavRoutes.Main.History.route) {
            HistoryScreen(navController = navController)
        }

        composable(route = NavRoutes.Main.Settings.route) {
            SettingsScreen(navController = navController)
        }

        // --- 設定區塊內部路由 ---
        composable(route = NavRoutes.Settings.Personalization.route) {
            PersonalizationScreen(navController = navController)
        }

        composable(route = NavRoutes.Settings.Plugins.route) {
            PluginsScreen(navController = navController)
        }

        composable(route = NavRoutes.Settings.Data.route) {
            DataManagementScreen(navController = navController)
        }

        composable(route = NavRoutes.Settings.About.route) {
            AboutScreen(navController = navController)
        }

        // --- 模型設定子區塊路由 ---
        composable(route = NavRoutes.Settings.Model.Main.route) {
            ModelsSettingsScreen(navController = navController)
        }

        composable(route = NavRoutes.Settings.Model.ApiKeys.route) {
            ApiKeysScreen(navController = navController)
        }

        composable(route = NavRoutes.Settings.Model.Parameters.route) {
            ModelParametersScreen(navController = navController)
        }
    }
}