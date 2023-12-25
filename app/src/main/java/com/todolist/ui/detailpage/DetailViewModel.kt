package com.todolist.ui.detailpage

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val reminderRepository: ReminderRepository,
) : ViewModel() {

    val state: StateFlow<DetailUIState> =
        savedStateHandle.get<String>("id").let { reminderRepository.getReminderFlow(it!!.toInt()) }
            .map { reminder ->
                DetailUIState.Success(reminder = reminder)
            }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = DetailUIState.Loading,
        )

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.updateReminder(reminder = reminder)
        }
    }
}

sealed class DetailUIState {
    object Loading : DetailUIState()
    data class Success(val reminder: Reminder) : DetailUIState()
}