package io.github.aris0x145.kimerai.ui.features.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.aris0x145.kimerai.domain.model.ChatMessage
import io.github.aris0x145.kimerai.ui.common.components.KimeraiAppBar
import io.github.aris0x145.kimerai.ui.theme.KimeraiTheme

/**
 * 聊天界面
 */
@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }
    val chatState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            KimeraiAppBar(navController = navController)
        },
        bottomBar = {
            ChatInputBar(
                messageText = messageText,
                onMessageTextChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }
                },
                isLoading = chatState.isLoading
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
                messages = chatState.messages,
                modifier = Modifier.fillMaxSize()
            )
            
            // 顯示錯誤信息（如果有）
            chatState.error?.let { error ->
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
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    KimeraiTheme {
        ChatScreen(navController = rememberNavController())
    }
}