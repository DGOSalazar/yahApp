package com.cortech.yahapp.core.domain.model.auth

fun String.toUserType(): UserType {
    return when (this.uppercase()) {
        "HUMAN_RESOURCE" -> UserType.HUMAN_RESOURCE
        else -> UserType.EMPLOYEE
    }
}
