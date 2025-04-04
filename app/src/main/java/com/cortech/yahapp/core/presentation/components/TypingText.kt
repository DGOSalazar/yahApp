package com.cortech.yahapp.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

@Composable
fun TypingText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    typingSpeed: Long = 10L // Milisegundos por carácter
) {
    var visibleCharacters by remember { mutableIntStateOf(0) }
    val currentText = text.take(visibleCharacters)

    LaunchedEffect(text) {
        visibleCharacters = 1
        text.forEachIndexed { index, _ ->
            delay(typingSpeed)
            visibleCharacters = index + 1
        }
    }
    
    Text(
        text = currentText,
        modifier = modifier,
        style = style
    )
}
