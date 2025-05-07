package io.github.aris0x145.kimerai.feature.chathistory.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.aris0x145.kimerai.feature.chathistory.ui.contract.ChatHistoryContract
import io.github.aris0x145.kimerai.feature.chathistory.viewmodel.ChatHistoryViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * 聊天歷史畫面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatHistoryViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToNewConversation: () -> Unit,
    onNavigateToSession: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // 處理 UI 效果
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ChatHistoryContract.Effect.NavigateToNewConversation -> {
                    onNavigateToNewConversation()
                }
                is ChatHistoryContract.Effect.NavigateToSession -> {
                    onNavigateToSession(effect.sessionId)
                }
                is ChatHistoryContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is ChatHistoryContract.Effect.ShowToast -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("聊天歷史") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                actions = {
                    // 新建聊天室按鈕
                    IconButton(
                        onClick = {
                            viewModel.handleIntent(ChatHistoryContract.Intent.CreateNewSession)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "新建聊天"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        // 歷史記錄內容區域
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (state.sessions.isEmpty()) {
                // 顯示無歷史記錄的提示
                Text(
                    text = "沒有聊天歷史記錄",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                // TODO: 實現聊天會話列表顯示
                // 先使用簡單提示
                Text(
                    text = "聊天歷史記錄將顯示在這裡 (${state.sessions.size} 個會話)",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}