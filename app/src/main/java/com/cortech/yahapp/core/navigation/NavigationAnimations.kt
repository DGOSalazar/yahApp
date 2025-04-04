package com.cortech.yahapp.core.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val ANIMATION_DURATION = 500

fun NavGraphBuilder.animatedComposable(
    route: String,
    isSplashScreen: Boolean = false,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            if (!isSplashScreen) {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(ANIMATION_DURATION)
                ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
            } else null
        },
        exitTransition = {
            if (isSplashScreen) {
                fadeOut(animationSpec = tween(ANIMATION_DURATION))
            } else {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(ANIMATION_DURATION)
                ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
            }
        },
        popEnterTransition = {
            if (isSplashScreen) {
                fadeIn(animationSpec = tween(ANIMATION_DURATION))
            } else {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(ANIMATION_DURATION)
                ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
            }
        },
        popExitTransition = {
            if (!isSplashScreen) {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(ANIMATION_DURATION)
                ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
            } else null
        }
    ) {
        content(it)
    }
}
