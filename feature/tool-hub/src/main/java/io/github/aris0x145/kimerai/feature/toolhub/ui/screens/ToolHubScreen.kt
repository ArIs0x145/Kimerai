package io.github.aris0x145.kimerai.feature.toolhub.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.feature.toolhub.ui.contract.ToolHubContract
import io.github.aris0x145.kimerai.feature.toolhub.viewmodel.ToolHubViewModel
import kotlinx.coroutines.flow.collectLatest
import android.widget.Toast

/**
 * 工具中心畫面 - 使用 MVI 架構
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolHubScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ToolHubViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // 處理副作用
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ToolHubContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is ToolHubContract.Effect.NavigateToToolDetails -> {
                    // TODO: 實現工具詳情頁面的導航
                    Toast.makeText(context, "導航到工具 ${effect.toolId} 詳情", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    // 初始加載工具列表
    LaunchedEffect(key1 = Unit) {
        viewModel.handleIntent(ToolHubContract.Intent.LoadTools)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("工具中心") },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: 打開工具商店或添加工具對話框 */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "添加工具"
                )
            }
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
        } else if (state.tools.isEmpty()) {
            // 顯示空狀態
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Extension,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "工具中心還沒有任何工具",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "點擊右下角的「+」按鈕添加您的第一個工具",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = { /* TODO: 打開工具商店 */ }
                    ) {
                        Text("瀏覽工具商店")
                    }
                }
            }
        } else {
            // 顯示工具列表
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(state.tools) { tool ->
                    ToolCard(
                        tool = tool,
                        onClick = {
                            viewModel.handleIntent(ToolHubContract.Intent.OpenToolDetails(tool.id))
                        },
                        onInstallClick = {
                            if (tool.isInstalled) {
                                viewModel.handleIntent(ToolHubContract.Intent.UninstallTool(tool.id))
                            } else {
                                viewModel.handleIntent(ToolHubContract.Intent.InstallTool(tool.id))
                            }
                        },
                        onEnableChange = { enabled ->
                            viewModel.handleIntent(ToolHubContract.Intent.EnableTool(tool.id, enabled))
                        }
                    )
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolCard(
    tool: ToolHubContract.Tool,
    onClick: () -> Unit,
    onInstallClick: () -> Unit,
    onEnableChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 工具圖標
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Extension,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 工具名稱
            Text(
                text = tool.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 工具描述
            Text(
                text = tool.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 工具操作區域
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 安裝/卸載按鈕
                IconButton(
                    onClick = onInstallClick
                ) {
                    Icon(
                        imageVector = if (tool.isInstalled) Icons.Default.Delete else Icons.Default.Add,
                        contentDescription = if (tool.isInstalled) "卸載" else "安裝",
                        tint = if (tool.isInstalled) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
                
                // 啟用/禁用開關
                if (tool.isInstalled) {
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Switch(
                        checked = tool.isEnabled,
                        onCheckedChange = onEnableChange,
                        enabled = tool.isInstalled
                    )
                }
            }
        }
    }
}