package com.todolist.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.december2023.voice.ui.Routes
import com.todolist.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(label: String, onNavigateListScreen: () -> Unit) {

    TopAppBar(
        title = { Text(text = label, style = MaterialTheme.typography.labelLarge) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        actions = {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Reminders",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 5.dp, end = 15.dp)
                    .clickable { onNavigateListScreen() }
            )
        }
    )
}

@Composable
fun HomePage(
    nav: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    HomeContent(
        insertReminder = { homeViewModel.insertReminder(it) },
        launchListenStart = { homeViewModel.launchListenStart() },
        launchListenFinish = { homeViewModel.launchListenFinish() },
        saveNoteState = homeViewModel.listenLaunchState,
        onNavigateListScreen = { nav.navigate(Routes.LIST.route) })
}

@Composable
private fun HomeContent(
    insertReminder: (String) -> Unit,
    launchListenStart: () -> Unit,
    launchListenFinish: () -> Unit,
    onNavigateListScreen: () -> Unit,
    saveNoteState: MutableState<Boolean>
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        topBar = {
            HomeTopBar(
                label = stringResource(R.string.remind_me),
                onNavigateListScreen = onNavigateListScreen
            )
        }) { paddingValuesTop ->

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValuesTop)
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.5f)
                )
                {
                    AddNoteSpeech(
                        insertReminderIntoDatabase = { reminderText ->
                            insertReminder(reminderText)
                            showSnackBar(scope, snackbarHostState, reminderText.substring(0, Math.min(reminderText.length, 20)) + " added",)
                        },
                        speakingReminderStarted = launchListenStart,
                        speakingReminderFinished = launchListenFinish,
                        saveNoteState = saveNoteState
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                {
                    AddNoteTyping(
                        insertReminderIntoDatabase = { reminderText ->
                            insertReminder(reminderText)
                            showSnackBar(scope, snackbarHostState, reminderText.substring(0, Math.min(reminderText.length, 20)) + " added",)
                        },
                    )
                }
            }
        }
    }
}

fun showSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String
) {
    scope.launch {
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "Hide",
            duration = SnackbarDuration.Short
        )
        when(result){
            SnackbarResult.Dismissed -> { }
            SnackbarResult.ActionPerformed -> {}
        }
    }
}



