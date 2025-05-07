package io.github.aris0x145.kimerai.feature.chathistory.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * 聊天歷史功能的依賴注入模組
 */
@Module
@InstallIn(SingletonComponent::class)
object ChatHistoryModule {
    // 這裡可以提供聊天歷史功能特有的依賴項
    // 目前使用的所有依賴都來自核心模組，所以暫時為空
}