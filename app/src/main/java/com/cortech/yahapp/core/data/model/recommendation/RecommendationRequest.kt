package com.cortech.yahapp.core.data.model.recommendation

import com.google.gson.annotations.SerializedName

data class RecommendationRequest(
    @SerializedName("prompt")
    val prompt: String = ""
)
