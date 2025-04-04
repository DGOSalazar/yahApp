package com.cortech.yahapp.core.data.repository.auth

import com.cortech.yahapp.core.data.api.JobsApi
import com.cortech.yahapp.core.data.model.JobPosition
import com.cortech.yahapp.core.domain.repository.JobsRepository
import javax.inject.Inject

class JobsRepositoryImpl @Inject constructor(
    private val api: JobsApi
) : JobsRepository {
    override suspend fun postJobPosition(position: JobPosition): Result<Unit> {
        return try {
            val response = api.postJobPosition(position)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error posting job position: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>> {
        return try {
            val response = api.getRecommendedPositions(mapOf("prompt" to cvContent))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error getting recommended positions: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
