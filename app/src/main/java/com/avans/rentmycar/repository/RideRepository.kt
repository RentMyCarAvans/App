package com.avans.rentmycar.repository

import com.avans.rentmycar.api.RideService
import com.avans.rentmycar.model.request.UpdateRideRequest
import com.avans.rentmycar.model.response.RideResponse

class RideRepository {
    suspend fun updateRide(updatedUser: UpdateRideRequest, rideId: String): retrofit2.Response<RideResponse>? {
        return RideService.getApi()?.putRide(updatedUser, url="/api/v1/ride/${rideId}")
    }
}