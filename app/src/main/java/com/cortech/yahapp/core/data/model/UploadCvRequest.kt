package com.cortech.yahapp.core.data.model.auth

import okhttp3.MultipartBody

data class UploadCvRequest(
    val file: MultipartBody.Part,
    val userName: String
)
