package com.todolist.ui.detailpage

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.todolist.helpers.HelperDate
import com.todolist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(label: String, onNavigateBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = label, style = MaterialTheme.typography.labelLarge) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 8.dp, end = 15.dp)
                    .clickable { onNavigateBack() }
            )
        },
    )
}

@Composable
fun DetailPage(
    nav: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {

    val state = detailViewModel.state.collectAsStateWithLifecycle()

    DetailPageContent(
        nav = nav,
        detailViewModel = detailViewModel,
        state = state.value,
    )
}

@Composable
fun DetailPageContent(
    nav: NavController,
    detailViewModel: DetailViewModel,
    state: DetailUIState,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            androidx.compose.material.SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            DetailTopBar(
                label = stringResource(R.string.reminder),
                onNavigateBack = { nav.navigateUp() },
            )
        }) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize())
        {
            when (state) {
                is DetailUIState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues)
                            .padding(top = 3.dp)
                            .padding(horizontal = 5.dp)
                    ) {
                        DateLabel(
                            label = stringResource(R.string.date),
                            date = HelperDate.getFormattedDateEEEMMMdyyyhmma(state.reminder.date)
                        )
                        UpdateEmailReminderLabel(
                            label = stringResource(id = R.string.email_reminder),
                            rightComposable = {
                                Switch(
                                    checked = state.reminder.emailReminder,
                                    onCheckedChange = null
                                )
                            },
                            modifier = Modifier.clickable {
                                val emaiReminder = state.reminder.emailReminder
                                detailViewModel.updateReminder(
                                    state.reminder.copy(
                                        emailReminder = emaiReminder.not()
                                    )
                                )
                            })

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            value = state.reminder.reminder,
                            onValueChange = {
                                detailViewModel.updateReminder(
                                    state.reminder.copy(
                                        reminder = it
                                    )
                                )
                            },
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }

                is DetailUIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
