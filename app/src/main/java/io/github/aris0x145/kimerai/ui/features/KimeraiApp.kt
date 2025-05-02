package io.github.aris0x145.kimerai.ui.features

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import io.github.aris0x145.kimerai.ui.navigation.KimeraiBottomNavBar
import io.github.aris0x145.kimerai.ui.navigation.KimeraiNavGraph

/**
 * 主應用畫面，整合導航和底部導航欄
 */
@Composable
fun KimeraiApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            KimeraiBottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        KimeraiNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}