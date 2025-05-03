package io.github.aris0x145.kimerai.ui.features.models

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.aris0x145.kimerai.domain.model.AiModel
import io.github.aris0x145.kimerai.ui.theme.KimeraiTheme

/**
 * 模型配置畫面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelConfigScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ModelSelectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("模型設定") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Text(
                    text = "目前選擇的模型",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                
                uiState.selectedModel?.let { selectedModel ->
                    ModelCard(
                        model = selectedModel,
                        isSelected = true,
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                
                HorizontalDivider(
                    modifier = Modifier.padding(16.dp)
                )
                
                Text(
                    text = "可用模型列表",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            items(uiState.availableModels) { model ->
                ModelCard(
                    model = model,
                    isSelected = model.id == uiState.selectedModel?.id,
                    onClick = { viewModel.selectModel(model.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ModelCard(
    model: AiModel,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = model.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (model.isLocal) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "本地模型",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            if (isSelected) {
                RadioButton(
                    selected = true,
                    onClick = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModelConfigScreenPreview() {
    KimeraiTheme {
        ModelConfigScreen(navController = rememberNavController())
    }
}