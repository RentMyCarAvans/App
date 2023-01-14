package com.avans.rentmycar.repository

import com.avans.rentmycar.api.RideService
import com.avans.rentmycar.model.response.RideResponse
import com.avans.rentmycar.room.Ride

class RideRepository {
    suspend fun updateRide(updatedUser: Ride, rideId: Long): retrofit2.Response<RideResponse>? {
        return RideService.getApi()?.putRide(updatedUser, url ="/api/v1/ride/${rideId}")
    }
}