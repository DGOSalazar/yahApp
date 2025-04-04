package com.cortech.yahapp.core.data.repository.auth

import com.cortech.yahapp.core.data.api.HomeApi
import com.cortech.yahapp.core.data.model.EmployeeResponse
import com.cortech.yahapp.core.data.model.FindEmployeesRequest
import com.cortech.yahapp.core.domain.repository.HomeRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : HomeRepository {
    override suspend fun findEmployees(prompt: String, userType: String): Result<List<EmployeeResponse>> {
        return try {
            val response = api.findEmployees(FindEmployeesRequest(prompt, userType))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error finding employees: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadCv(file: MultipartBody.Part, userName: String): Result<Unit> {
        return try {
            val response = api.uploadCv(file, userName)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error uploading CV: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
