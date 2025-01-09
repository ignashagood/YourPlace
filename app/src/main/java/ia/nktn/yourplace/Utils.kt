package ia.nktn.yourplace

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    return formatter.format(date)
}

fun formatTime(date: Date): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
}

fun createDate(year: Int, month: Int, day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return calendar.time
}

fun createTime(hour: Int, minute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}
