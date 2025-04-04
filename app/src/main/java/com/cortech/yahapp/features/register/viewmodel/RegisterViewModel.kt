package com.cortech.yahapp.features.register.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.domain.model.UserData
import com.cortech.yahapp.core.domain.model.UserType
import com.cortech.yahapp.core.utils.ImageUtils
import com.cortech.yahapp.features.register.domain.model.UserRegistration
import com.cortech.yahapp.features.register.domain.usecase.LoginUserUseCase
import com.cortech.yahapp.features.register.domain.usecase.RegisterUserUseCase
import com.cortech.yahapp.features.register.model.RegisterEvent
import com.cortech.yahapp.features.register.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val userPreferences: UserPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.ToggleAuthMode -> {
                state = state.copy(isLoginMode = !state.isLoginMode)
            }
            is RegisterEvent.ToggleUserType -> {
                state = state.copy(isHumanResource = !state.isHumanResource)
            }
            is RegisterEvent.UpdateName -> {
                state = state.copy(name = event.name)
            }
            is RegisterEvent.UpdateLastname -> {
                state = state.copy(lastname = event.lastname)
            }
            is RegisterEvent.UpdateBirthdate -> {
                state = state.copy(birthdate = event.birthdate)
            }
            is RegisterEvent.UpdatePassword -> {
                state = state.copy(password = event.password)
            }
            is RegisterEvent.UpdateDescription -> {
                state = state.copy(description = event.description)
            }
            is RegisterEvent.UpdateAvatar -> {
                state = state.copy(selectedAvatar = event.avatarResId)
            }
            RegisterEvent.Submit -> {
                if (state.isLoginMode) {
                    login()
                } else {
                    register()
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val result = registerUserUseCase(
                UserRegistration(
                    name = state.name,
                    lastname = state.lastname,
                    birthdate = state.birthdate,
                    password = state.password,
                    description = state.description,
                    image = state.selectedAvatar?.let { avatarResId ->
                        ImageUtils.vectorDrawableToBase64(context, avatarResId)
                    }
                )
            )
            if (result.isSuccess) {
                userPreferences.saveUserData(
                    UserData(
                        name = state.name,
                        type = if(state.isHumanResource) UserType.HUMAN_RESOURCE
                        else UserType.EMPLOYEE
                    )
                )
            }
            state = state.copy(
                isLoading = false,
                error = result.exceptionOrNull()?.message,
                isSuccess = result.isSuccess
            )
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val result = loginUserUseCase(state.name, state.password)
            state = state.copy(
                isLoading = false,
                error = result.exceptionOrNull()?.message,
                isSuccess = result.isSuccess
            )
        }
    }

    fun getWizelineLogo() : Bitmap? {
        return userPreferences.getImage()
    }
}


