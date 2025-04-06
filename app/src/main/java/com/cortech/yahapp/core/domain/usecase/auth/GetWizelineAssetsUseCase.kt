package com.cortech.yahapp.core.domain.usecase.auth

import com.cortech.yahapp.core.data.repository.AuthRepository
import com.cortech.yahapp.core.domain.model.SplashModel
import javax.inject.Inject

class GetWizelineAssetsUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(isDarkMode: Boolean): Result<SplashModel> {
        val logoType = if (isDarkMode) "dark" else "light"
        return repository.getWizelineLogo(logoType).map { response ->
            SplashModel(
                logo = response["image"] ?: "",
                quote = response["randomSentence"] ?: ""
            )
        }
    }
}
