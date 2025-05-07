package io.github.aris0x145.kimerai.feature.settings.ui.screens

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonalInjury
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.feature.settings.ui.contract.SettingsContract
import io.github.aris0x145.kimerai.feature.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import android.widget.Toast

/**
 * 設置畫面 - 使用 MVI 架構
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // 處理副作用
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SettingsContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is SettingsContract.Effect.Navigate -> {
                    navController.navigate(effect.route)
                }
            }
        }
    }
    
    // 初始加載設置
    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(SettingsContract.Intent.LoadSettings)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設置") },
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
        if (state.isLoading) {
            // 顯示加載指示器
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // 設置選項內容區域
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
                        viewModel.handleIntent(SettingsContract.Intent.NavigateToPersonalization)
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 模型設置
                OptionCard(
                    title = "模型設置",
                    description = "管理 AI 模型和 API 金鑰",
                    icon = Icons.Default.Api,
                    onClick = {
                        viewModel.handleIntent(SettingsContract.Intent.NavigateToModels)
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 插件與擴展
                OptionCard(
                    title = "插件與擴展",
                    description = "管理 MCP 插件和功能擴展",
                    icon = Icons.Default.Extension,
                    onClick = {
                        viewModel.handleIntent(SettingsContract.Intent.NavigateToPlugins)
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 資料管理
                OptionCard(
                    title = "資料管理",
                    description = "管理對話歷史、檔案和備份",
                    icon = Icons.Default.DataObject,
                    onClick = {
                        viewModel.handleIntent(SettingsContract.Intent.NavigateToDataManagement)
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 關於
                OptionCard(
                    title = "關於",
                    description = "查看版本資訊和開發者資訊",
                    icon = Icons.Default.Info,
                    onClick = {
                        viewModel.handleIntent(SettingsContract.Intent.NavigateToAbout)
                    }
                )
            }
            
            // 顯示錯誤信息（如果有）
            state.error?.let { error ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Snackbar(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(error)
                    }
                }
            }
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