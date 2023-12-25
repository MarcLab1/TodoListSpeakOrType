package com.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Reminder::class], version = 3)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao
}