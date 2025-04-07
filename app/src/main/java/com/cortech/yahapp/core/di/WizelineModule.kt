package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.api.WizelineApi
import com.cortech.yahapp.core.data.repository.WizelineRepositoryImpl
import com.cortech.yahapp.core.domain.repository.WizelineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WizelineModule {

    @Provides
    @Singleton
    fun provideProfileApi(
        @MainRetrofit retrofit: Retrofit
    ): WizelineApi {
        return retrofit.create(WizelineApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWizelineRepository(
        api: WizelineApi
    ): WizelineRepository {
        return WizelineRepositoryImpl(api)
    }
}
