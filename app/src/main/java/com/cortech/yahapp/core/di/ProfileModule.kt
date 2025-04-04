package com.cortech.yahapp.core.di

import com.cortech.yahapp.core.data.api.ProfileApi
import com.cortech.yahapp.core.data.repository.ProfileRepositoryImpl
import com.cortech.yahapp.core.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    
    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }
}
