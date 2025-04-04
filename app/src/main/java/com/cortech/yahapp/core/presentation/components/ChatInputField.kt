package com.cortech.yahapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.cortech.yahapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputField(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit,
    onAttach: () -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier.fillMaxWidth().height(90.dp).padding(horizontal = 4.dp)) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .weight(2f)
                .background(Color(0xFF2D2D2D), RoundedCornerShape(30))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier.size(20.dp), onClick = onAttach) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_upload),
                    contentDescription = stringResource(R.string.attach_file_description),
                    tint = Color.White
                )
            }
            TextField(
                value = textState,
                onValueChange = { textState = it },
                placeholder = { Text(stringResource(R.string.send_message_hint), color = Color.Gray, fontSize = 16.sp) },
                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 16.sp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
        }
        IconButton(modifier = Modifier
            .background(Color.DarkGray, RoundedCornerShape(50))
            .weight(.4f)
            .align(Alignment.CenterVertically),
            onClick = {
                if (textState.text.isNotBlank()) {
                    onSend(textState.text)
                    textState = TextFieldValue("")
                }
            }
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_send),
                contentDescription = stringResource(R.string.send_message_description),
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatInputField() {
    ChatInputField(
        onSend = { },
        onAttach = { }
    )
}
