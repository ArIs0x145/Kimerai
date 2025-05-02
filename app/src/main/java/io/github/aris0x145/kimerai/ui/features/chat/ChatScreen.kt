package io.github.aris0x145.kimerai.ui.features.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.aris0x145.kimerai.ui.common.components.KimeraiAppBar
import io.github.aris0x145.kimerai.ui.theme.KimeraiTheme

/**
 * 聊天界面
 */
@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }
    
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
                        // 發送訊息邏輯
                        messageText = ""
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        // 聊天訊息列表
        ChatMessageList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun ChatInputBar(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        tonalElevation = 3.dp
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
                maxLines = 5
            )
            
            // 發送按鈕
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onSendClick,
                enabled = messageText.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "發送"
                )
            }
        }
    }
}

@Composable
fun ChatMessageList(
    modifier: Modifier = Modifier
) {
    // 這裡將來會顯示聊天訊息
    // 現在先放個空的占位元素
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("開始與 AI 模型對話吧！")
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    KimeraiTheme {
        ChatScreen(navController = rememberNavController())
    }
}