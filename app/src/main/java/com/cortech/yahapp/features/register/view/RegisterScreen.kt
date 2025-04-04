package com.cortech.yahapp.features.register.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cortech.yahapp.R
import com.cortech.yahapp.core.navigation.NavigationConstants.Route
import com.cortech.yahapp.core.presentation.components.AppTitle
import com.cortech.yahapp.core.presentation.components.AvatarSelector
import com.cortech.yahapp.core.utils.Constants
import com.cortech.yahapp.features.register.model.RegisterEvent
import com.cortech.yahapp.features.register.viewmodel.RegisterViewModel
import java.util.*

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(Route.HOME) {
                popUpTo(Route.REGISTER) { inclusive = true }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppTitle(
                modifier = Modifier
            )
            viewModel.getWizelineLogo().let {
                it?.asImageBitmap()?.let { it1 ->
                    Image(
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        bitmap = it1,
                        contentDescription = stringResource(id = R.string.wizeline_logo)
                    )
                }
            }

            FilterChip(
                selected = state.isLoginMode,
                onClick = { viewModel.onEvent(RegisterEvent.ToggleAuthMode) },
                label = {
                    Text(
                        if (state.isLoginMode)
                            stringResource(id = R.string.login)
                        else stringResource(
                            id = R.string.register
                        )
                    )
                }
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(RegisterEvent.UpdateName(it)) },
                label = { Text(stringResource(
                    id = R.string.name
                )) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            if (!state.isLoginMode) {
                OutlinedTextField(
                    value = state.lastname,
                    onValueChange = { viewModel.onEvent(RegisterEvent.UpdateLastname(it)) },
                    label = { Text(stringResource(id = R.string.last_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = state.birthdate,
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.birthdate)) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                    viewModel.onEvent(
                                        RegisterEvent.UpdateBirthdate(
                                            String.format(Constants.Features.Register.DATE_FORMAT, year, month + 1, dayOfMonth)
                                        )
                                    )
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }) {
                            Text(Constants.Features.Register.CALENDAR_EMOJI)
                        }
                    }
                )

                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.onEvent(RegisterEvent.UpdateDescription(it)) },
                    label = { Text(stringResource(id = R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.UpdatePassword(it)) },
                label = { Text(stringResource(id = R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            if (!state.isLoginMode) {
                AvatarSelector(
                    selectedAvatar = state.selectedAvatar,
                    onAvatarSelected = { avatarResId ->
                        viewModel.onEvent(RegisterEvent.UpdateAvatar(avatarResId))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = Constants.Features.Register.USER_TYPE_LABEL,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )

                FilterChip(
                    selected = state.isHumanResource,
                    onClick = { viewModel.onEvent(RegisterEvent.ToggleUserType) },
                    label = {
                        Text(
                            if (state.isHumanResource)
                                stringResource(id = R.string.rh)
                            else stringResource(
                                id = R.string.register
                            )
                        )
                    }
                )
            }

            Button(
                onClick = { viewModel.onEvent(RegisterEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(if (state.isLoginMode) stringResource(id = R.string.login) else stringResource(
                        id = R.string.register
                    ))
                }
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
