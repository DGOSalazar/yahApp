package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTitleShort(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            modifier = modifier.align(Alignment.Center).fillMaxWidth(),
            text = "YaH",
            color = Color.White,
            fontSize = 24.sp,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                shadow = Shadow(
                    color = Color(0xFFB0BEC5), // Light Gray shadow
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )
    }
}