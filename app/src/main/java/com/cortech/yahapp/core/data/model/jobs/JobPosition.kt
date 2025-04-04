package com.cortech.yahapp.core.data.model.jobs

import com.cortech.yahapp.core.data.model.auth.Skill

data class JobPosition(
    val jobTitle: String = "Android Developer",
    val client: String = "Wizeline",
    val skillsRequired: List<String> = listOf("Kotlin", "Java", "Android SDK"),
    val description: String = "Android Developer with 3+ years of experience in Kotlin and Java. Strong knowledge of Android SDK, Android Studio, and RESTful APIs.",
    val typeOfInterview: String = "Technical Interview",
)
