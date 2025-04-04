package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.api.WizelineApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hackaton-rails-api.duckdns.org:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWizelineApi(retrofit: Retrofit): WizelineApi {
        return retrofit.create(WizelineApi::class.java)
    }
}
