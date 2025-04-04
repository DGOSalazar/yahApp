package com.cortech.yahapp.core.domain.model.auth

data class CandidateModel(
    val id: Int = 1,
    val name: String = "Joe Goldberg",
    val summary: String = "Android Developer with 5 years of experience in Java, Kotlin, and Flutter development.",
    val position: String = "Android Developer",
    val location: String = "New York, NY",
    val salary: String = "$100,000 p/y",
    val status: String = "Open to work",
    val skills: List<String> = listOf("Java", "Kotlin", "Flutter", "Android", "iOS"),
    val experience: String = "5 years",
    val resume: String = "https://www.linkedin.com/in/joe-goldberg-123456789",
)
