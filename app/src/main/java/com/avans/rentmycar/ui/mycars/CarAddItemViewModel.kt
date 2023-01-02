package com.avans.rentmycar.ui.mycars

import android.util.Log
import androidx.lifecycle.*
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
        Log.d(TAG, "getRdwCarDetails() => Get car details for car with licenseplate " + licenseplate)
        viewModelScope.launch {
            var responseRdw = RdwApiClient.retrofitService.getCarInfoByLicensePlate(licenseplate).toString()
            Log.d(TAG, "getRdwCarDetails() => reponse RDW: " + responseRdw)
        }
        Log.d(TAG, "getRdwCarDetails() => RDW data retrieved for car with licenseplate " + licenseplate)
    }

}