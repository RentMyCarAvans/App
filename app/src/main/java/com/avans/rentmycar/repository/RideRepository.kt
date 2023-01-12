package com.avans.rentmycar.repository

import com.avans.rentmycar.rest.methods.RideService
import com.avans.rentmycar.rest.request.UpdateRideRequest
import com.avans.rentmycar.rest.response.RideResponse
import okhttp3.Response

class RideRepository {
    suspend fun updateRide(updatedUser: UpdateRideRequest, rideId: String): retrofit2.Response<RideResponse>? {
        return RideService.getApi()?.putRide(updatedUser, url="/api/v1/ride/${rideId}")
    }
}