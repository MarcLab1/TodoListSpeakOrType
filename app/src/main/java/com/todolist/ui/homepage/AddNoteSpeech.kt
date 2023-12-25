package com.todolist.ui.homepage

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.todolist.R
import java.util.Locale

@Composable
fun AddNoteSpeech(
    insertReminderIntoDatabase: (String) -> Unit,
    speakingReminderStarted: () -> Unit,
    speakingReminderFinished: () -> Unit,
    saveNoteState: MutableState<Boolean>
) {
    val context = LocalContext.current
    var reminder by remember { mutableStateOf(context.getString(R.string.click_to_save_note)) }

    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val list = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                reminder = list?.get(0).toString()
                insertReminderIntoDatabase(reminder)
            }
        }

    LaunchedEffect(saveNoteState.value) {
        if (saveNoteState.value) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.add_a_reminder) )
            resultLauncher.launch(intent)
            speakingReminderFinished()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                speakingReminderStarted()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            Image(
                painterResource(id = R.drawable.ic_action_mic),
                stringResource(R.string.talk),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(85.dp)
                    .clip(CircleShape)
            )
        }
    }
}