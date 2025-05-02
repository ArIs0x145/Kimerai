package io.github.aris0x145.kimerai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 定義應用程式的主要導航目的地
 */
enum class BottomNavDestination(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    CHAT(
        route = "chat",
        icon = Icons.AutoMirrored.Filled.Chat,
        label = "聊天"
    ),
    MODELS(
        route = "models",
        icon = Icons.Default.SmartToy,
        label = "模型"
    ),
    SETTINGS(
        route = "settings",
        icon = Icons.Default.Settings,
        label = "設定"
    )
}