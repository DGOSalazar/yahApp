package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListTextWithLabel(
    modifier: Modifier = Modifier,
    listText: List<String>,
    listLabel: List<String>
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(listText.size) { index ->
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = listText[index],
                color = Color.Black,
                fontSize = 24.sp
            )
            Text(
                text = listLabel.getOrNull(index) ?: "",
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun TextWithLabelPreview() {
    ListTextWithLabel(listText = listOf("Hello", "World"), listLabel = listOf("Label", "Label"))
}