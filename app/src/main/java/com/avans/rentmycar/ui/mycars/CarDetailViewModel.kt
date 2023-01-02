package com.avans.rentmycar.ui.mycars

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.CarApiClient
import androidx.fragment.app.viewModels
import com.avans.rentmycar.model.CarResponse
import com.avans.rentmycar.ui.viewmodel.UserViewModel
import com.avans.rentmycar.utils.SessionManager
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

        // Retrieve userid of the logged in user, so we can fetch his cars
        // TODO Retreieve userid by viewModel / observer. For now, let's hardcode the userid with 1


        viewModelScope.launch {
            var carDetails = CarApiClient.retrofitService.getAllCarsByUserId(1).toString()
            Log.d(TAG, "getCarDetails => retrieved value: " + carDetails)
        }
        Log.d(TAG, "getMyCars() DONE")
    }

}