package io.github.aris0x145.kimerai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.aris0x145.kimerai.ui.navigation.KimeraiNavGraph
import io.github.aris0x145.kimerai.ui.theme.KimeraiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KimeraiTheme {
                val navController = rememberNavController()
                KimeraiNavGraph(navController = navController)
            }
        }
    }
}