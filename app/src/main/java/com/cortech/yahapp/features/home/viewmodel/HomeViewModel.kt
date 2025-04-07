package com.cortech.yahapp.features.home.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.domain.model.chat.ChatMessage
import com.cortech.yahapp.core.domain.model.chat.ChatModelConfig
import com.cortech.yahapp.core.domain.model.chat.PdfAction
import com.cortech.yahapp.core.domain.usecase.chat.AnalyzeResumeUseCase
import com.cortech.yahapp.core.domain.usecase.chat.UploadResumeUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GetRecommendedPositionsUseCase
import com.cortech.yahapp.core.utils.Constants
import com.cortech.yahapp.core.utils.Constants.Features.Home.FIND_COMMAND
import com.cortech.yahapp.core.utils.Constants.Features.Home.NO_VACANCIES_MESSAGE
import com.cortech.yahapp.core.utils.Constants.Features.Home.UPLOAD_CV_ERROR
import com.cortech.yahapp.features.home.model.MessageType
import com.cortech.yahapp.features.home.model.state.HomeEvent
import com.cortech.yahapp.features.home.model.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val generateResponseUseCase: GenerateResponseUseCase,
    private val uploadCvUseCase: UploadResumeUseCase,
    private val analyzeResumeUseCase: AnalyzeResumeUseCase,
    private val getRecommendedPositionsUseCase: GetRecommendedPositionsUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _event = MutableStateFlow<HomeEvent?>(null)
    private fun getUserData() = userPreferences.getUserData()

    init {
        val userMessage = ChatMessage(
            text = Constants.Features.Home.WELCOME_MESSAGE.format(getUserData()?.name),
            messageType = MessageType.Answer,
            fileConfig = ChatModelConfig(
                showCopyButton = false,
                showLikeButton = false
            )
        )
        _state.update { it.copy(
            userName = getUserData()?.name ?: "",
            messages = it.messages + userMessage,
            isLoading = false
        )}
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SendMessage -> handleMessage(event.message)
            is HomeEvent.ShowError -> _event.value = event
            is HomeEvent.PdfSelected -> handlePdfSelected(event.context, event.uri, event.fileName)
            is HomeEvent.PdfActionSelected -> handlePdfAction(event.action)
            is HomeEvent.DismissPdfOptions -> dismissPdfOptions()
        }
    }

    private fun handleMessage(message: String) {
        if (message.isBlank()) return

        viewModelScope.launch {
            val userMessage = ChatMessage(
                text = message,
                messageType = MessageType.Question,
                fileConfig = ChatModelConfig(
                    showDislikeButton = false,
                    showCopyButton = false,
                    showLikeButton = false
                )
            )
            _state.update { it.copy(
                messages = it.messages + userMessage,
                isLoading = true
            )}
            handleGeneralMessage(message)
        }
    }

    private suspend fun handleGeneralMessage(message: String) {
        if (message.startsWith(FIND_COMMAND)) {
            handleRecommendedSearch(message)
            return
        }

        generateResponseUseCase(message)
            .onSuccess { response ->
                val aiMessage = ChatMessage(text = response, messageType = MessageType.Answer)
                _state.update { it.copy(
                    messages = it.messages + aiMessage,
                    isLoading = false,
                    isEmpty = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: Constants.Features.Home.GENERATE_RESPONSE_ERROR)
            }
    }

    private suspend fun handleRecommendedSearch(message: String) {
        getRecommendedPositionsUseCase(message.removePrefix(FIND_COMMAND))
            .onSuccess { positions ->
                val chatMessage = if (positions.name.isEmpty()) {
                    ChatMessage(
                        text = NO_VACANCIES_MESSAGE,
                        messageType = MessageType.Answer
                    )
                } else {
                    ChatMessage(
                        text = "",
                        messageType = MessageType.CvResponse,
                        position = positions
                    )
                }

                _state.update { it.copy(
                    messages = it.messages + chatMessage,
                    isLoading = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: Constants.Features.Home.SEARCH_POSITIONS_ERROR)
            }
    }

    private fun handlePdfSelected(context: Context, uri: Uri, fileName: String) {
        _state.update { it.copy(
            selectedPdf = uri,
            selectedPdfName = fileName,
            showPdfOptions = true,
            showInput = false,
            context = context
        )}
    }

    private fun handlePdfAction(action: PdfAction) {
        val currentState = state.value
        currentState.selectedPdf?.let { uri ->
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                when (action) {
                    PdfAction.ANALYZE -> analyzePdf(uri)
                    PdfAction.UPLOAD -> uploadPdf(uri)
                }

                dismissPdfOptions()
            }
        }
    }

    private suspend fun analyzePdf(uri: Uri) {
        analyzeResumeUseCase(requireNotNull(_state.value.context), uri)
            .onSuccess { analysis ->
                _state.update { it.copy(
                    messages = it.messages + ChatMessage(
                        text = analysis, messageType = MessageType.Answer
                    ),
                    isLoading = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: Constants.Features.Home.ANALYZE_CV_ERROR)
            }
    }

    private suspend fun uploadPdf(uri: Uri) {
        try {
            val currentContext = _state.value.context
            if (currentContext == null) {
                handleError(Constants.Features.Home.CONTEXT_ERROR)
                return
            }
            var responseMessage = ""
            uploadCvUseCase(currentContext, uri)
                .onSuccess {
                    responseMessage = buildString {
                        appendLine(Constants.Features.Home.CvUpload.SUCCESS_MESSAGE)
                        appendLine()
                        appendLine(Constants.Features.Home.CvUpload.RECRUITER_ACCESS)
                        appendLine()
                        appendLine(Constants.Features.Home.CvUpload.HELP_OPTIONS_HEADER)
                        appendLine(Constants.Features.Home.CvUpload.HELP_CV)
                        appendLine(Constants.Features.Home.CvUpload.HELP_INTERVIEW)
                        appendLine(Constants.Features.Home.CvUpload.HELP_TRENDS)
                        append(Constants.Features.Home.CvUpload.HELP_OTHER)
                    }
                    

                }
                .onFailure { error ->
                    responseMessage = UPLOAD_CV_ERROR
                    handleError(error.message ?: UPLOAD_CV_ERROR)
                }

            _state.update { it.copy(
                messages = it.messages + ChatMessage(
                    text = "",
                    fileName = getFileNameFromUri(
                        currentContext,
                        uri
                    ),
                    messageType = MessageType.PDF
                ) + ChatMessage(
                    text = responseMessage,
                    messageType = MessageType.Answer
                ),
                isLoading = false
            )}
        } catch (e: Exception) {
            handleError(Constants.Features.Home.UNEXPECTED_ERROR.format(e.message))
        }
    }

    private fun dismissPdfOptions() {
        _state.update { it.copy(
            showPdfOptions = false,
            showInput = true,
            context = null
        )}
    }

    private fun handleError(message: String) {
        _state.update { it.copy(isLoading = false) }
        _event.value = HomeEvent.ShowError(message)
    }

    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        returnCursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst()) {
                return it.getString(nameIndex)
            }
        }
        return null
    }
}
