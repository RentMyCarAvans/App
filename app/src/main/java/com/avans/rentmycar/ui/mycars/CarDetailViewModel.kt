package com.avans.rentmycar.ui.mycars

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.RdwApiClient
import com.avans.rentmycar.model.RdwResponseItem
import kotlinx.coroutines.launch

private val TAG = "[RMC][MyCarsViewModel]"
class CarDetailViewModel : ViewModel() {

    private val _rdwResponse = MutableLiveData<List<RdwResponseItem>>()
    val rdwResponse: LiveData<List<RdwResponseItem>>
    get() = _rdwResponse

    init {
        Log.d(TAG, "init()")
        getRdwCarDetails()
    }

    fun getRdwCarDetails(){
        Log.d(TAG, "getRdwCarDetails()")
        viewModelScope.launch {
            var getCarInfoByLicensePlateDeferred = RdwApiClient.retrofitService.getCarInfoByLicensePlate("69SXKZ").toString()
            Log.d(TAG, "getRdwCarDetails => retrieved value: " + getCarInfoByLicensePlateDeferred)
        }
        Log.d(TAG, "getRdwCarDetails DONE")
    }

}