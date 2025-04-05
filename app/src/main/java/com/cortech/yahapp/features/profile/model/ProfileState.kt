package com.cortech.yahapp.features.profile.model

import com.cortech.yahapp.core.data.model.auth.UserProfile


data class ProfileState(
    val isLoading: Boolean = true,
    val profile: UserProfile = UserProfile(),
    val error: String? = null,
)

sealed class ProfileEvent {
    data object OnDismiss : ProfileEvent()
    data class ShowError(val message: String) : ProfileEvent()
}
