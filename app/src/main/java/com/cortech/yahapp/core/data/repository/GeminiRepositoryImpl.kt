package com.cortech.yahapp.core.data.repository.auth

import com.cortech.yahapp.BuildConfig
import com.cortech.yahapp.core.domain.model.Experience
import com.cortech.yahapp.core.domain.model.Resume
import com.cortech.yahapp.core.domain.repository.GeminiRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor() : GeminiRepository {
    private val model = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    override suspend fun generateResponse(prompt: String): Result<String> = runCatching {
        val response = model.generateContent(prompt)
        response.text ?: throw IllegalStateException("Empty response from Gemini")
    }
}
