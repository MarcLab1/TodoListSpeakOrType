package com.todolist.ui.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.todolist.R


@Composable
fun AddNoteTyping(
    insertReminderIntoDatabase: (String) -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Button(
            onClick = {
                openDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            Image(
                painterResource(id = R.drawable.ic_keyboard),
                stringResource(R.string.type),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(85.dp)
                    .clip(CircleShape)
            )
        }
    }

    if (openDialog.value) {
        AddNoteTypingDialogDialog(
            openDialog,
            onAddReminderClick = insertReminderIntoDatabase)
    }
}