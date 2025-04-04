package com.cortech.yahapp.features.home.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.model.auth.ChatMessage
import com.cortech.yahapp.core.data.model.auth.EmployeeResponse
import com.cortech.yahapp.core.data.model.auth.JobPosition
import com.cortech.yahapp.core.data.model.auth.Skill
import com.cortech.yahapp.core.domain.model.auth.PdfAction
import com.cortech.yahapp.core.domain.usecase.AnalyzePdfUseCase
import com.cortech.yahapp.core.domain.usecase.UploadCvUseCase
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import com.cortech.yahapp.core.domain.usecase.jobs.FindEmployeesUseCase
import com.cortech.yahapp.core.domain.usecase.jobs.GetRecommendedPositionsUseCase
import com.cortech.yahapp.core.domain.usecase.jobs.PostJobPositionUseCase
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
            text = "Welcome to YahApp!,  What can i help you with, ${userData?.name} ?",
            isUserMessage = false
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
            val userMessage = ChatMessage(text = message, isUserMessage = true)
            _state.update { it.copy(
                messages = it.messages + userMessage,
                isLoading = true
            )}

            if (message.startsWith("/find")) {
                handleFindCommand(message)
            } else {
                handleGeneralMessage(message)
            }
        }
    }

    private suspend fun handleFindCommand(message: String) {
        val userData = userPreferences.getUserData()
        if (userData == null) {
            handleError("Please log in first")
            return
        }

        findEmployeesUseCase(message, userType = userData.type)
            .onSuccess { employees ->
                val response = formatEmployeeResponse(employees)
                _state.update { it.copy(
                    messages = it.messages + ChatMessage(text = response, isUserMessage = false),
                    isLoading = false,
                    isEmpty = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: "Failed to find employees")
            }
    }

    private suspend fun handleGeneralMessage(message: String) {
        if (message.startsWith("/job")) {
            handleJobCommand(message)
            return
        }

        if (message.lowercase().contains("vacante") || message.lowercase().contains("empleo")) {
            handleJobSearch(message)
            return
        }

        generateResponseUseCase(message)
            .onSuccess { response ->
                val aiMessage = ChatMessage(text = response, isUserMessage = false)
                _state.update { it.copy(
                    messages = it.messages + aiMessage,
                    isLoading = false,
                    isEmpty = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: "Failed to generate response")
            }
    }

    private suspend fun handleJobCommand(message: String) {
        val userData = userPreferences.getUserData()
        if (userData == null) {
            handleError("Please log in first")
            return
        }

        val jobDetails = message.removePrefix("/job").trim()
        if (jobDetails.isBlank()) {
            handleError("Please provide job details after /job")
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
                            ¡Vacante publicada exitosamente! 🎉
                            
                            Los candidatos podrán encontrar esta posición cuando busquen:
                            - ${position.jobTitle}
                            - ${position.skillsRequired.joinToString { it.name }}
                            
                            ¿Necesitas publicar otra vacante?
                            """.trimIndent(),
                            isUserMessage = false
                        ),
                        isLoading = false
                    )}
                }
                .onFailure { error ->
                    handleError(error.message ?: "Failed to post job position")
                }
        } catch (e: Exception) {
            handleError("Error parsing job details: ${e.message}")
        }
    }

    private suspend fun handleJobSearch(message: String) {
        val userData = userPreferences.getUserData()
        if (userData == null) {
            handleError("Please log in first")
            return
        }

        getRecommendedPositionsUseCase(message)
            .onSuccess { positions ->
                val response = if (positions.isEmpty()) {
                    "Lo siento, no encontré vacantes que coincidan con tu búsqueda. ¿Podrías proporcionar más detalles sobre el tipo de trabajo que buscas?"
                } else {
                    formatJobPositions(positions)
                }

                _state.update { it.copy(
                    messages = it.messages + ChatMessage( text = response, isUserMessage = false),
                    isLoading = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: "Failed to search for positions")
            }
    }

    private fun parseJobPosition(details: String): JobPosition {
        return JobPosition(
            jobTitle = "Software Developer",
            client = "Tech Corp",
            skillsRequired = listOf(Skill("Java", 3)),
            description = details,
            typeOfInterview = "Technical"
        )
    }

    private fun formatJobPositions(positions: List<JobPosition>): String {
        return buildString {
            appendLine("¡Encontré las siguientes vacantes que podrían interesarte! 🎯")
            appendLine()
            positions.forEachIndexed { index, position ->
                appendLine("${index + 1}. ${position.jobTitle} - ${position.client}")
                appendLine("   📝 ${position.description}")
                appendLine("   🔧 Habilidades requeridas:")
                position.skillsRequired.forEach { skill ->
                    appendLine("      - ${skill.name} (${skill.years} años)")
                }
                appendLine("   🎯 Tipo de entrevista: ${position.typeOfInterview}")
                appendLine()
            }
            appendLine("¿Te gustaría saber más detalles sobre alguna de estas posiciones?")
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
                    messages = it.messages + ChatMessage(text = analysis, isUserMessage = false),
                    isLoading = false
                )}
            }
            .onFailure { error ->
                handleError(error.message ?: "Failed to analyze CV")
            }
    }

    private suspend fun uploadPdf(uri: Uri) {
        try {
            val userData = userPreferences.getUserData()
            if (userData == null) {
                handleError("Please log in first")
                return
            }

            val currentContext = _state.value.context
            if (currentContext == null) {
                handleError("Context not available")
                return
            }

            uploadCvUseCase(currentContext, uri, userData.name)
                .onSuccess {
                    val successMessage = buildString {
                        appendLine("¡Listo! Tu CV se ha subido correctamente a la nube. 🎉")
                        appendLine()
                        appendLine("Ahora los reclutadores podrán acceder a tu información profesional. ¿Hay algo más en lo que pueda ayudarte?")
                        appendLine()
                        appendLine("Puedes preguntarme sobre:")
                        appendLine("- Consejos para mejorar tu CV")
                        appendLine("- Cómo prepararte para entrevistas")
                        appendLine("- Tendencias del mercado laboral")
                        append("O cualquier otra cosa que necesites")
                    }
                    
                    _state.update { it.copy(
                        messages = it.messages + ChatMessage(text = successMessage, isUserMessage = false),
                        isLoading = false
                    )}
                }
                .onFailure { error ->
                    handleError(error.message ?: "Failed to upload CV")
                }
        } catch (e: Exception) {
            handleError("Error unexpected: ${e.message}")
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
        if (employees.isEmpty()) return "No employees found matching your criteria."

        return buildString {
            appendLine("Found ${employees.size} matching employees:")
            appendLine()
            employees.forEachIndexed { index, employee ->
                appendLine("${index + 1}. ${employee.name} ${employee.lastname}")
                appendLine("   Birth: ${employee.birthdate}")
                appendLine("   Description: ${employee.description}")
                if (employee.skills.isNotEmpty()) {
                    appendLine("   Skills: ${employee.skills.joinToString(", ")}")
                }
                if (!employee.cvUrl.isNullOrEmpty()) {
                    appendLine("   CV: ${employee.cvUrl}")
                }
                appendLine()
            }
        }
    }
}
