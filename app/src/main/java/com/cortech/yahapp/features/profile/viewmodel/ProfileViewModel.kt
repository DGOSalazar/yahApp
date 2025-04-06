package com.cortech.yahapp.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.domain.usecase.profile.GetUserProfileUseCase
import com.cortech.yahapp.features.profile.model.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            getUserProfileUseCase()
                .onSuccess { profile ->
                    profile.type = userPreferences.getUserData()?.type
                    _state.value = state.value.copy(
                        isLoading = false,
                        profile = profile,
                        error = null
                    )
                }
                .onFailure { error ->
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }
}
