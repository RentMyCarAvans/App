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
class CarAddItemViewModel : ViewModel() {

    private val _rdwResponse = MutableLiveData<List<RdwResponseItem>>()
    val rdwResponse: LiveData<List<RdwResponseItem>>
    get() = _rdwResponse

    fun getRdwCarDetails(licenseplate: String){
        Log.d(TAG, "getRdwCarDetails() => licenseplate " + licenseplate)
        viewModelScope.launch {
            var getCarInfoByLicensePlateDeferred = RdwApiClient.retrofitService.getCarInfoByLicensePlate(licenseplate).toString()
            Log.d(TAG, "getRdwCarDetails() => response: " + getCarInfoByLicensePlateDeferred)
        }
        Log.d(TAG, "getRdwCarDetails() => Data retrieved for car "+licenseplate)
    }

}