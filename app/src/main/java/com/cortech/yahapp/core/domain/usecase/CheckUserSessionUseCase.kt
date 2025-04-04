package com.cortech.yahapp.features.splash.domain.usecase

import com.cortech.yahapp.core.data.local.UserPreferences
import javax.inject.Inject

class CheckUserSessionUseCase @Inject constructor(
    private val userPreferences: UserPreferences
) {
    operator fun invoke(): Boolean {
        val userData = userPreferences.getUserData() ?: return false
        return userData.name.isNotEmpty()
    }
}
