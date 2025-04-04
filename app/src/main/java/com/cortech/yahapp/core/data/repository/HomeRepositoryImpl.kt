package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.core.data.model.auth.EmployeeResponse
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {
    override suspend fun findEmployees(prompt: String, userType: String): Result<List<EmployeeResponse>> {
        return Result.success(emptyList())
    }

    override suspend fun uploadCv(file: MultipartBody.Part, userName: String): Result<Unit> {
        return Result.success(Unit)
    }
}
