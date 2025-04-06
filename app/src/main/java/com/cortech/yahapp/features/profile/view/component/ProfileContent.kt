package com.cortech.yahapp.features.profile.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cortech.yahapp.R
import com.cortech.yahapp.core.domain.model.auth.UserType
import com.cortech.yahapp.core.presentation.components.AvatarProfile
import com.cortech.yahapp.core.utils.toComposeBitmap
import com.cortech.yahapp.features.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar y nombre
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!state.profile.image.isNullOrEmpty()) {
                    state.profile.image.toComposeBitmap()?.let { bitmap ->
                        Surface(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            AvatarProfile(img = bitmap)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${state.profile.name} ${state.profile.lastname}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = state.profile.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Información personal
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileSection(
                    title = stringResource(R.string.personal_info),
                    items = listOf(
                        Pair(stringResource(R.string.birthdate), state.profile.birthdate.split("T")[0]),
                        Pair(stringResource(R.string.type), state.profile.type?.asString() ?: "")
                    )
                )
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                ProfileSection(
                    title = stringResource(R.string.account_info),
                    items = listOf(
                        Pair(stringResource(R.string.user_name), state.profile.name),
                        Pair(stringResource(R.string.password), state.profile.password)
                    )
                )
            }
        }
    }
}

@Composable
private fun UserType.asString(): String {
    return if(this == UserType.EMPLOYEE) {
        stringResource(id = R.string.employee)
    } else {
        stringResource(id =R.string.rh)
    }
}

@Composable
private fun ProfileSection(
    title: String,
    items: List<Pair<String, String>>
) {
    var showPassword by remember { mutableStateOf(false) }
    
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        items.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = if (label == stringResource(R.string.password)) {
                            if (showPassword) value else "•".repeat(8)
                        } else value,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (label == stringResource(R.string.password)) {
                        IconButton(
                            modifier = Modifier.size(24.dp).padding(start = 8.dp),
                            onClick = { showPassword = !showPassword }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = if (showPassword) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (showPassword) {
                                    stringResource(R.string.description)
                                } else {
                                    stringResource(R.string.description)
                                },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileContentPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileContent()
        }
    }
}

