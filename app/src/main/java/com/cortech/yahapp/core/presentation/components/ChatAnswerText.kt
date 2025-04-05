package com.cortech.yahapp.core.presentation.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R

@Composable
fun ChatAnswerText(
    modifier: Modifier = Modifier,
    text: String,
    showCopyButton: Boolean,
    showLikeButton: Boolean,
    onLiked: () -> Unit = {},
    onDisliked: () -> Unit = {},
) {
    var finalText by rememberSaveable { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(true) }
    var isLiked by remember { mutableStateOf(false) }
    var isDisliked by remember { mutableStateOf(false) }
    
    AnimatedText(text = text) { newText ->
        finalText = newText
        isTyping = newText != text
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(top = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // AI Badge
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "YaH!",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            // Answer Text
            Text(
                text = finalText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Typing Indicator
            AnimatedVisibility(
                visible = isTyping,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BouncingDotsLoader(
                    modifier = Modifier.padding(top = 8.dp).height(10.dp)
                )
            }

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showLikeButton) {
                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            if (isLiked) isDisliked = false
                            onLiked()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.width(20.dp),
                            imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = stringResource(R.string.like_description),
                            tint = if (isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    IconButton(
                        onClick = {
                            isDisliked = !isDisliked
                            if (isDisliked) isLiked = false
                            onDisliked()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.width(20.dp),
                            imageVector = if (isDisliked) Icons.Filled.ThumbDown else Icons.Outlined.ThumbDown,
                            contentDescription = stringResource(R.string.dislike_description),
                            tint = if (isDisliked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                if (showCopyButton) {
                    val context = LocalContext.current
                    val snackbarHostState = remember { SnackbarHostState() }
                    
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                    
                    IconButton(
                        onClick = {
                            copyToClipboard(context, text)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.width(20.dp),
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = stringResource(id = R.string.description),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Texto copiado", text)
    clipboard.setPrimaryClip(clip)
}

@Preview(showBackground = true)
@Composable
private fun ChatAnswerTextPreview() {
    ChatAnswerText(
        text = "¡Hola! Soy tu asistente virtual. ¿En qué puedo ayudarte hoy? Estoy aquí para responder tus preguntas y ayudarte con lo que necesites.",
        modifier = Modifier.padding(16.dp),
        showCopyButton = false,
        showLikeButton = false,
    )
}