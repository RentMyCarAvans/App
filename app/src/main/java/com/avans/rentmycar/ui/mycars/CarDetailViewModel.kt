package com.avans.rentmycar.ui.mycars

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.CarApiClient
import com.avans.rentmycar.api.RdwApiClient
import com.avans.rentmycar.model.CarResponse
import com.avans.rentmycar.model.RdwResponseItem
import kotlinx.coroutines.launch

class CarDetailViewModel : ViewModel() {
    private val TAG = "[RMC][CarDetailVM]"

    private val _carResponse = MutableLiveData<List<CarResponse>>()
    val carResponse: LiveData<List<CarResponse>>
    get() = _carResponse

    init {
        Log.d(TAG, "init()")
        getMyCars()
    }

    fun getMyCars(){
        Log.d(TAG, "getMyCars()")
        viewModelScope.launch {
            var carDetails = CarApiClient.retrofitService.getAllCarsByUserId(1).toString()
            Log.d(TAG, "getCarDetails => retrieved value: " + carDetails)
        }
        Log.d(TAG, "getMyCars() DONE")
    }

}