package com.cortech.yahapp.core.domain.repository.profile

import com.cortech.yahapp.core.data.model.auth.UserProfile


interface ProfileRepository {
    suspend fun getUserProfile(name: String): Result<UserProfile>
}
