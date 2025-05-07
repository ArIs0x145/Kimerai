package io.github.aris0x145.kimerai.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aris0x145.kimerai.data.repository.ChatRepositoryImpl
import io.github.aris0x145.kimerai.data.repository.ModelRepositoryImpl
import io.github.aris0x145.kimerai.domain.repository.ChatRepository
import io.github.aris0x145.kimerai.domain.repository.ModelRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindModelRepository(
        modelRepositoryImpl: ModelRepositoryImpl
    ): ModelRepository
    
    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
}