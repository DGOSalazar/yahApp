package com.cortech.yahapp.core.data.api.jobs

import com.cortech.yahapp.core.data.model.auth.JobPosition
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JobsApi {
    @POST("open-positions/")
    suspend fun postJobPosition(
        @Body position: JobPosition
    ): Response<Unit>

    @POST("open-positions/recommended")
    suspend fun getRecommendedPositions(
        @Body request: Map<String, String>
    ): Response<List<JobPosition>>
}
