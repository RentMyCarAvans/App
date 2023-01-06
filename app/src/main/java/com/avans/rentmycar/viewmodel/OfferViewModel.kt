package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.BookingResponse
import com.avans.rentmycar.model.GeocodeResponse
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.repository.OfferRepository
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    private val offerRepository = OfferRepository()

    val offerResult: MutableLiveData<Collection<OfferData>> = MutableLiveData()

    val bookingResult: MutableLiveData<BookingResponse?> = MutableLiveData()
    val bookingsResult: MutableLiveData<Collection<BookingData>> = MutableLiveData()

    var geocodeResult: MutableLiveData<GeocodeResponse?>? = MutableLiveData()

    fun getOffers() {
        viewModelScope.launch {
            try {
                val offerResponse = offerRepository.getOpenOffers()
                offerResult.value = offerResponse
            } catch (e: Exception) {
                Log.e("[OfferVM] getOffers", e.message.toString())
            }
        }
    }

    fun getBookings(userId: Long) {
        viewModelScope.launch {
            try {
                val getBookingResponse = offerRepository.getBookings(userId)
                bookingsResult.value = getBookingResponse
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
            } catch (e: Exception) {
                Log.e("[OfferVM] createBooking", e.message.toString())
            }
        }
    }

    fun getGeocodeResponse(pickupLocation: String) {
        viewModelScope.launch {
            try {
                val geocodeResponse = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)
                geocodeResult?.value = geocodeResponse
            } catch (e: Exception) {
                Log.e("[OfferVM] getGeoRespon", e.message.toString())
            }
        }

    }

}