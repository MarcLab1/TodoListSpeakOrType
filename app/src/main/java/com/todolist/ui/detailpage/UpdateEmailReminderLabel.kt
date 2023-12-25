package com.todolist.ui.detailpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryLabel(
    modifier: Modifier = Modifier,
    LeftComposable: @Composable() () -> Unit,
    RightComposable: @Composable() () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background

    ) {
    Column(
        modifier = modifier
            .background(backgroundColor)
            .height(60.dp)
            .fillMaxWidth()
            .padding(10.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            LeftComposable()
            RightComposable()
        }
    }
    Divider(thickness = 0.25.dp)
}

@Composable
fun DateLabel(
    label: String,
    date: String = "",
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {

    PrimaryLabel(
        LeftComposable = { Text(text = label, color = textColor, style = textStyle) },
        RightComposable = { Text(text = date, color = textColor, style = textStyle) },
    )
}

@Composable
fun UpdateEmailReminderLabel(
    modifier: Modifier,
    label: String,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    rightComposable: @Composable() () -> Unit,
) {
    PrimaryLabel(
        LeftComposable = { Text(text = label, color = textColor, style = textStyle) },
        RightComposable = rightComposable,
        modifier = modifier
    )
}
