package com.avans.rentmycar.api

import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.model.CarResponse
import com.avans.rentmycar.rest.ApiClient
import com.avans.rentmycar.rest.response.LoginResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Returns a [List] of [CarList] and this method can be called from a Coroutine.
 */
interface CarApiService {

    @GET("/api/v1/cars{id}")
    suspend fun getAllCarsById(@Path("id") id:Int) : Response<CarResponse>

    // TODO 
    @GET("/api/v1/cars")
    suspend fun getAllCarsByUserId(@Query("id") id:Int) : Response<CarResponse>

    @DELETE("/api/v1/cars/{id}")
    suspend fun deleteCarById(@Path("id") todoId: Response<LoginResponse>)

    companion object {
        fun getApi(): CarApiService? {
            return ApiClient.client?.create(CarApiService::class.java)
        }

    }

}
