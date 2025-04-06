package com.cortech.yahapp.core.data.model.auth

import com.cortech.yahapp.core.domain.model.auth.UserType

data class UserProfile(
    val name: String = "",
    val lastname: String = "",
    val birthdate: String = "",
    val password: String = "",
    val description: String = "",
    val image: String? = null,
    var type: UserType? = null,
)
