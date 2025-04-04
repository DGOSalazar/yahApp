package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.api.jobs.JobsApi
import com.cortech.yahapp.core.data.repository.jobs.JobsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JobsModule {
    
    @Provides
    @Singleton
    fun provideJobsApi(retrofit: Retrofit): JobsApi {
        return retrofit.create(JobsApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideJobsRepository(): com.cortech.yahapp.core.domain.repository.jobs.JobsRepository {
        return com.cortech.yahapp.core.data.repository.JobsRepositoryImpl()
    }
}
