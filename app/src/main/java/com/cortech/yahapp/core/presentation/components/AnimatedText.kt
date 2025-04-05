package com.cortech.yahapp.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun AnimatedText(
    text: String,
    isTyping: Boolean = false,
    isCompleted: Boolean = false,
    returnText: (String)-> Unit = {}
) {
    var animationState by remember { mutableStateOf(AnimationState()) }

    LaunchedEffect(text) {
        if (animationState.completed && animationState.displayedText == text) {
            return@LaunchedEffect
        }
        animationState = animationState.copy(isTyping = isTyping, completed = isCompleted, displayedText = "")

        text.forEachIndexed { index, _ ->
            animationState = animationState.copy(
                displayedText = text.take(index + 1)
            )
            delay(10L)
        }

        animationState = animationState.copy(
            isTyping = false,
            completed = true
        )
        returnText(animationState.displayedText)
    }
}

data class AnimationState(
    val displayedText: String = "",
    val isTyping: Boolean = false,
    val completed: Boolean = false
)

@Preview
@Composable
private fun AnimatedTextPreview() {
    AnimatedText(text = "Hello, this is an animated text!")
}