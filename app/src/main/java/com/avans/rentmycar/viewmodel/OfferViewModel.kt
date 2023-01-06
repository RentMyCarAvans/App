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
                Log.v("[RMC][OfferViewModel]", "getOffers() is called")

                val offerResponse = offerRepository.getOpenOffers()
                offerResult.value = offerResponse
                Log.d("[OfferVM] getOffers", offerResponse.toString())

            } catch (e: Exception) {
                Log.e("[OfferVM] error", e.message.toString())
            }
        }
    }

    fun getBookings(userId: Long) {
        viewModelScope.launch {
            Log.d("[OfferVM] getBookings", "function called")
            try {
                val getBookingResponse = offerRepository.getBookings(userId)
                bookingsResult.value = getBookingResponse
                Log.d("[OfferVM] gBookingsResp", getBookingResponse.toString())

            } catch (e: Exception) {
                Log.e("[OfferVM] getB error", e.message.toString())
            }
        }
    }

    fun createBooking(offerId: Long, customerId: Long) {
        viewModelScope.launch {
            Log.d("[OfferVM] createBooking", "offerId: $offerId, customerId: $customerId")
            try {
                val bookingResponse = offerRepository.createBooking(offerId, customerId)
                bookingResult.value = bookingResponse
                Log.d("[OfferVM] bookingresp", bookingResponse.toString())
                Log.d("[OfferVM] bookingresult", bookingResult.value.toString())

            } catch (e: Exception) {
                Log.d("[OfferVM] bookingresult", bookingResult.value.toString())
                Log.e("[OfferVM] bookingerror", e.message.toString())
            }
        }
    }

    fun getGeocodeResponse(pickupLocation: String) {
        viewModelScope.launch {
            Log.d("[OfferVM] getGeocResp", "function called with PuL: $pickupLocation")
            try {
                val geocodeResponse = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)
                Log.d("[OfferVM]suc getGeoResp", geocodeResponse.toString())
                geocodeResult?.value = geocodeResponse
            } catch (e: Exception) {
                Log.e("[OfferVM]err getGeoResp", e.message.toString())
            }
        }

    }

}