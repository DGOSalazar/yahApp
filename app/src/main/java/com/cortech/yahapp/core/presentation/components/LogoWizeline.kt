package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun LogoWizeline(img: ImageBitmap) {
    Image(
        bitmap = img,
        contentDescription = "Wizeline Logo",
        modifier = Modifier.size(200.dp)
    )
}
