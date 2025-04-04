package com.cortech.yahapp.core.data.model.auth

data class JobPosition(
    val jobTitle: String,
    val client: String,
    val skillsRequired: List<Skill>,
    val description: String,
    val typeOfInterview: String
)
