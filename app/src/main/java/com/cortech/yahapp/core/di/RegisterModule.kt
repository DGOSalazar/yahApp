package com.cortech.yahapp.features.register.di

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.repository.AuthRepository
import com.cortech.yahapp.core.domain.usecase.auth.LoginUserUseCase
import com.cortech.yahapp.core.domain.usecase.auth.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RegisterModule {

    @Provides
    @ViewModelScoped
    fun provideRegisterUserUseCase(
        repository: AuthRepository,
        userPreferences: UserPreferences
    ): RegisterUserUseCase {
        return RegisterUserUseCase(repository, userPreferences)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginUserUseCase(
        repository: AuthRepository,
        userPreferences: UserPreferences
    ): LoginUserUseCase {
        return LoginUserUseCase(repository, userPreferences)
    }
}
