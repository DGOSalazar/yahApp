package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R
import kotlinx.coroutines.delay

@Composable
fun ChatAnswerText(
    text: String,
    messageId: String,
    hideButtons: Boolean = false,
    typingSpeed: Long = 30L,
    onLiked: () -> Unit = {},
    onDisliked: () -> Unit = {}
) {
    // Usamos derivedStateOf para mantener el estado incluso durante recomposiciones
    val animationState = remember(messageId) {
        mutableStateOf(AnimationState())
    }
    
    val displayedText by remember(messageId) {
        derivedStateOf { animationState.value.displayedText }
    }
    
    val isTyping by remember(messageId) {
        derivedStateOf { animationState.value.isTyping }
    }

    LaunchedEffect(messageId) {
        // Solo iniciamos la animación si no se ha completado antes
        if (!animationState.value.completed) {
            animationState.value = animationState.value.copy(isTyping = true)
            
            text.forEachIndexed { index, _ ->
                animationState.value = animationState.value.copy(
                    displayedText = text.take(index + 1)
                )
                delay(typingSpeed)
            }
            
            animationState.value = animationState.value.copy(
                isTyping = false,
                completed = true
            )
        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = displayedText,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (isTyping) {
                Text(
                    text = "▋",
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (!hideButtons && !isTyping) {
            Row(Modifier.padding(8.dp)) {
                CopyTextButton(
                    text = text,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                    showToast = false
                )
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp),
                    onClick = onLiked
                ) {
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = stringResource(R.string.like_description),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp),
                    onClick = onDisliked
                ) {
                    Icon(
                        imageVector = Icons.Filled.ThumbDown,
                        contentDescription = stringResource(R.string.dislike_description),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

private data class AnimationState(
    val displayedText: String = "",
    val isTyping: Boolean = true,
    val completed: Boolean = false
)

@Preview(showBackground = true)
@Composable
private fun ChatAnswerTextPreview() {
    ChatAnswerText(
        text = "Sure, I can help you with that.",
        messageId = "preview"
    )
}