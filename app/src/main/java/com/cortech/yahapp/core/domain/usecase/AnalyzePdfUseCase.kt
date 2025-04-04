package com.cortech.yahapp.core.domain.usecase

import android.content.Context
import android.net.Uri
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import javax.inject.Inject

class AnalyzePdfUseCase @Inject constructor(
    private val generateResponseUseCase: GenerateResponseUseCase
) {
    suspend operator fun invoke(context: Context, uri: Uri): Result<String> {
        return try {
            val text = extractTextFromPdf(context, uri)
            val prompt = buildString {
                appendLine("Analiza el siguiente CV y proporciona un resumen detallado incluyendo:")
                appendLine("1. Experiencia laboral relevante")
                appendLine("2. Habilidades principales")
                appendLine("3. Educación")
                appendLine("4. Áreas de especialización")
                appendLine("5. Recomendaciones de mejora si las hay")
                appendLine("\nCV:")
                append(text)
            }
            
            generateResponseUseCase(prompt)
        } catch (e: Exception) {
            Result.failure(Exception("Error analyzing PDF: ${e.message}"))
        }
    }

    private fun extractTextFromPdf(context: Context, uri: Uri): String {
        context.contentResolver.openInputStream(uri)?.use { input ->
            PDDocument.load(input).use { document ->
                return PDFTextStripper().getText(document)
            }
        }
        throw Exception("Could not read PDF file")
    }
}
