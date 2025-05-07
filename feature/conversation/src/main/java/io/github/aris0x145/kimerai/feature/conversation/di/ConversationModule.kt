package io.github.aris0x145.kimerai.feature.conversation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * 對話功能模組的依賴注入模組
 */
@Module
@InstallIn(SingletonComponent::class)
object ConversationModule {
    // 這裡可以提供對話功能特有的依賴
    // 由於大部分依賴可能已在 domain 和 data 層提供，這裡暫時保持空白
}