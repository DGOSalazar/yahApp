package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.core.data.model.jobs.JobPosition
import com.cortech.yahapp.core.domain.repository.jobs.JobsRepository
import javax.inject.Inject

class JobsRepositoryImpl @Inject constructor() : JobsRepository {
    override suspend fun postJobPosition(position: JobPosition): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>> {
        return Result.success(emptyList())
    }
}
