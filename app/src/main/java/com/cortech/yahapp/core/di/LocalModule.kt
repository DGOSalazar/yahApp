package com.cortech.yahapp.core.di

import android.content.Context
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.domain.usecase.chat.AnalyzeResumeUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import com.cortech.yahapp.core.domain.usecase.chat.ImproveResumeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAnalyzePdfUseCase(
        generateResponseUseCase: GenerateResponseUseCase
    ): AnalyzeResumeUseCase = AnalyzeResumeUseCase(generateResponseUseCase)

    @Provides
    @Singleton
    fun provideImproveResumeUseCase(
        generateResponseUseCase: GenerateResponseUseCase
    ): ImproveResumeUseCase = ImproveResumeUseCase(generateResponseUseCase)
}
