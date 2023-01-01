package com.avans.rentmycar.ui.mycars

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.RdwApiClient
import com.avans.rentmycar.model.RdwResponseItem
import kotlinx.coroutines.launch

class CarAddItemViewModel : ViewModel() {
    private val TAG = "[RMC][CarAddItemVM]"

    // Wrap response with livedata
    private val _rdwResponse = MutableLiveData<List<RdwResponseItem>>()
    val rdwResponse: LiveData<List<RdwResponseItem>>
    get() = _rdwResponse

    fun getRdwCarDetails(licenseplate: String){
        Log.d(TAG, "getRdwCarDetails() => licenseplate " + licenseplate)
        viewModelScope.launch {
            var responseRdw = RdwApiClient.retrofitService.getCarInfoByLicensePlate(licenseplate).toString()
            Log.d(TAG, "getRdwCarDetails() => (1) response livedata rdwResponse: " + rdwResponse)
            Log.d(TAG, "getRdwCarDetails() => (2) response livedata _rdwResponse.value: " + _rdwResponse.value)
            Log.d(TAG, "getRdwCarDetails() => (3) reponse livedata : " + this@CarAddItemViewModel.rdwResponse.toString())
            Log.d(TAG, "getRdwCarDetails() => reponseRdw: " + responseRdw)

        }
        Log.d(TAG, "getRdwCarDetails() => Data retrieved for car "+licenseplate)
    }

}