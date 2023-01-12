package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.CarApiService
import com.avans.rentmycar.model.request.CarRequest
import com.avans.rentmycar.model.response.CarList
import com.avans.rentmycar.model.response.CarResponse
import com.avans.rentmycar.model.response.CreateCarResponse

class CarRepository {
    suspend fun getCarsByUserId(userId:Int): Collection<CarList> {
        return CarApiService.getApi()?.getAllCarsByUserId(userId)?.body()?.data ?: emptyList()
    }

    suspend fun createCar(carRequest: CarRequest) : CreateCarResponse? {
        Log.d("[RMC][CarRepository]", "createCar => CarApiService invoked")
        return CarApiService.getApi()?.createCar(carRequest)?.body()
    }

    suspend fun updateCar(Id: Int, carRequest: CarRequest) : CreateCarResponse? {
        Log.d("[RMC][CarRepository]", "createCar => CarApiService invoked")
        return CarApiService.getApi()?.updateCar(Id, carRequest)?.body()
    }

    suspend fun deleteCarById(Id: Int) : CarResponse? {
        Log.d("[RMC][CarRepository]", "deleteCarById => CarApiService invoked")
        return CarApiService.getApi()?.deleteCarById(Id)?.body()
    }

}