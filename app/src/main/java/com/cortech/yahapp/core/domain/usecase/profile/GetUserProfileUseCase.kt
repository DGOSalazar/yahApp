package com.cortech.yahapp.core.domain.usecase.profile

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.model.auth.UserProfile
import com.cortech.yahapp.core.domain.repository.profile.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(): Result<UserProfile> {
        val userData = userPreferences.getUserData()
            ?: return Result.failure(Exception("User not logged in"))
        return repository.getUserProfile(userData.name)
    }
}
