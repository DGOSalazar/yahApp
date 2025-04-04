package com.cortech.yahapp.core.domain.model.auth

data class Resume(
    val name: String,
    val position: String,
    val phone: String,
    val linkedIn: String,
    val summary: String,
    val experience: List<Experience>,
    val skills: List<String>
)

data class Experience(
    val company: String,
    val position: String,
    val period: String,
    val description: String
)
