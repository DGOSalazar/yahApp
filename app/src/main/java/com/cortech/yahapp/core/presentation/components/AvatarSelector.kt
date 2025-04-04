package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R

@Composable
fun AvatarSelector(
    selectedAvatar: Int?,
    onAvatarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Select your avatar:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val avatars = listOf(
                R.drawable.av1_24dp,
                R.drawable.av2_24dp,
                R.drawable.av3_24dp,
                R.drawable.av4_24dp,
                R.drawable.av5_24dp
            )

            items(avatars) { avatarResId ->
                Image(
                    painter = painterResource(id = avatarResId),
                    contentDescription = "Avatar option",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 2.dp,
                            color = if (selectedAvatar == avatarResId) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outline
                            },
                            shape = CircleShape
                        )
                        .clickable { onAvatarSelected(avatarResId) }
                        .padding(8.dp)
                )
            }
        }
    }
}
