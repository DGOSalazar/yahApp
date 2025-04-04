package com.cortech.yahapp.core.domain.usecase.auth

import com.cortech.yahapp.core.data.model.JobPosition
import com.cortech.yahapp.core.domain.repository.JobsRepository
import javax.inject.Inject

class GetRecommendedPositionsUseCase @Inject constructor(
    private val repository: JobsRepository
) {
    suspend operator fun invoke(cvContent: String): Result<List<JobPosition>> {
        return repository.getRecommendedPositions(cvContent)
    }
}
