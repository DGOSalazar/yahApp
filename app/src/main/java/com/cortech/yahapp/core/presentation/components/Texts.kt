package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashText(
    modifier: Modifier,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            SingText(
                modifier = modifier,
                color = Color.DarkGray,
                text = "You"
            )
            Spacer(Modifier.width(16.dp))
            SingText(
                modifier = modifier,
                color = Color.Black,
                text = "are"
            )
            Spacer(Modifier.width(16.dp))
            SingText(
                modifier = modifier,
                color = Color.Red,
                text = "Hired!"
            )
        }
    }
}

@Composable
fun SingText(
    modifier: Modifier,
    color: Color,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight(900),
        color = color
    )
}

@Preview
@Composable
fun TextPreview() {
    Column {
        SplashText(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}