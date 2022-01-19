package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

fun getToday() = formatDate(Calendar.getInstance().time)

fun getSeventhDayFromToday(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return formatDate(calendar.time)
}

private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(date)
}