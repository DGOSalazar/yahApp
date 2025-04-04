package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun AvatarProfile(img: ImageBitmap) {
    Image(
        bitmap = img,
        contentDescription = "Profile Avatar",
        modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.Blue)
    )
}