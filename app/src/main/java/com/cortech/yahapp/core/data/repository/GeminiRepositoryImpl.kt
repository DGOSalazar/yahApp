package com.cortech.yahapp.core.data.repository

import com.cortech.yahapp.BuildConfig
import com.cortech.yahapp.core.domain.repository.GeminiRepository
import com.cortech.yahapp.core.utils.Constants
import com.cortech.yahapp.core.utils.Constants.GeminiPrompt.Guidelines
import com.cortech.yahapp.core.utils.Constants.GeminiPrompt.HrSupport
import com.cortech.yahapp.core.utils.Constants.GeminiPrompt.SYSTEM_ROLE
import com.cortech.yahapp.core.utils.Constants.GeminiPrompt.WorkerSupport
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiRepositoryImpl @Inject constructor() : GeminiRepository {
    private val model = GenerativeModel(
        modelName = Constants.GEMINI_MODEL_NAME,
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val chat = model.startChat()

    init {
        initializeChat()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initializeChat() {
        val systemPrompt = buildString {
            appendLine(SYSTEM_ROLE)
            appendLine()
            appendLine(WorkerSupport.HEADER)
            appendLine(WorkerSupport.IMPROVE_CV)
            appendLine(WorkerSupport.SHOW_JOBS)
            appendLine(WorkerSupport.CAREER_GUIDANCE)
            appendLine(WorkerSupport.APPLICATION_HELP)
            appendLine(WorkerSupport.SAVE_CV)
            appendLine()
            appendLine(HrSupport.HEADER)
            appendLine(HrSupport.MANAGE_JOBS)
            appendLine(HrSupport.FIND_CANDIDATES)
            appendLine(HrSupport.REVIEW_PROFILES)
            appendLine(HrSupport.OPTIMIZE_DESCRIPTIONS)
            appendLine()
            appendLine(Guidelines.HEADER)
            appendLine(Guidelines.BE_PROFESSIONAL)
            appendLine(Guidelines.PRACTICAL_ADVICE)
            appendLine(Guidelines.POSITIVE_TONE)
            appendLine(Guidelines.CONFIDENTIALITY)
            appendLine(Guidelines.QUALITY_FOCUS)
        }

        kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
            chat.sendMessage(systemPrompt)
        }
    }

    override suspend fun generateResponse(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val response = chat.sendMessage(prompt)
            response.text ?: throw IllegalStateException(Constants.EMPTY_RESPONSE_ERROR)
        }
    }
}
