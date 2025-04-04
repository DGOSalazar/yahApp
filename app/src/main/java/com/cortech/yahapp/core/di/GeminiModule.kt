package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.repository.GeminiRepositoryImpl
import com.cortech.yahapp.core.domain.repository.GeminiRepository
import com.cortech.yahapp.core.domain.usecase.GenerateResponseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {
    @Provides
    @Singleton
    fun provideGeminiRepository(): GeminiRepository {
        return GeminiRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGenerateResponseUseCase(repository: GeminiRepository): GenerateResponseUseCase {
        return GenerateResponseUseCase(repository)
    }
}
