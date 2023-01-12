package com.avans.rentmycar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "RideDatabase")
data class Ride(
    @PrimaryKey
    @ColumnInfo(name = "rideId") val rideId: Long?,
    @ColumnInfo(name = "start_timestamp") val startTimeStamp: Instant?,
    @ColumnInfo(name = "end_timestamp") var endTimeStamp: Instant?,
    @ColumnInfo(name = "start_latitude") val startLatitude: Double?,
    @ColumnInfo(name = "start_longitude") val startLongitude: Double?,
    @ColumnInfo(name = "end_latitude") var endLatitude: Double?,
    @ColumnInfo(name = "end_longitude") var endLongitude: Double?,
)