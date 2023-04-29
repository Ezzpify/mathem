package com.example.mathemtest.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class FriendlyDateFormat(
    val dayOfWeek: String,
    val dayOfMonth: String,
    val month: String
)

// This is shit but I'm not spending time on this
// So... TODO: Make better
// date format = 2023-04-28
fun getFriendlyDateFormat(inputDate: String): FriendlyDateFormat {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(inputDate, formatter)

    val today = LocalDate.now()
    val tomorrow = today.plusDays(1)

    val dayOfWeek = when (date) {
        today -> "Today"
        tomorrow -> "Tomorrow"
        else -> date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    val dayOfMonth = date.dayOfMonth.toString()
    val month = date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase()

    return FriendlyDateFormat(dayOfWeek, dayOfMonth, month)
}





