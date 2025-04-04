package com.cortech.yahapp.features.splash.domain.usecase

import com.cortech.yahapp.core.data.repository.WizelineRepository
import com.cortech.yahapp.features.splash.domain.model.SplashModel
import javax.inject.Inject

class GetWizelineAssetsUseCase @Inject constructor(
    private val repository: WizelineRepository
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
