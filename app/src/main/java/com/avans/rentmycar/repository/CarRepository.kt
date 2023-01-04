package com.avans.rentmycar.repository

import com.avans.rentmycar.api.CarApiService
import com.avans.rentmycar.model.CarList

class CarRepository {
    suspend fun getCarsByUserId(userId:Int): Collection<CarList> {
        return CarApiService.getApi()?.getAllCarsByUserId(userId)?.body()?.data ?: emptyList()
    }
}