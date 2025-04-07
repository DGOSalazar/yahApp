package com.cortech.yahapp.core.data.api

import com.cortech.yahapp.core.data.model.auth.UserProfile
import com.cortech.yahapp.core.data.model.auth.UserRegistrationRequest
import com.cortech.yahapp.core.data.model.auth.UserResponse
import com.cortech.yahapp.core.utils.Constants
import com.cortech.yahapp.core.utils.Constants.Api.LOGO_TYPE
import com.cortech.yahapp.core.utils.Constants.Api.USERS_QUERY_PARAM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WizelineApi {
    @GET(Constants.Api.USERS_ENDPOINT)
    suspend fun getUserByName(@Query(USERS_QUERY_PARAM) name: String): Response<UserProfile>

    @POST("users")
    suspend fun registerUser(@Body request: UserRegistrationRequest): Response<UserResponse>

    @GET("assets")
    suspend fun getWizelineLogo(@Query(LOGO_TYPE) logoType: String): Response<Map<String, String>>
}
