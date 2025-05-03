package io.github.aris0x145.kimerai.domain.model

import java.util.UUID

/**
 * 表示聊天中的一條消息
 */
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val isUserMessage: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val modelId: String? = null
)