package com.cortech.yahapp.features.profile.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileTitle(
    onExitClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(Color.White).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier,
            onClick = { onExitClick() }
        ) {
            Icon(
                modifier = Modifier.rotate(90f),
                painter = painterResource(id = android.R.drawable.arrow_down_float),
                contentDescription = "back arrow",
                tint = Color.Black
            )
        }
        Text(text ="Profile")
    }
}

@Preview
@Composable
fun ProfileTitlePreview() {
    ProfileTitle()
}