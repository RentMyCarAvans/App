package com.avans.rentmycar.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.RdwApiClient
import com.avans.rentmycar.model.*
import com.avans.rentmycar.repository.CarRepository
import com.avans.rentmycar.rest.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val TAG = "[RMC][CarViewModel]"
    val carRepository = CarRepository()

    val carResponse: MutableLiveData<Collection<CarList>> = MutableLiveData()
    val carCreateResponse: MutableLiveData<BaseResponse<CarResponse>> = MutableLiveData()
    val rdwResponse: MutableLiveData<List<RdwResponseItem>> = MutableLiveData()

    fun getRdwCarDetails(licenseplate: String){
        Log.d(TAG, "getRdwCarDetails() => Trying to retrieve car details of RDW for car with licenseplate " + licenseplate)
        viewModelScope.launch {
            rdwResponse.value = RdwApiClient.retrofitService.getCarInfoByLicensePlate(licenseplate)
            Log.d(TAG,"Reponse RDW: "+ rdwResponse.value)
        }
        Log.d(TAG, "getRdwCarDetails() => RDW data retrieved for car with licenseplate " + licenseplate)
    }

    fun getMyCars(userId : Int){
        Log.d(TAG, "getMyCars() => Trying to retrieve all cars for userId " +userId)

        // Execution on Default thread (=background thread). Dit mag niet voor api
        viewModelScope.launch(Dispatchers.Main) {
            try{
                carResponse.value = carRepository.getCarsByUserId(userId)
                Log.d(TAG, "getCarDetails => retrieved value carResponse: " + carResponse.value)
            } catch (e: Exception){
                Log.d(TAG, "getCarDetails => exception occurred: " + e.toString())
            }
        }
    }

    fun createCar(colorType: String?, image: String, licensePlate: String, mileage: Int, model: String, numberOfSeats: Int, type: String, userId: Int, vehicleType: String, yearOfManufacture: Int) {
        carCreateResponse.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                Log.d(TAG, "createCar() => colorType = " + colorType)
                Log.d(TAG, "createCar() => image = " + image)
                Log.d(TAG, "createCar() => licensePlate = " + licensePlate)
                Log.d(TAG, "createCar() => mileage = " + mileage)
                Log.d(TAG, "createCar() => model = " + model)
                Log.d(TAG, "createCar() => numberOfSeats = " + numberOfSeats)
                Log.d(TAG, "createCar() => type = " + type)
                Log.d(TAG, "createCar() => userId = " + userId)
                Log.d(TAG, "createCar() => vehicleType = " + vehicleType)
                Log.d(TAG, "createCar() => yearOfManufacture = " + yearOfManufacture)
                val carRequest = CarRequest(colorType = colorType,
                    image = image,
                    licensePlate = licensePlate,
                    mileage = mileage,
                    model = model,
                    numberOfSeats = numberOfSeats,
                    type = type,
                    userId = userId,
                    vehicleType = vehicleType,
                    yearOfManufacture = yearOfManufacture)
                Log.d(TAG, "createCar() => carRequest = " + carRequest)
                Log.d(TAG, "createCar() => carRequest = " + carRequest.toString())
                val response = carRepository.createCar(carRequest = carRequest)
                Log.d("[RMC][CarViewModel]", "createCar() => carRepository called with reponse: " + response.toString())
                if (response?.code() == 201) {
                    Log.d("[RMC][CarViewModel]", "createCar() => Responsecode 201")

                    carCreateResponse.value = BaseResponse.Success(response.body())
                } else {
                    Log.d("[RMC][CarViewModel]", "createCar() => Error")

                    carCreateResponse.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                Log.d("[RMC][CarViewModel]", "createCar() => Exception")
                carCreateResponse.value = BaseResponse.Error(ex.message)
            }
        }    }

}