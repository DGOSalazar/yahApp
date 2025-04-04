package com.cortech.yahapp.core.domain.repository.auth

import com.cortech.yahapp.core.data.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(name: String): Result<UserProfile>
}
