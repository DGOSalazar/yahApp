package com.cortech.yahapp.features.home.presentation.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cortech.yahapp.core.navigation.NavigationConstants.Route
import com.cortech.yahapp.core.domain.model.HomeActions
import com.cortech.yahapp.features.home.presentation.screen.component.HomeContent
import com.cortech.yahapp.features.home.presentation.screen.component.HomeTitle
import com.cortech.yahapp.features.home.presentation.model.state.HomeEvent
import com.cortech.yahapp.features.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { selectedUri -> processSelectedPdf(
                context = context,
                selectedUri = selectedUri,
                viewModel = viewModel
            )
        }
    }

    val actions = remember {
        HomeActions(
            onProfileClick = {
                navController.navigate(Route.PROFILE)
            },
            onAttachmentClick = {
                filePickerLauncher.launch(arrayOf("application/pdf"))
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTitle(
                onProfileClick = actions.onProfileClick,
            )
        }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onAttachmentClick = actions.onAttachmentClick
        ) {
        }
    }
}

private fun processSelectedPdf(
    context: Context,
    selectedUri:Uri,
    viewModel: HomeViewModel
) {
    try {
        context.contentResolver.takePersistableUriPermission(
            selectedUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val fileName = getFileNameFromUri(context, selectedUri)

        viewModel.onEvent(
            HomeEvent.PdfSelected(
                context = context,
                uri = selectedUri,
                fileName = fileName
            )
        )
    } catch (e: Exception) {
        viewModel.onEvent(HomeEvent.ShowError("Erroral seleccionar el archivo: ${e.message}"))
    }
}

private fun getFileNameFromUri(context: Context, uri: Uri): String {
    return context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        cursor.getString(nameIndex)
    } ?: "Documento PDF"
}
