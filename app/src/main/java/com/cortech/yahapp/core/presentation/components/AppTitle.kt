package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTitle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                    )
                ) {
                    append("You ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                    )
                ) {
                    append("Are ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFE91E63), // Material Design Red
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                    )
                ) {
                    append("Hired!")
                }
            },
            style = MaterialTheme.typography.headlineLarge.copy(
                textAlign = TextAlign.Center
            ),
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun AppTitlePreview() {
    AppTitle()
}
