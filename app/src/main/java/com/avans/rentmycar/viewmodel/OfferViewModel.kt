package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.BookingResponse
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.repository.OfferRepository
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

//    private val _offerResponse = MutableLiveData<String?>()

    val offerRepository = OfferRepository()
    val offerResult: MutableLiveData<Collection<OfferData>> = MutableLiveData()

    val bookingResult: MutableLiveData<BookingResponse?> = MutableLiveData()
    val bookingsResult: MutableLiveData<Collection<BookingData>> = MutableLiveData()

    private val _offers = MutableLiveData<OfferData>()
//    val offers: LiveData<OfferData> = _offers

    fun getOffers() {
        viewModelScope.launch {
            try {
                val offerResponse = offerRepository.getOpenOffers()
                offerResult.value = offerResponse
                Log.d("[OfferVM] response", offerResponse.toString())

            } catch (e: Exception) {
                Log.e("[OfferVM] error", e.message.toString())
            }
        }
    }

    fun getBookings(userId: Long) {
        viewModelScope.launch {
            try {
                val getBookingResponse = offerRepository.getBookings(userId)
                bookingsResult.value = getBookingResponse
                Log.d("[OfferVM] getBooking", getBookingResponse.toString())

            } catch (e: Exception) {
                Log.e("[OfferVM] getB error", e.message.toString())
            }
        }
    }

    fun createBooking(offerId: Long, customerId: Long) {
        viewModelScope.launch {
            try {
                val bookingResponse = offerRepository.createBooking(offerId, customerId)
                bookingResult.value = bookingResponse
                Log.d("[OfferVM] bookingresp", bookingResponse.toString())
                Log.d("[OfferVM] bookingresult", bookingResult.value.toString())

            } catch (e: Exception) {
                Log.e("[OfferVM] bookingerror", e.message.toString())
            }
        }
    }

}