package io.github.aris0x145.kimerai.domain.repository

import io.github.aris0x145.kimerai.domain.model.AiModel
import kotlinx.coroutines.flow.Flow

/**
 * 處理 AI 模型相關操作的倉庫接口
 */
interface ModelRepository {
    /**
     * 獲取所有可用的 AI 模型
     */
    fun getAllModels(): Flow<List<AiModel>>
    
    /**
     * 獲取當前選中的模型
     */
    fun getSelectedModel(): Flow<AiModel>
    
    /**
     * 設定當前選中的模型
     */
    suspend fun setSelectedModel(modelId: String)
}