package com.cortech.yahapp.core.data.model.auth

data class EmployeeResponse(
    val name: String,
    val lastname: String,
    val description: String,
    val birthdate: String,
    val cvUrl: String?,
    val skills: List<String>
)
