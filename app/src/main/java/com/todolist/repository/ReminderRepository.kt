package com.todolist.repository

import com.todolist.database.Reminder
import com.todolist.database.ReminderDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ReminderRepository @Inject constructor(private val reminderDatabase: ReminderDatabase) {

    private val dao = reminderDatabase.reminderDao()

    fun getReminders(): Flow<List<Reminder>> {
        return dao.getReminders()
    }

   fun getReminderFlow(id : Int): Flow<Reminder> {
        return dao.getReminderFlow(id)
    }

    suspend fun getReminder(id : Int): Reminder {
        return dao.getReminder(id)
    }

    suspend fun insertReminder(reminder: Reminder) {
        return dao.insertReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        return dao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        return dao.deleteReminder(reminder)
    }
}