package com.todolist.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.todolist.helpers.HelperDate
import com.todolist.R
import com.todolist.database.Reminder

@Composable
fun ReminderItem(
    index: Int,
    reminder: Reminder,
    deleteReminder: (Reminder) -> Unit,
    navigateToDetailScreen: (Reminder) -> Unit
) {
    Column()
    {
        if (index == 0)
            Divider(thickness = .5.dp, color = MaterialTheme.colorScheme.surfaceVariant)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToDetailScreen(reminder)
                }
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                modifier = Modifier.fillMaxWidth(.1f)
            )
            {
                Text(
                    text = (index + 1).toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(),
                )
            }
            Column(modifier = Modifier.fillMaxWidth(.9f))
            {
                Text(
                    text = reminder.reminder.trim(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 5.dp),
                )
                Text( //text = reminder.id.toString(),
                    text = HelperDate.getFormattedDateEEEMMMdyyyy(reminder.date),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(),
                )
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End)
            {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    modifier = Modifier.clickable {
                        deleteReminder(reminder)
                    })
            }
        }
        Divider(thickness = .5.dp, color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

