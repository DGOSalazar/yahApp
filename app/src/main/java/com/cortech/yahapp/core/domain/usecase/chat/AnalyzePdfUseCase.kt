package com.cortech.yahapp.core.domain.usecase.chat

import android.content.Context
import android.net.Uri
import com.cortech.yahapp.core.utils.Constants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import javax.inject.Inject

class AnalyzePdfUseCase @Inject constructor(
    private val generateResponseUseCase: GenerateResponseUseCase
) {
    suspend operator fun invoke(context: Context, uri: Uri): Result<String> {
        return try {
            val text = extractTextFromPdf(context, uri)
            val prompt = buildString {
                appendLine(Constants.PdfAnalysis.PROMPT)
                appendLine(Constants.PdfAnalysis.PROMPT_CV_HEADER)
                append(text)
            }
            
            generateResponseUseCase(prompt)
        } catch (e: Exception) {
            Result.failure(Exception(Constants.PdfAnalysis.ERROR_ANALYZING.format(e.message)))
        }
    }

    private fun extractTextFromPdf(context: Context, uri: Uri): String {
        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                val reader = PdfReader(input)
                val pdfDoc = PdfDocument(reader)
                val stringBuilder = StringBuilder()

                for (i in 1..pdfDoc.numberOfPages) {
                    val page = pdfDoc.getPage(i)
                    stringBuilder.append(PdfTextExtractor.getTextFromPage(page))
                    stringBuilder.append("\n")
                }

                pdfDoc.close()
                reader.close()
                return stringBuilder.toString()
            } ?: throw Exception(Constants.PdfAnalysis.ERROR_READING)
        } catch (e: Exception) {
            throw Exception("${Constants.PdfAnalysis.ERROR_READING}: ${e.message}")
        }
    }
}
