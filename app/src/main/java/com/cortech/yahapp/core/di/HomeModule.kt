package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.api.HomeApi
import com.cortech.yahapp.core.data.repository.HomeRepositoryImpl
import com.cortech.yahapp.core.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeApi(
        @AIRetrofit retrofit: Retrofit
    ): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeApi: HomeApi
    ): HomeRepository = HomeRepositoryImpl(homeApi)
}
