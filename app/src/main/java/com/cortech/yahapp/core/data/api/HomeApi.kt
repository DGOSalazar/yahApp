package com.cortech.yahapp.core.data.api

import com.cortech.yahapp.core.data.model.recommendation.RecommendationRequest
import com.cortech.yahapp.core.data.model.recommendation.RecommendationResponseDto
import com.cortech.yahapp.core.utils.Constants.Api.RECOMMEND_SUMMARY
import com.cortech.yahapp.core.utils.Constants.Api.UPLOAD_SUMMARY
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {
    @Multipart
    @POST(UPLOAD_SUMMARY)
    suspend fun uploadCv(
        @Part file: MultipartBody.Part
    ): Response<Unit>

    @POST(RECOMMEND_SUMMARY)
    suspend fun getRecommendedPositions(
        @Body request: RecommendationRequest
    ): Response<RecommendationResponseDto>
}
