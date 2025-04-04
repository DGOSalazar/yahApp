package com.cortech.yahapp.features.home.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.core.presentation.components.AppTitleShort

@Composable
fun HomeTitle(
    onProfileClick: () -> Unit = {},
    onThemeToggle: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF2D2D2D)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onThemeToggle
        ) {
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = "Switch to Dark Mode",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        AppTitleShort(
            modifier = Modifier.weight(2f)
        )

        IconButton(
            modifier = Modifier,
            onClick = onProfileClick
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Profile",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTitlePreview() {
    HomeTitle()
}
