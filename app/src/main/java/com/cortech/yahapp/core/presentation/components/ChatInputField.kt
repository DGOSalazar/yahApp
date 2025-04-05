package com.cortech.yahapp.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatInputField(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit,
    onAttach: () -> Unit,
    enabled: Boolean = true
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onAttach,
                enabled = enabled,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = stringResource(R.string.attach_file_description),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            TextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                placeholder = {
                    Text(
                        text = stringResource(R.string.send_message_hint),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (textState.text.isNotBlank()) {
                        onSend(textState.text)
                        textState = TextFieldValue("")
                        keyboardController?.hide()
                    }
                }),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 4
            )

            // Botón de enviar
            AnimatedVisibility(
                visible = textState.text.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = {
                        if (textState.text.isNotBlank()) {
                            onSend(textState.text)
                            textState = TextFieldValue("")
                            keyboardController?.hide()
                        }
                    },
                    enabled = enabled && textState.text.isNotBlank(),
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = stringResource(R.string.send_message_description),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatInputField() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChatInputField(
                onSend = { },
                onAttach = { }
            )
        }
    }
}
