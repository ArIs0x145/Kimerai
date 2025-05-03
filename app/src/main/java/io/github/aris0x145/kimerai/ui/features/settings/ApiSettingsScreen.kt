package io.github.aris0x145.kimerai.ui.features.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.ui.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiSettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("模型 API 設定") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // OpenAI API 設定
            ListItem(
                headlineContent = { Text("OpenAI API 設定") },
                supportingContent = { Text("管理 OpenAI API 金鑰") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.Api,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    IconButton(onClick = { 
                        // TODO: 導航到 OpenAI API 設定頁面
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "前往設定"
                        )
                    }
                }
            )
            
            HorizontalDivider()
            
            // Google Gemini API 設定
            ListItem(
                headlineContent = { Text("Google Gemini API 設定") },
                supportingContent = { Text("管理 Google Gemini API 金鑰") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.Api,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    IconButton(onClick = { 
                        // TODO: 導航到 Google Gemini API 設定頁面
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "前往設定"
                        )
                    }
                }
            )
            
            HorizontalDivider()
            
            // Anthropic API 設定
            ListItem(
                headlineContent = { Text("Anthropic API 設定") },
                supportingContent = { Text("管理 Anthropic API 金鑰") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.Api,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    IconButton(onClick = { 
                        // TODO: 導航到 Anthropic API 設定頁面
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "前往設定"
                        )
                    }
                }
            )
            
            HorizontalDivider()
            
            // Deepseek API 設定
            ListItem(
                headlineContent = { Text("Deepseek API 設定") },
                supportingContent = { Text("管理 Deepseek API 金鑰") },
                leadingContent = { 
                    Icon(
                        imageVector = Icons.Default.Api,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    IconButton(onClick = { 
                        // TODO: 導航到 Deepseek API 設定頁面
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "前往設定"
                        )
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}