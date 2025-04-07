package com.cortech.yahapp.features.home.model

import com.cortech.yahapp.core.domain.model.chat.PdfAction

data class PdfOption(
    val action: PdfAction,
    val title: String,
    val description: String
)
