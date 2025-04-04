package com.cortech.yahapp.features.profile.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cortech.yahapp.R
import com.cortech.yahapp.core.presentation.components.AvatarProfile
import com.cortech.yahapp.core.presentation.components.ListTextWithLabel
import com.cortech.yahapp.core.utils.toComposeBitmap
import com.cortech.yahapp.features.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listLabels = listOf(
        stringResource(id = R.string.name),
        stringResource(id = R.string.last_name),
        stringResource(id = R.string.birthdate),
        stringResource(id = R.string.password),
        stringResource(id = R.string.description),
        stringResource(id = R.string.type)
    )
    var listText: List<String>
    with(state.profile) {
         listText = listOf(
             this?.name ?: "",
             this?.lastname ?: "",
             this?.birthdate ?: "",
             this?.password ?: "",
             this?.description ?: "",
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

