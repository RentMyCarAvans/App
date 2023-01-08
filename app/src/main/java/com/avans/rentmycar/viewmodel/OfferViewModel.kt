package com.avans.rentmycar.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.CreateBookingResponse
import com.avans.rentmycar.model.GeocodeResponse
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.repository.OfferRepository
import com.avans.rentmycar.utils.SessionManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    private val offerRepository = OfferRepository()

    // ===== Results of the API calls =====
    // TODO: Refactor this
    val bookingsResult: MutableLiveData<Collection<BookingData>> = MutableLiveData()
    val createBookingResult: MutableLiveData<CreateBookingResponse?> = MutableLiveData()
    var geocodeResult: MutableLiveData<GeocodeResponse?>? = MutableLiveData()


    // ===== Variables for the API calls =====
    val offerCollection = MutableLiveData<Collection<OfferData>>()


    // ===== Filter options =====
    // Checkbox Filters
    val checkboxFuelTypeIceFilter: MutableLiveData<Boolean> = MutableLiveData(true)
    val checkboxFuelTypeBevFilter: MutableLiveData<Boolean> = MutableLiveData(true)
    val checkboxFuelTypeFcevFilter: MutableLiveData<Boolean> = MutableLiveData(true)

    // Slider Filters
    val numberOfSeatsFilter: MutableLiveData<Int> = MutableLiveData(2)
    val maxDistanceInKmFilter: MutableLiveData<Float> = MutableLiveData(100.0f)



    // ===== Filter Setters =====
    fun setCheckboxFuelTypeIceFilter(boolean: Boolean) {
        checkboxFuelTypeIceFilter.value = boolean
    }

    fun setCheckboxFuelTypeBevFilter(boolean: Boolean) {
        checkboxFuelTypeBevFilter.value = boolean
    }

    fun setCheckboxFuelTypeFcevFilter(boolean: Boolean) {
        checkboxFuelTypeFcevFilter.value = boolean
    }

    fun setNumberOfSeatsFilter(number: Int) {
        numberOfSeatsFilter.value = number
    }

    fun setMaxDistanceInKmFilter(number: Float) {
        maxDistanceInKmFilter.value = number
    }


    // ===== Offers =====
    fun setOfferCollection(offers: Collection<OfferData>) {

        var filteredOffers = offers.filter { it.car.numberOfSeats >= numberOfSeatsFilter.value!! }
        filteredOffers = filteredOffers.filter { it.distance <= (maxDistanceInKmFilter.value!! * 1000) }

        if (!checkboxFuelTypeIceFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "ICE" }
        }

        if (!checkboxFuelTypeBevFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "BEV" }
        }

        if (!checkboxFuelTypeFcevFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "FCEV" }
        }

        offerCollection.value = filteredOffers
    }

    fun getOfferWithId(id: Long): OfferData? {
        Log.d("[OVM] getOfferWithId", "getOfferWithId: $id")
        return offerCollection.value?.find { it.id == id }
    }




    // ===== Repository Interaction =====
    fun getOffers() {
        viewModelScope.launch {
            try {
                val offerResponse = offerRepository.getOpenOffers()
                setOfferCollection(updateOfferDataWithDistance(offerResponse))
            } catch (e: Exception) {
                Log.e("[OfferVM] getOffers", e.message.toString())
            }
        }
    }


    suspend fun updateOfferDataWithDistance(offers: Collection<OfferData>): Collection<OfferData> {

        val userLocation = SessionManager.getDeviceLocation()
        val deviceLocation = Location("deviceLocation")
        deviceLocation.latitude = userLocation.latitude
        deviceLocation.longitude = userLocation.longitude
        offers.forEach { offer ->
            val pickupLocation = offer.pickupLocation
            val pickupLocationGeocode = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)?.results?.get(0)?.geometry?.location
            val pickupLocationLatLng = LatLng(pickupLocationGeocode?.lat!!, pickupLocationGeocode.lng!!)
            val pickupLocationLocation = Location("pickupLocation")
            pickupLocationLocation.latitude = pickupLocationLatLng.latitude
            pickupLocationLocation.longitude = pickupLocationLatLng.longitude
            val distance = deviceLocation.distanceTo(pickupLocationLocation)
            offer.distance = distance
        }

        val sortedOffers = offers.sortedBy { offer -> offer.distance }
        return sortedOffers
    }

    fun getBookings(userId: Long) {
        viewModelScope.launch {
            try {
                val getBookingResponse = offerRepository.getBookings(userId)

                // TODO: Refactor this sorting and that of getOffers() to a separate function
                val userLocation = SessionManager.getDeviceLocation()
                val deviceLocation = Location("deviceLocation")
                deviceLocation.latitude = userLocation.latitude
                deviceLocation.longitude = userLocation.longitude
                getBookingResponse.forEach { booking ->
                    val pickupLocation = booking.offer.pickupLocation
                    val pickupLocationGeocode = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)?.results?.get(0)?.geometry?.location
                    val pickupLocationLatLng = LatLng(pickupLocationGeocode?.lat!!, pickupLocationGeocode.lng!!)
                    val pickupLocationLocation = Location("pickupLocation")
                    pickupLocationLocation.latitude = pickupLocationLatLng.latitude
                    pickupLocationLocation.longitude = pickupLocationLatLng.longitude
                    val distance = deviceLocation.distanceTo(pickupLocationLocation)
                    booking.offer.distance = distance
                }

                val sortedBookings = getBookingResponse.sortedBy { booking -> booking.offer.distance }
                bookingsResult.value = sortedBookings

            } catch (e: Exception) {
                Log.e("[OfferVM] getB error", e.message.toString())
            }
        }
    }

    fun createBooking(offerId: Long, customerId: Long) {
        viewModelScope.launch {
            try {
                val createBookingResponse = offerRepository.createBooking(offerId, customerId)
                createBookingResult.value = createBookingResponse
                Log.d("[OfferVM] crBookingResp", createBookingResponse.toString())
                Log.d("[OfferVM] crBookingResu", createBookingResult.value.toString())
            } catch (e: Exception) {
                Log.d("[OfferVM] bookingresult", createBookingResult.value.toString())
                Log.e("[OfferVM] crBookingResu", e.message.toString())
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