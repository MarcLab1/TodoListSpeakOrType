package com.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val reminder: String = "Hello my darling",
    val date: Long = 0L,
    val completed : Boolean = false,
    val emailReminder : Boolean = true,

)
{
    override fun toString(): String {
        return "${this.id} ${this.reminder}"
    }
}
