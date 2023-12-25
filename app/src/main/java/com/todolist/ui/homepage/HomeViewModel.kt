package com.todolist.ui.homepage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolist.database.Reminder
import com.todolist.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val reminderRepository: ReminderRepository) :
    ViewModel() {

    val listenLaunchState = mutableStateOf(false)

    fun insertReminder(reminder: String) {
        viewModelScope.launch {
            reminderRepository.insertReminder(
                Reminder(
                    reminder = reminder,
                    date = System.currentTimeMillis()
                )
            )
        }
    }

    fun launchListenStart(): Unit {
        listenLaunchState.value = true
    }

    fun launchListenFinish(): Unit {
        listenLaunchState.value = false
    }
}
