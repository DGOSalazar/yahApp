package com.cortech.yahapp.core.data.repository.chat

import com.cortech.yahapp.core.data.api.chat.HomeApi
import com.cortech.yahapp.core.data.api.jobs.JobsApi
import com.cortech.yahapp.core.data.model.auth.EmployeeResponse
import com.cortech.yahapp.core.data.model.auth.FindEmployeesRequest
import com.cortech.yahapp.core.data.model.jobs.JobPosition
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
import com.cortech.yahapp.core.utils.Constants
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
    private val homeApi: HomeApi,
    private val jobsApi: JobsApi
) : HomeRepository {

    override suspend fun findEmployees(prompt: String, userType: String): Result<List<EmployeeResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val request = FindEmployeesRequest(prompt.trim(), userType)
                val response = homeApi.findEmployees(request)
                handleResponse(response) { it ?: emptyList() }
            } catch (e: Exception) {
                handleException(e, "finding employees")
            }
        }

    override suspend fun uploadCv(file: MultipartBody.Part, userName: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = homeApi.uploadCv(file, userName.trim())
                handleResponse(response) { Unit }
            } catch (e: Exception) {
                handleException(e, "uploading CV")
            }
        }

    override suspend fun postJobPosition(position: JobPosition): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = jobsApi.postJobPosition(position)
                handleResponse(response) { Unit }
            } catch (e: Exception) {
                handleException(e, "posting job position")
            }
        }

    override suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>> =
        withContext(Dispatchers.IO) {
            try {
                val response = jobsApi.getRecommendedPositions(mapOf("prompt" to cvContent.trim()))
                handleResponse(response) { it ?: emptyList() }
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
