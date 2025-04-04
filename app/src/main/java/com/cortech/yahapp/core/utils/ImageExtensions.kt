package com.cortech.yahapp.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Convierte una cadena Base64 a un Bitmap de Android.
 * Si la cadena es inválida o está vacía, retorna null.
 */
fun String?.toAndroidBitmap(): Bitmap? {
    if (this.isNullOrEmpty()) return null
    
    return try {
        val imageBytes = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } catch (e: Exception) {
        null
    }
}

/**
 * Convierte una cadena Base64 a un ImageBitmap de Jetpack Compose.
 * Si la cadena es inválida o está vacía, retorna null.
 */
fun String?.toComposeBitmap(): ImageBitmap? {
    return this?.toAndroidBitmap()?.asImageBitmap()
}
