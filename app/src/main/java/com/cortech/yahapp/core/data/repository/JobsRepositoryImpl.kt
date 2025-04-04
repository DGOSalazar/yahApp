package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.core.data.model.auth.JobPosition
import com.cortech.yahapp.core.domain.repository.jobs.JobsRepository
import javax.inject.Inject

class JobsRepositoryImpl @Inject constructor() : JobsRepository {
    override suspend fun postJobPosition(position: JobPosition): Result<Unit> {
        // TODO: Implement API call
        return Result.success(Unit)
    }

    override suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>> {
        // TODO: Implement API call
        return Result.success(emptyList())
    }
}
