package com.cortech.yahapp.core.domain.usecase.chat

import com.cortech.yahapp.core.data.model.jobs.JobPosition
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
import javax.inject.Inject

class GetRecommendedPositionsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(cvContent: String): Result<List<JobPosition>> {
        return repository.getRecommendedPositions(cvContent)
    }
}
