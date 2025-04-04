package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.core.data.model.auth.UserProfile
import com.cortech.yahapp.core.domain.repository.profile.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {
    override suspend fun getUserProfile(name: String): Result<UserProfile> {
        // TODO: Implement API call
        return Result.success(UserProfile("", "", "", "", "", ""))
    }
}
