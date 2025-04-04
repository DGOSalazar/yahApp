package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.BuildConfig
import com.cortech.yahapp.core.domain.repository.GeminiRepository
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor() : GeminiRepository {
    private val model = GenerativeModel(
        modelName = Constants.GEMINI_MODEL_NAME,
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    override suspend fun generateResponse(prompt: String): Result<String> = runCatching {
        val response = model.generateContent(prompt)
        response.text ?: throw IllegalStateException(Constants.EMPTY_RESPONSE_ERROR)
    }
}
