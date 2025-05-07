package io.github.aris0x145.kimerai.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.feature.settings.ui.screens.SettingsScreen

const val SETTINGS_ROUTE = "settings"
const val SETTINGS_PERSONALIZATION_ROUTE = "settings/personalization"
const val SETTINGS_PLUGINS_ROUTE = "settings/plugins"
const val SETTINGS_DATA_ROUTE = "settings/data"
const val SETTINGS_MODELS_ROUTE = "settings/model"
const val SETTINGS_ABOUT_ROUTE = "settings/about"

/**
 * 設置功能的導航擴展
 */
fun NavGraphBuilder.settingsGraph(navController: NavController) {
    // 主設置頁面
    composable(SETTINGS_ROUTE) {
        SettingsScreen(navController = navController)
    }
    
    // TODO: 添加設置子頁面
    // 個人化設置頁面
    // 插件頁面
    // 資料管理頁面
    // 模型設置頁面
    // 關於頁面
}

/**
 * 導航到設置頁面
 */
fun NavController.navigateToSettings() {
    navigate(SETTINGS_ROUTE)
}