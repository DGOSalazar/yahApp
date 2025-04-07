package com.cortech.yahapp.core.data.model.recommendation

import com.cortech.yahapp.core.domain.model.chat.RecommendationModel
import com.google.gson.annotations.SerializedName

data class RecommendationResponseDto(
    val recommendation: RecommendationDto = RecommendationDto()
)

data class RecommendationDto(
    val description: String = "",
    @SerializedName("id")
    val cvId: String = "",
    val name: String = "",
    val requiredSkills: String = ""
)

fun RecommendationDto.toDomain() = RecommendationModel(
    description = description,
    cvId = cvId,
    name = name,
    requiredSkills = requiredSkills
)
