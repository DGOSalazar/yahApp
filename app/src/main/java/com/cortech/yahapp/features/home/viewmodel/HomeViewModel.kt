package com.cortech.yahapp.features.home.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.model.chat.ChatMessage
import com.cortech.yahapp.core.data.model.auth.EmployeeResponse
import com.cortech.yahapp.core.data.model.chat.model.ChatModelConfig
import com.cortech.yahapp.core.data.model.jobs.JobPosition
import com.cortech.yahapp.core.domain.model.auth.PdfAction
import com.cortech.yahapp.core.domain.usecase.chat.AnalyzePdfUseCase
import com.cortech.yahapp.core.domain.usecase.chat.UploadCvUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import com.cortech.yahapp.core.domain.usecase.chat.FindEmployeesUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GetRecommendedPositionsUseCase
import com.cortech.yahapp.core.domain.usecase.chat.PostJobPositionUseCase
import com.cortech.yahapp.core.utils.Constants
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
    private val findEmployeesUseCase: FindEmployeesUseCase,
    private val uploadCvUseCase: UploadCvUseCase,
    private val analyzePdfUseCase: AnalyzePdfUseCase,
    private val postJobPositionUseCase: PostJobPositionUseCase,
    private val getRecommendedPositionsUseCase: GetRecommendedPositionsUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _event = MutableStateFlow<HomeEvent?>(null)

    init {
        val userData = userPreferences.getUserData()
        val userMessage = ChatMessage(
            text = Constants.Features.Home.WELCOME_MESSAGE.format(userData?.name),
            messageType = MessageType.Answer,
            fileConfig = ChatModelConfig(
                showCopyButton = false,
                showLikeButton = false
            )
        )
        _state.update { it.copy(
            userName = userData?.name ?: "",
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

            if (message.startsWith(Constants.Features.Home.FIND_COMMAND)) {
                handleFindCommand(message)
            } else if (message.startsWith(Constants.Features.Home.FILE_COMMAND)) {
                handleFileCommand(message)
            } else {
                handleGeneralMessage(message)
            }
        }
    }

    private suspend fun handleFindCommand(message: String) {
        val userData = userPreferences.getUserData()
        if (userData == null) {
            handleError(Constants.LOGIN_REQUIRED)
            return
        }

        findEmployeesUseCase(message, userType = userData.type)
            .onSuccess { employees ->
                val response = formatEmployeeResponse(employees)
                _state.update { it.copy(
                    messages = it.messages + ChatMessage(text = response, messageType = MessageType.Answer),
                    isLoading = false,
                    isEmpty = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: Constants.Features.Home.FIND_EMPLOYEES_ERROR)
            }
    }

    private suspend fun handleGeneralMessage(message: String) {
        if (message.startsWith(Constants.Features.Home.JOB_COMMAND)) {
            handleJobCommand(message)
            return
        }

        if (message.lowercase().contains("vacante") || message.lowercase().contains("empleo")) {
            handleJobSearch(message)
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

    private suspend fun handleJobCommand(message: String) {
        val userData = userPreferences.getUserData()
        if (userData == null) {
            handleError(Constants.LOGIN_REQUIRED)
            return
        }

        val jobDetails = message.removePrefix(Constants.Features.Home.JOB_COMMAND).trim()
        if (jobDetails.isBlank()) {
            handleError(Constants.Features.Home.JOB_DETAILS_REQUIRED)
            return
        }

        try {
            val position = parseJobPosition(jobDetails)
            postJobPositionUseCase(position)
                .onSuccess {
                    _state.update { it.copy(
                        messages = it.messages + ChatMessage(
                            text =
                            """
                            ${Constants.Features.Home.JOB_PUBLICATION_SUCCESS}
                            
                            ${Constants.Features.Home.JOB_PUBLICATION_DETAILS}
                            - ${position.jobTitle}
                            - ${position.skillsRequired.joinToString { skill -> skill }}
                            
                            ${Constants.Features.Home.SEND_NEW_VACANCY}
                            """.trimIndent(),
                            messageType = MessageType.Answer
                        ),
                        isLoading = false
                    )}
                }
                .onFailure { error ->
                    handleError(error.message ?: Constants.Features.Home.POST_JOB_ERROR)
                }
        } catch (e: Exception) {
            handleError(Constants.Features.Home.JOB_DETAILS_PARSE_ERROR.format(e.message))
        }
    }

    private suspend fun handleFileCommand(message: String) {
        val query = message.removePrefix(Constants.Features.Home.FILE_COMMAND).trim()
        if (query.isBlank()) {
            handleError("Please provide a search term after /file")
            return
        }

        // Actualizar estado a cargando
        _state.update { it.copy(isLoading = true) }

        generateResponseUseCase("Please provide information about $query from the Android Developer documentation. Focus on practical implementation details and best practices.")
            .onSuccess { response ->
                _state.update { it.copy(
                    messages = it.messages + ChatMessage(
                        text = response,
                        messageType = MessageType.Answer,
                        fileConfig = ChatModelConfig(
                            showCopyButton = true,
                            showLikeButton = true
                        )
                    ),
                    isLoading = false,
                    isEmpty = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: Constants.Features.Home.GENERATE_RESPONSE_ERROR)
            }
    }

    private suspend fun handleJobSearch(message: String) {
        val userData = userPreferences.getUserData()
        if (userData == null) {
            handleError(Constants.LOGIN_REQUIRED)
            return
        }

        getRecommendedPositionsUseCase(message)
            .onSuccess { positions ->
                val response = if (positions.isEmpty()) {
                    Constants.Features.Home.NO_VACANCIES_MESSAGE
                } else {
                    formatJobPositions(positions)
                }

                _state.update { it.copy(
                    messages = it.messages + ChatMessage( text = response, messageType = MessageType.Answer),
                    isLoading = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: Constants.Features.Home.SEARCH_POSITIONS_ERROR)
            }
    }

    private fun parseJobPosition(details: String): JobPosition {
        return JobPosition(
            jobTitle = "Software Developer",
            client = "Tech Corp",
            skillsRequired = listOf("Java"),
            description = details,
            typeOfInterview = "Technical"
        )
    }

    private fun formatJobPositions(positions: List<JobPosition>): String {
        return buildString {
            appendLine(Constants.Features.Home.JobListing.VACANCIES_FOUND.format(positions.size))
            appendLine()
            positions.forEachIndexed { index, position ->
                appendLine("${index + 1}. ${position.jobTitle} - ${position.client}")
                appendLine("   📝 ${position.description}")
                appendLine(Constants.Features.Home.JobListing.SKILLS_HEADER)
                position.skillsRequired.forEach { skill ->
                    appendLine("      - $skill")
                }
                appendLine("   ${Constants.Features.Home.JobListing.INTERVIEW_TYPE} ${position.typeOfInterview}")
                appendLine()
            }
            appendLine(Constants.Features.Home.JobListing.MORE_DETAILS)
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
        analyzePdfUseCase(requireNotNull(_state.value.context), uri)
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
            val userData = userPreferences.getUserData()
            if (userData == null) {
                handleError(Constants.LOGIN_REQUIRED)
                return
            }

            val currentContext = _state.value.context
            if (currentContext == null) {
                handleError(Constants.Features.Home.CONTEXT_ERROR)
                return
            }

            uploadCvUseCase(currentContext, uri, userData.name)
                .onSuccess {
                    val successMessage = buildString {
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
                    
                    _state.update { it.copy(
                        messages = it.messages + ChatMessage(
                                    text = "",
                                    fileName = getFileNameFromUri(
                                        currentContext,
                                        uri
                                    ),
                                    messageType = MessageType.PDF
                                ) + ChatMessage(
                                     text = successMessage,
                                     messageType = MessageType.Answer
                                ),
                        isLoading = false
                    )}
                }
                .onFailure { error ->
                    handleError(error.message ?: Constants.Features.Home.UPLOAD_CV_ERROR)
                }
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

    private fun formatEmployeeResponse(employees: List<EmployeeResponse>): String {
        if (employees.isEmpty()) return Constants.Features.Home.NO_EMPLOYEES_FOUND

        return buildString {
            appendLine(Constants.Features.Home.EmployeeListing.EMPLOYEES_FOUND.format(employees.size))
            appendLine()
            employees.forEachIndexed { index, employee ->
                appendLine(Constants.Features.Home.EmployeeListing.EMPLOYEE_FORMAT.format(index + 1, employee.name, employee.lastname))
                appendLine(Constants.Features.Home.EmployeeListing.BIRTH_FORMAT.format(employee.birthdate))
                appendLine(Constants.Features.Home.EmployeeListing.DESCRIPTION_FORMAT.format(employee.description))
                if (employee.skills.isNotEmpty()) {
                    appendLine(Constants.Features.Home.EmployeeListing.SKILLS_FORMAT.format(employee.skills.joinToString(", ")))
                }
                if (!employee.cvUrl.isNullOrEmpty()) {
                    appendLine("   CV: ${employee.cvUrl}")
                }
                appendLine()
            }
        }
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String? {
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
