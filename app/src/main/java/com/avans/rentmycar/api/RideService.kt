package com.avans.rentmycar.api

import com.avans.rentmycar.model.request.UpdateRideRequest
import com.avans.rentmycar.model.response.RideResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface RideService {

    @PUT()
    suspend fun putRide(@Body updateRideRequest: UpdateRideRequest, @Url url: String): Response<RideResponse>

    companion object {
        fun getApi(): RideService? {
            return ApiClient.client?.create(RideService::class.java)
        }
    }
}
