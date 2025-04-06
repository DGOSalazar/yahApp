package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.domain.usecase.chat.AnalyzePdfUseCase
import com.cortech.yahapp.core.domain.usecase.chat.ImproveResumeUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PdfModule {
    
    @Provides
    @Singleton
    fun provideAnalyzePdfUseCase(
        generateResponseUseCase: GenerateResponseUseCase
    ): AnalyzePdfUseCase = AnalyzePdfUseCase(generateResponseUseCase)

    @Provides
    @Singleton
    fun provideImproveResumeUseCase(
        generateResponseUseCase: GenerateResponseUseCase
    ): ImproveResumeUseCase = ImproveResumeUseCase(generateResponseUseCase)
}
