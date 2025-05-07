package io.github.aris0x145.kimerai.data.repository

import io.github.aris0x145.kimerai.domain.entity.model.AiModel
import io.github.aris0x145.kimerai.domain.repository.ModelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepositoryImpl @Inject constructor() : ModelRepository {
    
    // 預設模型列表
    private val models = listOf(
        AiModel(id = "gpt4o", name = "GPT-4o", description = "OpenAI 的高級大型語言模型"),
        AiModel(id = "claude3", name = "Claude 3 Opus", description = "Anthropic 的高級助手"),
        AiModel(id = "llama3", name = "Llama 3", description = "Meta 的開源大型語言模型"),
        AiModel(id = "gemini", name = "Gemini 1.5 Pro", description = "Google 的多模態 AI 模型"),
        AiModel(id = "local", name = "本地模型", description = "在設備上運行的模型", isLocal = true)
    )
    
    // 所有模型的流
    private val _allModels = MutableStateFlow(models)
    
    // 當前選中的模型 ID
    private val _selectedModelId = MutableStateFlow("gpt4o")
    
    override fun getAllModels(): Flow<List<AiModel>> {
        return _allModels
    }
    
    override fun getSelectedModel(): Flow<AiModel> {
        return _selectedModelId.map { selectedId ->
            models.first { it.id == selectedId }
        }
    }
    
    override suspend fun setSelectedModel(modelId: String) {
        if (models.any { it.id == modelId }) {
            _selectedModelId.value = modelId
        }
    }
}