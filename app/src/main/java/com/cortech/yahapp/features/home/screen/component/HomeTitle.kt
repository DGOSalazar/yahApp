package com.cortech.yahapp.features.home.screen.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTitle(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    onProfileClick: () -> Unit = {},
    onThemeToggle: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "YaH!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onThemeToggle
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            actions = {
                IconButton(onClick = onProfileClick) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTitlePreview() {
    MaterialTheme {
        HomeTitle()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTitleDarkPreview() {
    MaterialTheme {
        HomeTitle(isDarkTheme = true)
    }
}
