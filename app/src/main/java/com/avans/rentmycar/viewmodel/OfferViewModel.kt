package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.model.OfferResponse
import com.avans.rentmycar.model.OfferUiModel
import com.avans.rentmycar.repository.OfferRepository
import com.avans.rentmycar.repository.UserRepository
import com.avans.rentmycar.rest.request.CreateUpdateUserRequest
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.rest.response.UserResponse
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    private val _offerResponse = MutableLiveData<String?>()

    val offerRepository = OfferRepository()
    val offerResult: MutableLiveData<Collection<OfferData>> = MutableLiveData()

    private val _offers = MutableLiveData<OfferData>()
    val offers: LiveData<OfferData> = _offers

    fun getOffers() {


//        offerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                Log.v("[OfferViewModel]", "getOffers() is called")

//                val response = offerRepository.getMockOffers()
                val offerResponse = offerRepository.getOpenOffers()
//                Log.d("[offerResponse]", offerResponse.toString())
                offerResult.value = offerResponse
                Log.d("[OfferVM] response", offerResponse.toString())

            } catch (e: Exception) {
                Log.e("[OfferVM] error", e.message.toString())
            }
        }
    }

}