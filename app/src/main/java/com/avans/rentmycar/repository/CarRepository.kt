package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.CarApiService
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.BookingResponse
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.model.CarRequest
import com.avans.rentmycar.model.CarResponse
import retrofit2.Response

class CarRepository {
    suspend fun getCarsByUserId(userId:Int): Collection<CarList> {
        return CarApiService.getApi()?.getAllCarsByUserId(userId)?.body()?.data ?: emptyList()
    }

    suspend fun createCar(carRequest: CarRequest) : Response<CarResponse>? {
        Log.d("[RMC][CarRepository]", "createCar => CarApiService invoked")
        return CarApiService.getApi()?.createCar(carRequest)
    }

}