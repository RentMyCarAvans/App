package com.avans.rentmycar.utils

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

object DateTimeConverter {
        fun convertDatabaseDateTimeToReadableDateTime(dateTime: String): String {
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val localDateTime = LocalDateTime.parse(dateTime, inputFormat)
            return localDateTime.format(outputFormat)
        }

        fun formatCalendarToString(caledar: Calendar) : String{
            val year = caledar.get(Calendar.YEAR)
            val month = caledar.get(Calendar.MONTH) + 1
            val day = caledar.get(Calendar.DAY_OF_MONTH)
            val hour = caledar.get(Calendar.HOUR_OF_DAY)
            val minute = caledar.get(Calendar.MINUTE)
            return "$day-$month-$year $hour:$minute"
        }

    fun formatCalendarToDBString(calendar: Calendar) : String{
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:00"
    }

    fun formatDBStringToCalendar(dateString: String?): Calendar {
        val calendar = Calendar.getInstance()
        val parts = dateString?.split("T")
        val date = parts?.get(0)?.split("-")
        val time = parts?.get(1)?.split(":")
        calendar.set(Calendar.YEAR, date?.get(0)?.toInt() ?: 0)
        calendar.set(Calendar.MONTH, date?.get(1)?.toInt()?.minus(1) ?: 0)
        calendar.set(Calendar.DAY_OF_MONTH, date?.get(2)?.toInt() ?: 0)
        calendar.set(Calendar.HOUR_OF_DAY, time?.get(0)?.toInt() ?: 0)
        calendar.set(Calendar.MINUTE, time?.get(1)?.toInt() ?: 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar

    }

}