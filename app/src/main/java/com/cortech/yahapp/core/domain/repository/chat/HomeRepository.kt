package com.cortech.yahapp.core.domain.repository.auth

import com.cortech.yahapp.core.data.model.EmployeeResponse
import okhttp3.MultipartBody

interface HomeRepository {
    suspend fun findEmployees(prompt: String, userType: String): Result<List<EmployeeResponse>>
    suspend fun uploadCv(file: MultipartBody.Part, userName: String): Result<Unit>
}
