package com.todolist.helpers

import java.text.SimpleDateFormat

object HelperDate {

    fun getFormattedDateEEEMMMdyyyhmma(date: Long): String {
        val sdf = SimpleDateFormat("EEE MMM d, yyyy h:mm a")
        return sdf.format(date)
    }

    fun getFormattedDateEEEMMMdyyyy(date: Long): String {
        val sdf = SimpleDateFormat("EEE MMM d, yyyy")
        return sdf.format(date)
    }

}
