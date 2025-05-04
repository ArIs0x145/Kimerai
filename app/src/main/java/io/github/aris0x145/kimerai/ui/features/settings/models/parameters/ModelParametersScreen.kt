package io.github.aris0x145.kimerai.ui.features.settings.models.parameters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.ui.features.settings.models.components.ParameterSlider

/**
 * 模型參數設定頁面
 * 使用 MCP (Model Context Protocol) 管理模型參數
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelParametersScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // 這些狀態在實際應用中應該從 ViewModel 獲取，並通過 MCP 管理
    var temperature by remember { mutableFloatStateOf(0.7f) }
    var maxTokens by remember { mutableIntStateOf(1000) }
    var topP by remember { mutableFloatStateOf(0.95f) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("模型參數設定") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 參數設定說明
            Text(
                text = "這些設定將影響所有支持的 AI 模型。不同模型對參數的支持程度可能有所不同。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 溫度參數
            ParameterSlider(
                title = "溫度 (Temperature)",
                value = temperature,
                onValueChange = { temperature = it },
                valueRange = 0f..2f,
                description = "控制模型輸出的隨機性。較高值產生更多創意和多樣性，較低值產生更確定的回應。"
            )
            
            // 最大令牌數
            ParameterSlider(
                title = "最大輸出長度 (Max Tokens)",
                value = maxTokens.toFloat(),
                onValueChange = { maxTokens = it.toInt() },
                valueRange = 50f..4000f,
                description = "控制模型一次生成的最大令牌數。較大值允許更長回應。",
                valueFormatter = { it.toInt().toString() }
            )
            
            // Top-P 參數
            ParameterSlider(
                title = "核心採樣 (Top-P)",
                value = topP,
                onValueChange = { topP = it },
                valueRange = 0f..1f,
                description = "控制模型考慮的詞彙範圍。較高值產生更多變化，較低值使回應更集中。"
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 底部按鈕
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                OutlinedButton(
                    onClick = { 
                        // 重置為默認值
                        temperature = 0.7f
                        maxTokens = 1000
                        topP = 0.95f
                    }
                ) {
                    Text("重置為默認")
                }
                
                Button(
                    onClick = { 
                        // 保存參數並返回
                        navController.navigateUp()
                    }
                ) {
                    Text("保存設定")
                }
            }
        }
    }
}