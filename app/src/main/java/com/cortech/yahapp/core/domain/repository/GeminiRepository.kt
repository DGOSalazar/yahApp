package com.cortech.yahapp.core.domain.repository


interface GeminiRepository {
    suspend fun generateResponse(prompt: String): Result<String>
}
