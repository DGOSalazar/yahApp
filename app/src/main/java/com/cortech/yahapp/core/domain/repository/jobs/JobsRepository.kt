package com.cortech.yahapp.core.domain.repository.jobs

import com.cortech.yahapp.core.data.model.jobs.JobPosition


interface JobsRepository {
    suspend fun postJobPosition(position: JobPosition): Result<Unit>
    suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>>
}
