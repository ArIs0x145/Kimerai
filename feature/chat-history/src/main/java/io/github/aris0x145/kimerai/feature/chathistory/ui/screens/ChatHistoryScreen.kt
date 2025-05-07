package io.github.aris0x145.kimerai.feature.chathistory.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.aris0x145.kimerai.feature.chathistory.ui.contract.ChatHistoryContract
import io.github.aris0x145.kimerai.feature.chathistory.viewmodel.ChatHistoryViewModel
import kotlinx.coroutines.flow.collectLatest
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 聊天歷史記錄畫面 - 使用 MVI 架構
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ChatHistoryViewModel = hiltViewModel(),
    onNavigateToChat: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // 處理副作用
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ChatHistoryContract.Effect.NavigateToChat -> {
                    onNavigateToChat(effect.sessionId)
                }
                is ChatHistoryContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("聊天歷史") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                actions = {
                    // 新建聊天室按鈕
                    IconButton(
                        onClick = { viewModel.handleIntent(ChatHistoryContract.Intent.CreateNewSession()) }
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
        } else if (state.chatSessions.isEmpty()) {
            // 顯示空狀態
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "暫無聊天記錄\n點擊右上角「+」開始新的對話",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            // 顯示聊天會話列表
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
            ) {
                items(state.chatSessions) { session ->
                    ChatSessionItem(
                        session = session,
                        onClick = { viewModel.handleIntent(ChatHistoryContract.Intent.OpenSession(session.id)) },
                        onDelete = { viewModel.handleIntent(ChatHistoryContract.Intent.DeleteSession(session.id)) }
                    )
                }
            }
        }
        
        // 顯示錯誤信息
        state.error?.let { error ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSessionItem(
    session: ChatHistoryContract.ChatSession,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ListItem(
            headlineContent = { 
                Text(
                    text = session.title,
                    style = MaterialTheme.typography.titleMedium
                ) 
            },
            supportingContent = { 
                Text(
                    text = session.lastMessage,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                ) 
            },
            trailingContent = {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "刪除"
                    )
                }
            },
            overlineContent = {
                Text(
                    text = formatDate(session.timestamp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        )
    }
}

/**
 * 格式化日期
 */
private fun formatDate(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60 * 60 * 1000 -> "剛剛"
        diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}小時前"
        else -> {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            dateFormat.format(Date(timestamp))
        }
    }
}