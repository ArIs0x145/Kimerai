package io.github.aris0x145.kimerai.core.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aris0x145.kimerai.core.common.logging.Logger
import io.github.aris0x145.kimerai.core.common.logging.TimberLogger
import javax.inject.Singleton

/**
 * 核心通用模組的 Hilt 依賴注入模組
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {
    
    @Binds
    @Singleton
    abstract fun provideLogger(timberLogger: TimberLogger): Logger
}
