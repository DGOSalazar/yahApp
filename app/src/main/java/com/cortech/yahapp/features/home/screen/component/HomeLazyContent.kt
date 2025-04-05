package com.cortech.yahapp.features.home.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.core.data.model.chat.ChatMessage
import com.cortech.yahapp.core.presentation.components.ChatAnswerText
import com.cortech.yahapp.core.presentation.components.ChatQuestionText
import com.cortech.yahapp.core.presentation.components.PdfMessage
import com.cortech.yahapp.features.home.model.MessageType

@Composable
fun HomeLazyContent(
    message: ChatMessage
) {
    when(message.messageType) {
        MessageType.Question -> {
            ChatQuestionText(text = message.text)
        }
        MessageType.Answer -> {
            ChatAnswerText(
                text = message.text,
                showCopyButton = message.fileConfig.showCopyButton,
                showLikeButton = message.fileConfig.showLikeButton,
            )
        }
        MessageType.CvResponse -> {

        }
        MessageType.VacancyResponse -> {

        }
        MessageType.PDF -> {
            message.fileName?.let {
                PdfMessage(
                    fileName = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeLazyContentPreview() {
    HomeLazyContent(
        message = ChatMessage(
            messageType = MessageType.Question,
            text = "What is your name?"
        )
    )
}