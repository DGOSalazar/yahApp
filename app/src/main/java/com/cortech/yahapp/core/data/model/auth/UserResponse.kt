package com.cortech.yahapp.core.data.model.auth

data class UserResponse(
    val name: String,
    val lastname: String,
    val birthdate: String,
    val password: String,
    val description: String?,
    val image: String?
)
