package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.core.data.api.WizelineApi
import com.cortech.yahapp.core.data.model.auth.UserProfile
import com.cortech.yahapp.core.data.model.auth.UserRegistrationRequest
import com.cortech.yahapp.core.data.model.auth.UserResponse
import com.cortech.yahapp.core.domain.repository.WizelineRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizelineRepositoryImpl @Inject constructor(
    private val api: WizelineApi
) : WizelineRepository {
    override suspend fun getUserByName(name: String): Result<UserProfile> {
        return try {
            val response = api.getUserByName(name)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerUser(user: UserRegistrationRequest): Result<UserResponse> {
        return try {
            val response = api.registerUser(user)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to register user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWizelineLogo(logoType: String): Result<Map<String, String>> {
        return try {
            val response = api.getWizelineLogo(logoType)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get logo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
