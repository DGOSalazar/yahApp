package com.cortech.yahapp.features.splash.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.domain.usecase.CheckUserSessionUseCase
import com.cortech.yahapp.core.utils.Constants
import com.cortech.yahapp.core.domain.usecase.auth.GetWizelineAssetsUseCase
import com.cortech.yahapp.features.splash.model.SplashEvent
import com.cortech.yahapp.features.splash.model.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserSessionUseCase: CheckUserSessionUseCase,
    private val getWizelineAssetsUseCase: GetWizelineAssetsUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private val _event = MutableStateFlow<SplashEvent?>(null)
    val event: StateFlow<SplashEvent?> = _event.asStateFlow()

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            try {
                val hasSession = checkUserSessionUseCase()
                if (hasSession) {
                    _event.value = SplashEvent.NavigateToHome
                } else {
                    _event.value = SplashEvent.NavigateToLogin
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
                _event.value = SplashEvent.ShowError(e.message ?: Constants.UNKNOWN_ERROR)
            }
        }
    }

    fun loadWizelineContent() {
        viewModelScope.launch {
            try {
                val result = getWizelineAssetsUseCase(false)
                val content = result.getOrThrow()
                _state.value = _state.value.copy(
                    isLoading = false,
                    splashData = content
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun saveImage(image: Bitmap) {
        userPreferences.saveImage(image)
    }
}