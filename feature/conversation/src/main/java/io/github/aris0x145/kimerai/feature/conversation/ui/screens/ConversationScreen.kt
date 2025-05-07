package io.github.aris0x145.kimerai.feature.conversation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.domain.entity.conversation.ChatMessage
import io.github.aris0x145.kimerai.feature.conversation.ui.components.ChatMessageItem
import io.github.aris0x145.kimerai.feature.conversation.ui.contract.ConversationContract
import io.github.aris0x145.kimerai.feature.conversation.viewmodel.ConversationViewModel
import kotlinx.coroutines.flow.collectLatest
import android.widget.Toast

/**
 * 聊天界面 - 使用 MVI 架構
 */
@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    
    // 處理副作用
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ConversationContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is ConversationContract.Effect.ScrollToBottom -> {
                    if (state.messages.isNotEmpty()) {
                        listState.animateScrollToItem(state.messages.size - 1)
                    }
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("聊天") },
                actions = {
                    IconButton(onClick = { viewModel.handleIntent(ConversationContract.Intent.ClearMessages) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "清空聊天"
                        )
                    }
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "返回"
                            )
                        }
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
                listState = listState,
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
        }
    }
}

@Composable
fun ChatInputBar(
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
fun ChatMessageList(
    messages: List<ChatMessage>,
    listState: LazyListState,
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
            state = listState,
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