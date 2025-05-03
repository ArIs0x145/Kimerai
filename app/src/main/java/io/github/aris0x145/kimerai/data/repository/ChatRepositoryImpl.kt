package io.github.aris0x145.kimerai.data.repository

import io.github.aris0x145.kimerai.domain.model.AiModel
import io.github.aris0x145.kimerai.domain.model.ChatMessage
import io.github.aris0x145.kimerai.domain.repository.ChatRepository
import io.github.aris0x145.kimerai.domain.repository.ModelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 聊天數據存儲庫的實現
 * 目前使用內存存儲，後續可擴展為本地數據庫或網絡存儲
 */
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val modelRepository: ModelRepository
) : ChatRepository {
    
    // 內存中的聊天消息列表
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    
    override fun getChatMessages(): Flow<List<ChatMessage>> {
        return _messages
    }
    
    override suspend fun sendMessage(message: ChatMessage): Result<ChatMessage> {
        return try {
            // 添加用戶消息
            val currentMessages = _messages.value.toMutableList()
            currentMessages.add(message)
            _messages.value = currentMessages
            
            // 如果是用戶消息，則生成AI回應
            if (message.isUserMessage) {
                generateAiResponse(message)
            }
            
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun clearMessages() {
        _messages.value = emptyList()
    }
    
    /**
     * 生成 AI 回應
     * 暫時使用簡單的模擬回應，後續可替換為實際 API 調用
     */
    private suspend fun generateAiResponse(userMessage: ChatMessage) {
        try {
            // 獲取當前選中的模型
            val currentModel = modelRepository.getSelectedModel().first()
            
            // 模擬 AI 回應
            val aiResponse = ChatMessage(
                content = "這是來自 ${currentModel.name} 的回應：我收到了您的消息「${userMessage.content}」",
                isUserMessage = false,
                modelId = currentModel.id
            )
            
            // 添加 AI 回應到消息列表
            val currentMessages = _messages.value.toMutableList()
            currentMessages.add(aiResponse)
            _messages.value = currentMessages
        } catch (e: Exception) {
            // 處理錯誤情況
            val errorResponse = ChatMessage(
                content = "抱歉，無法生成回應：${e.message}",
                isUserMessage = false
            )
            val currentMessages = _messages.value.toMutableList()
            currentMessages.add(errorResponse)
            _messages.value = currentMessages
        }
    }
}