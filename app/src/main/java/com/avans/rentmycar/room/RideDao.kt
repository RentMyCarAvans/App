package com.avans.rentmycar.room

import androidx.room.*
import com.avans.rentmycar.Converters
import kotlinx.coroutines.flow.Flow
import java.sql.Date


@Dao
@TypeConverters(Converters::class)
interface RideDao {
    @Query("SELECT * FROM RideDatabase")
    fun getAll(): List<Ride>

    @Query("SELECT * FROM RideDatabase WHERE rideId IN (:rideId) LIMIT 1")
    fun getRide(rideId: Long): Flow<Ride>

    @Update
    suspend fun update(ride: Ride)


}