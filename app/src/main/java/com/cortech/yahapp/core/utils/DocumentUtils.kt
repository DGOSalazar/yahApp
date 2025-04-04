package com.cortech.yahapp.core.utils

import android.content.Context
import android.net.Uri
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.InputStream

object DocumentUtils {
    fun extractTextFromDocument(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Could not open document")

        return when {
            uri.toString().endsWith(".pdf", ignoreCase = true) -> {
                extractTextFromPdf(inputStream)
            }
            else -> throw IllegalArgumentException("Unsupported file format")
        }
    }

    private fun extractTextFromPdf(inputStream: InputStream): String {
        return PDDocument.load(inputStream).use { document ->
            PDFTextStripper().getText(document)
        }
    }
}
