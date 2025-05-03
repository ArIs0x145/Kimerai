package io.github.aris0x145.kimerai.ui.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonalInjury
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.aris0x145.kimerai.ui.theme.KimeraiTheme

/**
 * 更多選項畫面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("更多選項") },
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
        // 選項內容區域
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 個人化選項
            OptionCard(
                title = "個人化",
                description = "調整語言、主題和顯示設定",
                icon = Icons.Default.PersonalInjury,
                onClick = {
                    // TODO: 導航到個人化頁面
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 模型 API 設定
            OptionCard(
                title = "模型 API 設定",
                description = "管理 OpenAI、Gemini 等 API 金鑰",
                icon = Icons.Default.Api,
                onClick = {
                    // TODO: 導航到模型 API 設定頁面
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 插件與擴展
            OptionCard(
                title = "插件與擴展",
                description = "管理 MCP 插件和功能擴展",
                icon = Icons.Default.Extension,
                onClick = {
                    // TODO: 導航到插件與擴展頁面
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 資料管理
            OptionCard(
                title = "資料管理",
                description = "管理對話歷史、檔案和備份",
                icon = Icons.Default.DataObject,
                onClick = {
                    // TODO: 導航到資料管理頁面
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 關於應用
            OptionCard(
                title = "關於",
                description = "查看版本資訊和開發者資訊",
                icon = Icons.Default.Info,
                onClick = {
                    // TODO: 導航到關於頁面
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp) // 預留給 icon 的空間
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterStart),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoreOptionsScreenPreview() {
    KimeraiTheme {
        MoreOptionsScreen(navController = rememberNavController())
    }
}