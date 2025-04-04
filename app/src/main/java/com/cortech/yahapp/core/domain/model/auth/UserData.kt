package com.cortech.yahapp.core.domain.model.auth

enum class UserType {
    HUMAN_RESOURCE,
    EMPLOYEE
}

data class UserData(
    val name: String,
    val type: UserType
)
