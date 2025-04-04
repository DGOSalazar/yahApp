package com.cortech.yahapp.core.data.api.auth

import com.cortech.yahapp.core.data.model.UserProfile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {
    @GET("users/")
    suspend fun getUserByName(
        @Query("name") name: String
    ): Response<UserProfile>
}
