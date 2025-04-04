package com.cortech.yahapp.core.data.api

import com.cortech.yahapp.core.data.model.auth.UserRegistrationRequest
import com.cortech.yahapp.core.data.model.auth.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WizelineApi {
    @GET("users/")
    suspend fun getUserByName(@Query("name") name: String): Response<UserResponse>

    @POST("users")
    suspend fun registerUser(@Body request: UserRegistrationRequest): Response<UserResponse>

    @GET("assets")
    suspend fun getWizelineLogo(@Query("logo_type") logoType: String): Response<Map<String, String>>
}
