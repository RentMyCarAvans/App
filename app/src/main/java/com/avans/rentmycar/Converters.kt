package com.avans.rentmycar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant

class Converters {
    // more info: https://developer.android.com/training/data-storage/room/referencing-data
    // this is to convert Instant format to some type that Room db understands.
    // cant be in folder utils for some reason

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromInstant(value: Instant): Long {
        return value.toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toInstant(value: Long): Instant {
        return Instant.ofEpochMilli(value)
    }

}


