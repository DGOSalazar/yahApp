package com.cortech.yahapp.features.splash.view

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cortech.yahapp.R
import com.cortech.yahapp.core.navigation.NavigationConstants.Route
import com.cortech.yahapp.core.presentation.components.AppTitle
import com.cortech.yahapp.core.presentation.components.LogoWizeline
import com.cortech.yahapp.features.splash.model.SplashEvent
import com.cortech.yahapp.features.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsState()
    val showError = rememberSaveable { false }
    
    LaunchedEffect(Unit) {
        viewModel.loadWizelineContent()
    }

    LaunchedEffect(event) {
        when (event) {
            is SplashEvent.NavigateToHome -> {
                delay(4000)
                navController.navigate(Route.HOME) {
                    popUpTo(Route.SPLASH) { inclusive = true }
                }
            }
            is SplashEvent.NavigateToLogin -> {
                delay(4000)
                navController.navigate(Route.REGISTER) {
                    popUpTo(Route.SPLASH) { inclusive = true }
                }
            }
            is SplashEvent.ShowError -> {
                delay(4000)
            }
            null -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            state.splashData?.logo?.let { base64Image ->
                val bitmap = remember(base64Image) {
                    decodeBase64ToBitmap(base64Image)
                }
                bitmap?.asAndroidBitmap()?.let { viewModel.saveImage(it) }
                bitmap?.let {
                    LogoWizeline(img = it)
                }
            }

            AnimatedYouAreHiredText()

            state.splashData?.quote?.let { quote ->
                Text(
                    text = quote,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    if(showError) {
        AlertDialog(
            onDismissRequest = { 
                navController.popBackStack()
            },
            confirmButton = {},
            text = { 
                Text(text = stringResource(id = R.string.error_message))
            }
        )
    }
}

@Composable
private fun AnimatedYouAreHiredText() {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetX by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -300f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ), label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }
    AppTitle(
        modifier = Modifier.offset(x = offsetX.dp),
    )
}

private fun decodeBase64ToBitmap(base64Image: String): ImageBitmap? {
    return try {
        val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}
