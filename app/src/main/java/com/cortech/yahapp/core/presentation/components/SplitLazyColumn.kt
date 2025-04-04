package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SplitLazyColumns(items: List<String>) {
    val halfSize = (items.size + 1) / 2
    Column(Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                items(items.take(halfSize)) { experience ->
                    Text(experience, color = Color.White)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                items(items.drop(halfSize)) { experience ->
                    Text(experience, color = Color.White)
                }
            }
        }
    }
}
