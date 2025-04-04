package com.cortech.yahapp.core.domain.usecase

import android.content.Context
import android.net.Uri
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UploadCvUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(context: Context, uri: Uri, userName: String): Result<Unit> {
        return try {
            val file = createTempFileFromUri(context, uri)
            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            
            repository.uploadCv(filePart, userName)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createTempFileFromUri(context: Context, uri: Uri): File {
        val tempFile = File.createTempFile("upload", ".pdf", context.cacheDir)
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }
}
