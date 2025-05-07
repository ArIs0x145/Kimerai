package io.github.aris0x145.kimerai.core.modelselector.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aris0x145.kimerai.domain.repository.ModelRepository
import javax.inject.Singleton

/**
 * 模型選擇器模組的依賴注入模組
 */
@Module
@InstallIn(SingletonComponent::class)
object ModelSelectorModule {

    // 如果需要提供與模型選擇器相關的特定依賴，可以在這裡添加
    // 例如自定義的模型選擇器服務等
}