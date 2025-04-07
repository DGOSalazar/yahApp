package com.cortech.yahapp.core.domain.repository

import com.cortech.yahapp.core.data.model.recommendation.RecommendationResponseDto
import okhttp3.MultipartBody

interface HomeRepository {
    suspend fun uploadCv(file: MultipartBody.Part): Result<Unit>
    suspend fun getRecommendedPositions(position: String): Result<RecommendationResponseDto>
}
