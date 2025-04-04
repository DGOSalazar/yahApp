package com.cortech.yahapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cortech.yahapp.core.navigation.NavigationConstants.Route
import com.cortech.yahapp.core.navigation.animatedComposable
import com.cortech.yahapp.core.presentation.components.theme.YahAppTheme
import com.cortech.yahapp.features.splash.view.SplashScreen
import com.cortech.yahapp.features.register.view.RegisterScreen
import com.cortech.yahapp.features.home.presentation.screen.HomeScreen
import com.cortech.yahapp.features.profile.presentation.view.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YahAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    YahAppNavigation()
                }
            }
        }
    }
}

@Composable
fun YahAppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Route.SPLASH
    ) {
        animatedComposable(
            route = Route.SPLASH,
            isSplashScreen = true
        ) {
            SplashScreen(navController)
        }
        
        animatedComposable(route = Route.REGISTER) {
            RegisterScreen(navController)
        }
        
        animatedComposable(route = Route.HOME) {
            HomeScreen(navController)
        }
        
        animatedComposable(route = Route.PROFILE) {
            ProfileScreen(navController)
        }
    }
}
