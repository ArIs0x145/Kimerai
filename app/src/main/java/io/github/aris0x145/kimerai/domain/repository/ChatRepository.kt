package io.github.aris0x145.kimerai.domain.repository

import io.github.aris0x145.kimerai.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

/**
 * 管理聊天消息的存儲庫接口
 */
interface ChatRepository {
    /**
     * 獲取聊天消息流
     */
    fun getChatMessages(): Flow<List<ChatMessage>>
    
    /**
     * 發送新的聊天消息
     */
    suspend fun sendMessage(message: ChatMessage): Result<ChatMessage>
    
    /**
     * 清空聊天記錄
     */
    suspend fun clearMessages()
}