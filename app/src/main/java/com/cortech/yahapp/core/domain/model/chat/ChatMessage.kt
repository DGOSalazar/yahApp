package com.cortech.yahapp.core.domain.model.chat

import com.cortech.yahapp.features.home.model.MessageType
import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val messageType: MessageType,
    val text: String,
    val fileName: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val fileConfig: ChatModelConfig = ChatModelConfig(),
    val position: RecommendationModel = RecommendationModel(),
)
