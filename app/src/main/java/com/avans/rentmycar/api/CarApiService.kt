package com.avans.rentmycar.api

import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.model.CarResponse
import com.avans.rentmycar.rest.ApiClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Returns a [List] of [CarList] and this method can be called from a Coroutine.
 */
interface CarApiService {

    @GET("/api/v1/cars{userid}")
    suspend fun getAllCarsById(@Query("carId") userId:Int) : Response<CarResponse>

    @GET("/api/v1/cars")
    suspend fun getAllCarsByUserId(@Query("userId") userId:Int) : Response<CarResponse>

    companion object {
        fun getApi(): CarApiService? {
            return ApiClient.client?.create(CarApiService::class.java)
        }

    }

}
