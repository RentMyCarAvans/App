package com.avans.rentmycar.api

import com.avans.rentmycar.BuildConfig.MAPS_API_KEY
import com.avans.rentmycar.model.GeocodeResponse
import com.avans.rentmycar.rest.ApiClient
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface MapsApiService {

    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    suspend fun getLatLongFromAddress(
        @Query("address") pickupLocation: String,
        @Query("key") key: String = MAPS_API_KEY
    ): GeocodeResponse

    companion object {
        fun getApi(): MapsApiService? {
            return ApiClient.client?.create(MapsApiService::class.java)
        }
    }

}

class GeolocationDTO(location: String) {
    var address: String = ""
    var key: String = MAPS_API_KEY
}

