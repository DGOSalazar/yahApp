package com.cortech.yahapp.features.profile.presentation.model

import com.cortech.yahapp.core.data.model.UserProfile

data class ProfileState(
    val isLoading: Boolean = true,
    val profile: UserProfile? = null,
    val error: String? = null,
)

sealed class ProfileEvent {
    data object OnDismiss : ProfileEvent()
    data class ShowError(val message: String) : ProfileEvent()
}
