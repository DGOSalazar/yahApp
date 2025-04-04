package com.cortech.yahapp.core.domain.model.auth

data class HomeActions(
    val onSend: (String) -> Unit = {},
    val onReceive: (String) -> Unit = {},
    val onProfileClick: () -> Unit = {},
    val onAttachmentClick: () -> Unit = {}
)
