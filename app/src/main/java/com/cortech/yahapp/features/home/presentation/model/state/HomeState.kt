package com.cortech.yahapp.features.home.presentation.model.state

import android.content.Context
import android.net.Uri
import com.cortech.yahapp.core.data.model.ChatMessage
import com.cortech.yahapp.features.home.presentation.model.PdfOption

data class HomeState(
    val userName: String = "",
    val isLoading: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
    val error: String? = null,
    val selectedPdf: Uri? = null,
    val selectedPdfName: String? = null,
    val showPdfOptions: Boolean = false,
    val pdfOptions: List<PdfOption> = emptyList(),
    val showInput: Boolean = true,
    val context: Context? = null,
    val isEmpty: Boolean = true
)
