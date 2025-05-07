package io.github.aris0x145.kimerai.feature.toolhub.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.aris0x145.kimerai.feature.toolhub.ui.screens.ToolHubScreen

const val TOOL_HUB_ROUTE = "tool_hub"
const val TOOL_DETAILS_ROUTE = "tool_details"
const val TOOL_ID_ARG = "toolId"

/**
 * 工具中心功能的導航擴展
 */
fun NavGraphBuilder.toolHubGraph(navController: NavController) {
    // 工具中心主頁面
    composable(TOOL_HUB_ROUTE) {
        ToolHubScreen(navController = navController)
    }
    
    // TODO: 添加工具詳情頁面
    // composable("$TOOL_DETAILS_ROUTE/{$TOOL_ID_ARG}") { backStackEntry ->
    //     val toolId = backStackEntry.arguments?.getString(TOOL_ID_ARG)
    //     ToolDetailsScreen(
    //         toolId = toolId ?: "",
    //         navController = navController
    //     )
    // }
}

/**
 * 導航到工具中心頁面
 */
fun NavController.navigateToToolHub() {
    navigate(TOOL_HUB_ROUTE)
}

/**
 * 導航到工具詳情頁面
 */
fun NavController.navigateToToolDetails(toolId: String) {
    navigate("$TOOL_DETAILS_ROUTE/$toolId")
}