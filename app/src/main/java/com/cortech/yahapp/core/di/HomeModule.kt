package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.api.chat.HomeApi
import com.cortech.yahapp.core.data.api.jobs.JobsApi
import com.cortech.yahapp.core.data.repository.chat.HomeRepositoryImpl
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
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
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJobsApi(retrofit: Retrofit): JobsApi {
        return retrofit.create(JobsApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideHomeRepository(api: HomeApi, job: JobsApi): HomeRepository {
        return HomeRepositoryImpl(api, job)
    }
}
