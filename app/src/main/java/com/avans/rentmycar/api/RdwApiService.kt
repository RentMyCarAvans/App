package com.avans.rentmycar.api

import com.avans.rentmycar.model.response.RdwResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Returns a [List] of [RdwResponseItem] and this method can be called from a Coroutine.
 */
private val TAG = "[RMC][RdwApiService]"
interface RdwApiService {

    @GET("m9d7-ebf2.json")
    suspend fun getCarInfoByLicensePlate(@Query("kenteken") licensePlate:String) : List<RdwResponseItem>

}
