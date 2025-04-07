package com.cortech.yahapp.core.presentation.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChatQuestionText(
    modifier: Modifier = Modifier,
    text: String,
    showCopyButton: Boolean = true
) {
    var showSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxWidth().padding(top = 16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterEnd)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 4.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }

            // Message Surface
            Surface(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 4.dp
                    )),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )

                    if (showCopyButton) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    copyToClipboard(context, text)
                                    showSnackbar = true
                                },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copiar mensaje",
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.6f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Snackbar
        if (showSnackbar) {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = "Mensaje copiado al portapapeles",
                    duration = SnackbarDuration.Short
                )
                showSnackbar = false
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Mensaje copiado", text)
    clipboard.setPrimaryClip(clip)
}

@Preview(showBackground = true)
@Composable
fun ChatQuestionTextPreview() {
    MaterialTheme {
        ChatQuestionText(
            text = "¿Podrías ayudarme a entender cómo funciona este código?",
            modifier = Modifier.padding(16.dp)
        )
    }
}