package com.cortech.yahapp.features.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cortech.yahapp.R
import com.cortech.yahapp.core.presentation.components.ShimmerEffect
import com.cortech.yahapp.features.profile.view.component.ProfileContent
import com.cortech.yahapp.features.profile.view.component.ProfileTitle
import com.cortech.yahapp.features.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(state.profile) {
        isLoading = true
        delay(2000)
        isLoading = false
    }

    Scaffold(
        topBar = {
            ProfileTitle(
                onExitClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.error != null) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error ?: stringResource(R.string.application),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = { viewModel.loadProfile() },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            } else {
                ShimmerEffect(
                    isLoading = isLoading,
                    modifier = Modifier.fillMaxSize()
                ) {
                    ProfileContent(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen(
                navController = NavHostController(context = LocalContext.current)
            )
        }
    }
}