package io.github.aris0x145.kimerai.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.aris0x145.kimerai.ui.theme.KimeraiTheme

/**
 * 底部導航欄組件
 */
@Composable
fun KimeraiBottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = BottomNavDestination.entries.toTypedArray()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { destination ->
            NavigationBarItem(
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) },
                selected = currentRoute == destination.route,
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination.route) {
                            // 避免創建相同的目的地實例，保持單一實例
                            launchSingleTop = true
                            // 當用户點擊底部導航項目時，清除回堆棧到該項目
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // 保存和恢復目的地的狀態
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun KimeraiBottomNavBarPreview() {
    KimeraiTheme {
        KimeraiBottomNavBar(navController = rememberNavController())
    }
}