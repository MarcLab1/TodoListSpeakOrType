
package com.todolist.di

import android.app.Application
import androidx.room.Room
import com.todolist.database.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun ProvideReminderDatabase(application: Application): ReminderDatabase {
        return Room.databaseBuilder(application, ReminderDatabase::class.java, "reminder_db")
            .fallbackToDestructiveMigration().build()
    }

}
