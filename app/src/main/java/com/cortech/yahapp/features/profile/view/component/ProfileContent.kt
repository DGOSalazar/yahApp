package com.cortech.yahapp.features.profile.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cortech.yahapp.core.presentation.components.AvatarProfile
import com.cortech.yahapp.core.presentation.components.ListTextWithLabel
import com.cortech.yahapp.core.util.toComposeBitmap
import com.cortech.yahapp.features.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listLabels = listOf("Name", "Lastname", "Birthdate", "Password", "Description", "Type")
    var listText: List<String>
    with(state.profile) {
         listText = listOf(
             this?.name ?: "Name",
             this?.lastname ?: "Lastname",
             this?.birthdate ?: "Birthdate",
             this?.password ?: "Password",
             this?.description ?: "Description",
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        if (!state.profile?.image.isNullOrEmpty()) {
            state.profile!!.image.toComposeBitmap()?.let { bitmap ->
                AvatarProfile(
                    img = bitmap
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        ListTextWithLabel(listText = listText, listLabel = listLabels)
    }
}

