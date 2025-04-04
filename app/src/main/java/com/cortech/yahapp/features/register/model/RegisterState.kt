package com.cortech.yahapp.features.register.model

import com.cortech.yahapp.core.domain.model.UserType

data class RegisterState(
    val isLoginMode: Boolean = false,
    val isHumanResource: Boolean = false,
    val name: String = "",
    val lastname: String = "",
    val birthdate: String = "",
    val password: String = "",
    val description: String = "",
    val selectedAvatar: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val type: UserType = UserType.EMPLOYEE,
    val isSuccess: Boolean = false,
)
