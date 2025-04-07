package com.cortech.yahapp.core.domain.repository

import com.cortech.yahapp.core.data.model.auth.UserProfile
import com.cortech.yahapp.core.data.model.auth.UserRegistrationRequest
import com.cortech.yahapp.core.data.model.auth.UserResponse

interface WizelineRepository {
    suspend fun getUserByName(name: String): Result<UserProfile>
    suspend fun registerUser(user: UserRegistrationRequest): Result<UserResponse>
    suspend fun getWizelineLogo(logoType: String = "dark"): Result<Map<String, String>>
}