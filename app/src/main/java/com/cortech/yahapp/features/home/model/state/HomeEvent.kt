package com.cortech.yahapp.features.home.model.state

import android.content.Context
import android.net.Uri
import com.cortech.yahapp.core.domain.model.auth.PdfAction

sealed class HomeEvent {
    data class SendMessage(val message: String) : HomeEvent()
    data class ShowError(val message: String) : HomeEvent()
    data class PdfSelected(val context: Context, val uri: Uri, val fileName: String) : HomeEvent()
    data class PdfActionSelected(val action: PdfAction) : HomeEvent()
    object DismissPdfOptions : HomeEvent()
}
