package com.cortech.yahapp.core.domain.repository.chat

import com.cortech.yahapp.core.data.model.auth.EmployeeResponse
import com.cortech.yahapp.core.data.model.jobs.JobPosition
import okhttp3.MultipartBody

interface HomeRepository {
    // Employee and CV related operations
    suspend fun findEmployees(prompt: String, userType: String): Result<List<EmployeeResponse>>
    suspend fun uploadCv(file: MultipartBody.Part, userName: String): Result<Unit>
    
    // Job related operations
    suspend fun postJobPosition(position: JobPosition): Result<Unit>
    suspend fun getRecommendedPositions(cvContent: String): Result<List<JobPosition>>
}
