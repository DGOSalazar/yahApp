package com.cortech.yahapp.core.domain.usecase

import android.content.Context
import android.net.Uri
import com.cortech.yahapp.core.domain.usecase.chat.GenerateResponseUseCase
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import javax.inject.Inject

class ImproveResumeUseCase @Inject constructor(
    private val generateResponseUseCase: GenerateResponseUseCase
) {
    suspend operator fun invoke(context: Context, originalText: String): Result<Uri> {
        return try {
            PDFBoxResourceLoader.init(context)
            
            // Generar el CV mejorado usando Gemini
            val prompt = """
                Based on the following resume, create an improved version that:
                1. Better highlights achievements and skills
                2. Uses more impactful action verbs
                3. Improves formatting and organization
                4. Adds relevant keywords for ATS systems
                
                Original resume:
                $originalText
                
                Provide the improved resume in a clear, professional format.
            """.trimIndent()
            
            val improvedText = generateResponseUseCase(prompt).getOrThrow()
            
            // Crear el nuevo PDF
            val document = PDDocument()
            val page = PDPage()
            document.addPage(page)
            
            PDPageContentStream(document, page).use { contentStream ->
                contentStream.beginText()
                contentStream.setFont(PDType1Font.HELVETICA, 12f)
                contentStream.newLineAtOffset(50f, 700f)
                
                improvedText.split("\\n").forEach { line ->
                    contentStream.showText(line)
                    contentStream.newLineAtOffset(0f, -15f)
                }
                
                contentStream.endText()
            }
            
            // Guardar el PDF en el almacenamiento interno
            val fileName = "improved_resume.pdf"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                document.save(output)
            }
            document.close()
            
            // Devolver el URI del archivo guardado
            val uri = Uri.parse("file://${context.getFileStreamPath(fileName)}")
            Result.success(uri)
        } catch (e: Exception) {
            Result.failure(Exception("Error improving resume: ${e.message}"))
        }
    }
}
