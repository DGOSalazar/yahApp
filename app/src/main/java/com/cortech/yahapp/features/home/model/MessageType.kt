package com.cortech.yahapp.features.home.model

enum class MessageType {
    /*** Represents a general message.
     */
    Question,

    /**
     * Represents an answer to a question or prompt.
     */
    Answer,

    /**
     * Represents a response related to a CV (Curriculum Vitae).
     */
    CvResponse,

    /**
     * Represents a response related to a job vacancy.
     */
    VacancyResponse,

    /**
     * Represents a PDF document.
     */
    PDF
}