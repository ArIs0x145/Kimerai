package io.github.aris0x145.kimerai.domain.model

/**
 * AI 模型的域模型
 */
data class AiModel(
    val id: String,
    val name: String,
    val description: String = "",
    val isLocal: Boolean = false
)