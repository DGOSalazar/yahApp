package com.cortech.yahapp.core.domain.usecase.auth

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.domain.model.UserType
import com.cortech.yahapp.core.data.model.JobPosition
import com.cortech.yahapp.core.domain.repository.JobsRepository
import javax.inject.Inject

class PostJobPositionUseCase @Inject constructor(
    private val repository: JobsRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(position: JobPosition): Result<Unit> {
        val userData = userPreferences.getUserData()
            ?: return Result.failure(Exception("Please log in first"))

        if (userData.type != UserType.HUMAN_RESOURCE) {
            return Result.failure(Exception("Only HR personnel can post job positions"))
        }

        return repository.postJobPosition(position)
    }
}
