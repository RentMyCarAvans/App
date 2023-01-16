package com.avans.rentmycar.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConverter {
        fun convertDatabaseDateTimeToReadableDateTime(dateTime: String): String {
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val localDateTime = LocalDateTime.parse(dateTime, inputFormat)
            return localDateTime.format(outputFormat)
        }

        fun convertReadableDateTimeToDatabaseDateTime(dateTime: String): String {
            val inputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val localDateTime = LocalDateTime.parse(dateTime, inputFormat)
            return localDateTime.format(outputFormat)
        }
}