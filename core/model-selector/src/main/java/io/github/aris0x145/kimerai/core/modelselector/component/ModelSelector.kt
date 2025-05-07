package io.github.aris0x145.kimerai.core.modelselector.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.aris0x145.kimerai.core.modelselector.state.ModelSelectorContract
import io.github.aris0x145.kimerai.core.modelselector.viewmodel.ModelSelectorViewModel
import android.widget.Toast
import kotlinx.coroutines.flow.collectLatest

/**
 * 模型選擇器組件
 */
@Composable
fun ModelSelector(
    modifier: Modifier = Modifier,
    viewModel: ModelSelectorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // 處理副作用
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ModelSelectorContract.Effect.ShowModelChanged -> {
                    Toast.makeText(
                        context, 
                        "已切換到模型: ${effect.modelName}", 
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    
    Box(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // 顯示當前選中的模型
        OutlinedButton(
            onClick = { viewModel.handleIntent(ModelSelectorContract.Intent.SetMenuExpanded(true)) }
        ) {
            Text(state.selectedModel?.name ?: "選擇模型")
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "展開選單"
            )
        }
        
        // 模型選擇下拉選單
        DropdownMenu(
            expanded = state.isModelMenuExpanded,
            onDismissRequest = { viewModel.handleIntent(ModelSelectorContract.Intent.SetMenuExpanded(false)) }
        ) {
            state.availableModels.forEach { model ->
                DropdownMenuItem(
                    text = { 
                        Text(
                            text = model.name + (if (model.isLocal) " (本地)" else ""),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    },
                    onClick = { viewModel.handleIntent(ModelSelectorContract.Intent.SelectModel(model.id)) },
                    enabled = !state.isLoading
                )
            }
        }
    }
}