package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChatQuestionText(text: String) {
    Row(Modifier.fillMaxWidth().padding(8.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = text,
                    color = Color.White
                )
            }
            CopyTextButton(
                text = text,
                modifier = Modifier.size(24.dp).align(Alignment.End).padding(end = 4.dp),
                tint = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun ChatQuestionTextPreview() {
    ChatQuestionText(text = "Could you please help me with this?")
}