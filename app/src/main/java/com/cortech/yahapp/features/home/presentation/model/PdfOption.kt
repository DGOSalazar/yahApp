package com.cortech.yahapp.features.home.presentation.model

import com.cortech.yahapp.core.domain.model.PdfAction

data class PdfOption(
    val action: PdfAction,
    val title: String,
    val description: String
)
