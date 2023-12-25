package com.todolist.ui.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolist.database.Reminder
import com.todolist.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ListViewModel @Inject constructor(private val reminderRepository: ReminderRepository) :
    ViewModel() {

    val state: StateFlow<ListUIState> = reminderRepository.getReminders().map { reminders ->
        ListUIState.Success(reminders = reminders.sortedByDescending { it.date })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = ListUIState.Loading,
    )

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder = reminder)
        }
    }

    fun insertReminder(reminder: Reminder){
        viewModelScope.launch {
            reminderRepository.insertReminder(reminder = reminder)
        }
    }
}

sealed class ListUIState {
    object Loading : ListUIState()
    data class Success(val reminders: List<Reminder>) : ListUIState()
}