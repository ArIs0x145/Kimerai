package io.github.aris0x145.kimerai.feature.conversation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.aris0x145.kimerai.domain.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

/**
 * 聊天消息項組件
 */
@Composable
fun ChatMessageItem(
    modifier: Modifier = Modifier,
    message: ChatMessage
) {
    val isUserMessage = message.isUserMessage
    val alignment = if (isUserMessage) Alignment.End else Alignment.Start
    val backgroundColor = if (isUserMessage) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val textColor = if (isUserMessage) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val shape = if (isUserMessage) {
        RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)
    }
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Card(
                shape = shape,
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                ),
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // 顯示時間和模型信息
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = formatTime(message.timestamp),
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.7f),
                            textAlign = TextAlign.End
                        )
                        
                        if (!isUserMessage && message.modelId != null) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "來自 ${message.modelId}",
                                style = MaterialTheme.typography.labelSmall,
                                color = textColor.copy(alpha = 0.7f),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 格式化時間戳
 */
private fun formatTime(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}