package com.cortech.yahapp.features.home.screen.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cortech.yahapp.R
import com.cortech.yahapp.core.domain.model.auth.PdfAction
import com.cortech.yahapp.core.presentation.components.BouncingDotsLoader
import com.cortech.yahapp.core.presentation.components.ChatAnswerText
import com.cortech.yahapp.core.presentation.components.ChatInputField
import com.cortech.yahapp.core.presentation.components.ChatQuestionText
import com.cortech.yahapp.core.presentation.components.PdfMessage
import com.cortech.yahapp.core.presentation.components.PdfOptions
import com.cortech.yahapp.features.home.model.PdfOption
import com.cortech.yahapp.features.home.model.state.HomeEvent
import com.cortech.yahapp.features.home.viewmodel.HomeViewModel

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onAttachmentClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            state.selectedPdfName?.let { fileName ->
                PdfMessage(
                    fileName = fileName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                items(
                    items = state.messages,
                    key = { message -> message.id }
                ) { message ->
                    if (message.isUserMessage) {
                        ChatQuestionText(text = message.text)
                    } else {
                        ChatAnswerText(
                            text = message.text,
                            messageId = message.id,
                            hideButtons = state.isEmpty || state.isLoading
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = state.showPdfOptions,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                PdfOptions(
                    options = listOf(
                        PdfOption(
                            action = PdfAction.ANALYZE,
                            title = stringResource(id = R.string.review_cv),
                            description = stringResource(id = R.string.be_helped)
                        ),
                        PdfOption(
                            action = PdfAction.UPLOAD,
                            title = stringResource(id = R.string.upload_cv),
                            description = stringResource(id = R.string.upload_description)
                        )
                    ),
                    onOptionSelected = { option ->
                        viewModel.onEvent(HomeEvent.PdfActionSelected(option.action))
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BouncingDotsLoader(
                        dotColor = MaterialTheme.colorScheme.primary,
                        dotSize = 10f
                    )
                }
            }

            AnimatedVisibility(
                visible = state.showInput,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                ChatInputField(
                    modifier = Modifier.fillMaxWidth(),
                    onSend = { message ->
                        viewModel.onEvent(HomeEvent.SendMessage(message))
                    },
                    onAttach = { onAttachmentClick() }
                )
            }
        }
    }
}