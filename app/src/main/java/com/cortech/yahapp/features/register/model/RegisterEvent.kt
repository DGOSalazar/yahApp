package com.cortech.yahapp.features.register.model

sealed class RegisterEvent {
    object ToggleAuthMode : RegisterEvent()
    object ToggleUserType : RegisterEvent()
    data class UpdateName(val name: String) : RegisterEvent()
    data class UpdateLastname(val lastname: String) : RegisterEvent()
    data class UpdateBirthdate(val birthdate: String) : RegisterEvent()
    data class UpdatePassword(val password: String) : RegisterEvent()
    data class UpdateDescription(val description: String) : RegisterEvent()
    data class UpdateAvatar(val avatarResId: Int) : RegisterEvent()
    object Submit : RegisterEvent()
}
