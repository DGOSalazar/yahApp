package com.cortech.yahapp.core.domain.repository.auth

import com.cortech.yahapp.core.domain.model.Resume

interface GeminiRepository {
    suspend fun generateResponse(prompt: String): Result<String>
}
