package com.cortech.yahapp.core.domain.usecase.chat

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.model.jobs.JobPosition
import com.cortech.yahapp.core.domain.model.auth.UserType
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
import com.cortech.yahapp.core.utils.Constants
import javax.inject.Inject

class PostJobPositionUseCase @Inject constructor(
    private val repository: HomeRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(position: JobPosition): Result<Unit> {
        val userData = userPreferences.getUserData() ?: return Result.failure(Exception(Constants.LOGIN_REQUIRED))

        if (userData.type != UserType.HUMAN_RESOURCE) {
            return Result.failure(Exception(Constants.Features.Home.JobListing.ONLY_HUMAN_RESOURCES))
        }

        return repository.postJobPosition(position)
    }
}
