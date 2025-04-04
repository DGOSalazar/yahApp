package com.cortech.yahapp.core.data.api.auth

import com.cortech.yahapp.core.data.model.EmployeeResponse
import com.cortech.yahapp.core.data.model.FindEmployeesRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {
    @POST("find-employees")
    suspend fun findEmployees(
        @Body request: FindEmployeesRequest
    ): Response<List<EmployeeResponse>>

    @Multipart
    @POST("upload-cv")
    suspend fun uploadCv(
        @Part file: MultipartBody.Part,
        @Part("userName") userName: String
    ): Response<Unit>
}
