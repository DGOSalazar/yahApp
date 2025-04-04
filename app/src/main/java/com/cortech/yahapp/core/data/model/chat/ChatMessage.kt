package com.cortech.yahapp.core.data.model.auth

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUserMessage: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
