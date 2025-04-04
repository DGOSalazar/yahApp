package com.cortech.yahapp.core.domain.model.chat

data class HomeActions(
    val onSend: (String) -> Unit = {},
    val onReceive: (String) -> Unit = {},
    val onProfileClick: () -> Unit = {},
    val onAttachmentClick: () -> Unit = {}
)
