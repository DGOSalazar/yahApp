package com.cortech.yahapp.core.data.repository.profile

import com.cortech.yahapp.core.data.api.profile.ProfileApi
import com.cortech.yahapp.core.data.model.auth.UserProfile
import com.cortech.yahapp.core.domain.repository.profile.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun getUserProfile(name: String): Result<UserProfile> {
        return try {
            val response = api.getUserByName(name)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error getting user profile: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
