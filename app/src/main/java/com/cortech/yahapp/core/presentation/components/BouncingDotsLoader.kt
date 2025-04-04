package com.cortech.yahapp.core.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BouncingDotsLoader(
    modifier: Modifier = Modifier,
    dotSize: Float = 8f,
    delayUnit: Int = 300,
    dotColor: Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    @Composable
    fun Dot(delay: Int) {
        val translation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = delayUnit * 4
                    0f at 0 with LinearEasing
                    15f at delayUnit with LinearEasing
                    0f at delayUnit * 2
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(delay)
            ), label = ""
        )

        Box(
            Modifier
                .size(dotSize.dp)
                .offset(y = translation.dp)
                .background(
                    color = dotColor,
                    shape = CircleShape
                )
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            Dot(delay = index * (delayUnit / 5))
        }
    }
}
