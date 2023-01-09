package com.avans.rentmycar.api

import com.avans.rentmycar.BuildConfig.MAPS_API_KEY
import com.avans.rentmycar.BuildConfig.POSITIONSTACK_API_KEY
import com.avans.rentmycar.model.GeocodeResponseGoogle
import com.avans.rentmycar.model.GeocodeResponsePositionstack
import com.avans.rentmycar.rest.ApiClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Query


interface MapsApiService {



    @GET("http://api.positionstack.com/v1/forward")
    suspend fun getLatLongFromAddress(
        @Query("query") pickupLocation: String,
        @Query("access_key") key: String = POSITIONSTACK_API_KEY
    ): GeocodeResponsePositionstack


    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    suspend fun getLatLongFromAddressGoogle(
        @Query("address") pickupLocation: String,
        @Query("key") key: String = MAPS_API_KEY
    ): GeocodeResponseGoogle

    companion object {
        fun getApi(): MapsApiService? {
            return ApiClient.client?.create(MapsApiService::class.java)
        }

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    }

}

class GeolocationDTO(location: String) {
    var address: String = ""
    var key: String = MAPS_API_KEY
}

