package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val TAG = "[RMC][CarDetailVM]"
    val carRepository = CarRepository()

    val carResponse: MutableLiveData<Collection<CarList>> = MutableLiveData()

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