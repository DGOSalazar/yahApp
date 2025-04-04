package com.cortech.yahapp.features.splash.di

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.repository.WizelineRepository
import com.cortech.yahapp.core.domain.usecase.CheckUserSessionUseCase
import com.cortech.yahapp.core.domain.usecase.auth.GetWizelineAssetsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SplashModule {

    @Provides
    @ViewModelScoped
    fun provideCheckUserSessionUseCase(
        userPreferences: UserPreferences
    ): CheckUserSessionUseCase {
        return CheckUserSessionUseCase(userPreferences)
    }

    @Provides
    @ViewModelScoped
    fun provideGetWizelineAssetsUseCase(
        repository: WizelineRepository
    ): GetWizelineAssetsUseCase {
        return GetWizelineAssetsUseCase(repository)
    }
}
