package com.avans.rentmycar.api

import com.avans.rentmycar.model.CarList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Returns a [List] of [CarList] and this method can be called from a Coroutine.
 */
interface CarApiService {

    @GET("cars")
    suspend fun getAllCarsByUserId(@Query("userId") userId:Int) : List<CarList>

}
