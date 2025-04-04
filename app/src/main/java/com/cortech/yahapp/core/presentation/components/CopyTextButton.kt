package com.cortech.yahapp.core.presentation.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cortech.yahapp.R

@Composable
fun CopyTextButton(
    text: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black,
    showToast: Boolean = true
) {
    val context = LocalContext.current

    IconButton(
        modifier = modifier,
        onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Chat message", text)
            clipboard.setPrimaryClip(clip)
            if (showToast) {
                Toast.makeText(
                    context,
                    context.getString(R.string.text_copied_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
    ) {
        Icon(
            imageVector = Icons.Filled.ContentCopy,
            contentDescription = stringResource(R.string.copy_text_description),
            tint = tint
        )
    }
}
