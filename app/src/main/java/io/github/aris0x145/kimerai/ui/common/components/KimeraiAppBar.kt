package io.github.aris0x145.kimerai.ui.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.domain.model.AiModel
import io.github.aris0x145.kimerai.ui.features.models.ModelSelectionViewModel
import io.github.aris0x145.kimerai.ui.navigation.NavRoutes

/**
 * Kimerai 應用通用頂部應用欄
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KimeraiAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ModelSelectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 左側歷史按鈕 - 點擊跳轉到歷史記錄畫面
                IconButton(onClick = { 
                    navController.navigate(NavRoutes.Main.History.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "聊天歷史"
                    )
                }
                
                // 中間的模型選擇器
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    Button(onClick = { viewModel.setModelMenuExpanded(true) }) {
                        Text(uiState.selectedModel?.name ?: "選擇模型")
                    }
                    
                    ModelDropdownMenu(
                        expanded = uiState.isModelMenuExpanded,
                        models = uiState.availableModels,
                        onModelSelected = { 
                            viewModel.selectModel(it.id)
                            viewModel.setModelMenuExpanded(false)
                        },
                        onDismissRequest = { viewModel.setModelMenuExpanded(false) },
                        navController = navController
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                
                // 右側的更多選項按鈕 - 直接跳轉到更多選項畫面
                IconButton(onClick = { 
                    navController.navigate(NavRoutes.Main.Settings.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "更多選項"
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun ModelDropdownMenu(
    expanded: Boolean,
    models: List<AiModel>, 
    onModelSelected: (AiModel) -> Unit,
    onDismissRequest: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        // 模型配置選項
        DropdownMenuItem(
            text = { Text("模型設定") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "模型設定"
                )
            },
            onClick = { 
                onDismissRequest()
                navController.navigate(NavRoutes.Settings.Model.Main.route)
            }
        )
        
        HorizontalDivider()
        
        // 模型列表
        models.forEach { model ->
            DropdownMenuItem(
                text = { Text(model.name) },
                onClick = { onModelSelected(model) }
            )
        }
    }
}