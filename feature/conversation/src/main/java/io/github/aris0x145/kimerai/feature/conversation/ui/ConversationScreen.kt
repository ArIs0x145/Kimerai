package io.github.aris0x145.kimerai.feature.conversation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.aris0x145.kimerai.core.ui.components.LoadingIndicator
import io.github.aris0x145.kimerai.domain.model.ChatMessage
import io.github.aris0x145.kimerai.feature.conversation.ui.components.ChatMessageItem
import io.github.aris0x145.kimerai.feature.conversation.ui.contract.ConversationContract
import io.github.aris0x145.kimerai.feature.conversation.viewmodel.ConversationViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * 對話界面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    viewModel: ConversationViewModel = hiltViewModel(),
    onNavigateToHistory: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var messageText by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    
    // 處理 UI 效果
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ConversationContract.Effect.NavigateToHistory -> {
                    onNavigateToHistory()
                }
                is ConversationContract.Effect.ShowError -> {
                    // 錯誤已在 UI 中顯示
                }
                is ConversationContract.Effect.ShowToast -> {
                    // 可以使用 Toast 或 Snackbar 顯示訊息
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("對話") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "更多選項"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("清空對話") },
                            onClick = {
                                viewModel.handleIntent(ConversationContract.Intent.ClearConversation)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("查看歷史") },
                            onClick = {
                                onNavigateToHistory()
                                showMenu = false
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            ChatInputBar(
                messageText = messageText,
                onMessageTextChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.handleIntent(ConversationContract.Intent.SendMessage(messageText))
                        messageText = ""
                    }
                },
                isLoading = state.isLoading
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        // 聊天訊息列表
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ChatMessageList(
                messages = state.messages,
                modifier = Modifier.fillMaxSize()
            )
            
            // 顯示錯誤信息（如果有）
            state.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(error)
                }
            }
            
            // 顯示加載指示器
            if (state.isLoading && state.messages.isNotEmpty()) {
                LoadingIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatInputBar(
    modifier: Modifier = Modifier,
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isLoading: Boolean = false
) {
    Surface(
        tonalElevation = 3.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 訊息輸入框
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageTextChange,
                placeholder = { Text("輸入訊息...") },
                modifier = Modifier.weight(1f),
                maxLines = 5,
                enabled = !isLoading
            )
            
            // 發送按鈕
            Spacer(modifier = Modifier.width(8.dp))
            Box {
                IconButton(
                    onClick = onSendClick,
                    enabled = messageText.isNotBlank() && !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "發送"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatMessageList(
    messages: List<ChatMessage>,
    modifier: Modifier = Modifier
) {
    if (messages.isEmpty()) {
        // 無消息時顯示提示
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("開始與 AI 模型對話吧！")
        }
    } else {
        // 顯示消息列表
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            reverseLayout = false,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message = message)
            }
        }
    }
}