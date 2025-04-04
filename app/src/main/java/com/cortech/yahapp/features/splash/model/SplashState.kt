package com.cortech.yahapp.features.splash.model

import com.cortech.yahapp.core.domain.model.SplashModel

data class SplashState(
    val isLoading: Boolean = true,
    val splashData: SplashModel? = null,
    val error: String? = null
)

sealed class SplashEvent {
    object NavigateToHome : SplashEvent()
    object NavigateToLogin : SplashEvent()
    data class ShowError(val message: String) : SplashEvent()
}
