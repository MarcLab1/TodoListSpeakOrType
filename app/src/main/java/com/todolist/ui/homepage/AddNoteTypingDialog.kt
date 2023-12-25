package com.todolist.ui.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todolist.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddNoteTypingDialogDialog(
    openDialog: MutableState<Boolean> = mutableStateOf(true),
    text: String = "",
    onAddReminderClick: (String) -> Unit = {},
) {
    var reminderText by remember { mutableStateOf(text) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    androidx.compose.material.AlertDialog(
        onDismissRequest = { openDialog.value = false },

        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.add_a_reminder),
                )
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = reminderText,
                    onValueChange = { reminderText = it },
                    modifier = Modifier.focusRequester(focusRequester),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddReminderClick(reminderText)
                    openDialog.value = false
                }) {
                Text(stringResource(R.string.add))
            }
        },
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboard?.show()
    }
}