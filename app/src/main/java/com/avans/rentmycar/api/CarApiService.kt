package com.avans.rentmycar.api

import com.avans.rentmycar.model.response.CarList
import com.avans.rentmycar.model.request.CarRequest
import com.avans.rentmycar.model.response.CarResponse
import com.avans.rentmycar.model.response.CreateCarResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Returns a [List] of [CarList] and this method can be called from a Coroutine.
 */
interface CarApiService {

    @GET("/api/v1/cars/{id}")
    suspend fun getAllCarsById(@Path("id") id:Int) : Response<CarResponse>

    // Retrieve all cars, and if the (optional) userid is passed, then retrieve all cars by userid
    @GET("/api/v1/cars")
    suspend fun getAllCarsByUserId(@Query("userId") userId:Int) : Response<CarResponse>

    @DELETE("/api/v1/cars/{id}")
    suspend fun deleteCarById(@Path("id") Id:Int): Response<CarResponse>

    @POST("/api/v1/cars")
    suspend fun createCar(@Body carRequest: CarRequest): Response<CreateCarResponse>

    @PUT("/api/v1/cars/{id}")
    suspend fun updateCar(@Path("id") id:Int, @Body carRequest: CarRequest): Response<CreateCarResponse>

    companion object {
        fun getApi(): CarApiService? {
            return ApiClient.client?.create(CarApiService::class.java)
        }

    }

}
