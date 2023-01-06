package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.RdwApiClient
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.model.RdwResponseItem
import com.avans.rentmycar.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val TAG = "[RMC][CarViewModel]"
    val carRepository = CarRepository()

    val carResponse: MutableLiveData<Collection<CarList>> = MutableLiveData()
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

}