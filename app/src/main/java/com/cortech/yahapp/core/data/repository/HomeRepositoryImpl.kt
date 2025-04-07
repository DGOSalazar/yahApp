package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.core.data.api.HomeApi
import com.cortech.yahapp.core.data.model.recommendation.RecommendationRequest
import com.cortech.yahapp.core.data.model.recommendation.RecommendationResponseDto
import com.cortech.yahapp.core.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {

    override suspend fun uploadCv(file: MultipartBody.Part): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = homeApi.uploadCv(file)
                handleResponse(response) {}
            } catch (e: Exception) {
                handleException(e, "uploading CV")
            }
        }

    override suspend fun getRecommendedPositions(position: String): Result<RecommendationResponseDto> =
        withContext(Dispatchers.IO) {
            try {
                val response = homeApi.getRecommendedPositions(RecommendationRequest(position))
                handleResponse(response) { it ?: RecommendationResponseDto() }
            } catch (e: Exception) {
                handleException(e, "getting recommended positions")
            }
        }

    private fun <T, R> handleResponse(response: Response<T>, transform: (T?) -> R): Result<R> {
        return if (response.isSuccessful) {
            Result.success(transform(response.body()))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception(errorBody ?: response.message()))
        }
    }

    private fun handleException(e: Exception, action: String): Result<Nothing> {
        return when (e) {
            is HttpException -> Result.failure(
                Exception("Network error while $action: ${e.message}")
            )
            is IOException -> Result.failure(
                Exception("Connection error while $action: ${e.message}")
            )
            else -> Result.failure(
                Exception("Unexpected error while $action: ${e.message}")
            )
        }
    }
}
