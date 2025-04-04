package com.cortech.yahapp.core.domain.usecase.chat

import com.cortech.yahapp.core.domain.repository.GeminiRepository
import javax.inject.Inject

class GenerateResponseUseCase @Inject constructor(
    private val repository: GeminiRepository
) {
    suspend operator fun invoke(prompt: String): Result<String> =
        repository.generateResponse(prompt)
}
