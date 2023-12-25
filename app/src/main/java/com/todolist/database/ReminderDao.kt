package com.todolist.database

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder")
    fun getReminders() : Flow<List<Reminder>>

    @Query("SELECT * FROM reminder WHERE id = :id")
    suspend fun getReminder(id : Int) :  Reminder

    @Query("SELECT * FROM reminder WHERE id = :id")
    fun getReminderFlow(id : Int) :  Flow<Reminder>

    @Insert(onConflict = REPLACE)
    suspend fun insertReminder(reminder : Reminder)

    @Update
    suspend fun updateReminder(reminder : Reminder)

    @Delete
    suspend fun deleteReminder(reminder : Reminder)

}