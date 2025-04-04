package com.cortech.yahapp.features.register.domain.model

data class UserRegistration(
    val name: String,
    val lastname: String,
    val birthdate: String,
    val password: String,
    val description: String? = null,
    val image: String? = null,
    val isHumanResource: Boolean = false
)
