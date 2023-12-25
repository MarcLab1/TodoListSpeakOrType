package com.todolist.ui.listpage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.december2023.voice.ui.Routes
import com.todolist.R
import com.todolist.database.Reminder
import com.todolist.ui.homepage.ReminderItem

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTopBar(label: String, onNavigateHome: () -> Unit) {

    TopAppBar(
        title = { Text(text = label, style = MaterialTheme.typography.labelLarge) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        actions = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(R.string.home),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 5.dp, end = 15.dp)
                    .clickable { onNavigateHome() }
            )
        }
    )
}

@Composable
fun ListPage(
    nav: NavController,
    listViewModel: ListViewModel = hiltViewModel(),
) {

    val state = listViewModel.state.collectAsStateWithLifecycle()

    ListContent(
        state = state.value,
        vm = listViewModel,
        nav = nav,
    )
}

@Composable
private fun ListContent(
    state: ListUIState,
    vm: ListViewModel,
    nav: NavController,

) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            androidx.compose.material.SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            ListTopBar(
                label = stringResource(R.string.reminders),
                onNavigateHome = { nav.navigate(Routes.HOME.route) }
            )
        }) { paddingValuesTop ->

        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is ListUIState.Success -> {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValuesTop),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {

                        LazyColumn() {
                            itemsIndexed(
                                items = state.reminders,
                                key = { _, reminder -> reminder.date }) { index, reminder ->

                                ReminderItem(index = index, reminder = reminder,
                                    deleteReminder = {
                                        vm.deleteReminder(reminder)
                                        showSnackBarDelete(
                                            scope = scope,
                                            snackbarHostState = snackbarHostState,
                                            message = context.getString(R.string.reminder_deleted),
                                            reminder = reminder,
                                            insertReminder = { vm.insertReminder(reminder) }
                                        )
                                    },
                                    navigateToDetailScreen = { nav.navigate("${Routes.DETAIL.route}/${it.id}") })
                            }
                        }
                    }
                }

                is ListUIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {}
            }
        }
    }
}

fun showSnackBarDelete(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    reminder: Reminder,
    insertReminder: (Reminder) -> Unit
) {
    scope.launch {
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "Undo",
            duration = SnackbarDuration.Short
        )
        when (result) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> {
                insertReminder(reminder)
            }
        }
    }
}