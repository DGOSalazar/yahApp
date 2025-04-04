package com.cortech.yahapp.core.domain.repository.auth

import com.cortech.yahapp.core.data.model.JobPosition

interface JobsRepository {
    suspend fun postJobPosition(position: JobPosition): Result<Unit>
    suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>>
}
