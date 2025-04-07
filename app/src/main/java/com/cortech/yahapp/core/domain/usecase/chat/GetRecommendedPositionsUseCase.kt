package com.cortech.yahapp.core.domain.usecase.chat

import com.cortech.yahapp.core.data.model.recommendation.toDomain
import com.cortech.yahapp.core.domain.model.chat.RecommendationModel
import com.cortech.yahapp.core.domain.repository.HomeRepository
import javax.inject.Inject

class GetRecommendedPositionsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(cvContent: String): Result<RecommendationModel> {
        val result = repository.getRecommendedPositions(cvContent)
        return if (result.isSuccess) {
            val recommendationResponse = result.getOrNull()
            val recommendationDto = recommendationResponse?.recommendation
            Result.success(recommendationDto?.toDomain() ?: RecommendationModel())
        } else {
            val exception = result.exceptionOrNull()
            return Result.failure(exception ?: Exception("Api Error"))
        }
    }
}
