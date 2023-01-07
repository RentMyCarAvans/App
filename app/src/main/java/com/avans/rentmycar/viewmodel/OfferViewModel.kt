package com.avans.rentmycar.viewmodel

import android.location.Location
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.BookingResponse
import com.avans.rentmycar.model.GeocodeResponse
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.repository.OfferRepository
import com.avans.rentmycar.utils.SessionManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    private val offerRepository = OfferRepository()

    val offerResult: MutableLiveData<Collection<OfferData>> = MutableLiveData()

    val bookingResult: MutableLiveData<BookingResponse?> = MutableLiveData()
    val bookingsResult: MutableLiveData<Collection<BookingData>> = MutableLiveData()

    var geocodeResult: MutableLiveData<GeocodeResponse?>? = MutableLiveData()

    var carLocationList: MutableLiveData<Map<Long,LatLng>> = MutableLiveData()
    var carDistanceList: MutableLiveData<Map<Long,Float>> = MutableLiveData()


    fun getOffers() {
        viewModelScope.launch {
            try {
                val offerResponse = offerRepository.getOpenOffers()

                // For each offer, use the pickupLocation and calculate the distance to the device location and add it to the offer.distance
                val userLocation = SessionManager.getUserLocation()
                val deviceLocation = Location("deviceLocation")
                deviceLocation.latitude = userLocation.latitude
                deviceLocation.longitude = userLocation.longitude
                offerResponse.forEach { offer ->
                    val pickupLocation = offer.pickupLocation
                    val pickupLocationGeocode = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)?.results?.get(0)?.geometry?.location
                    val pickupLocationLatLng = LatLng(pickupLocationGeocode?.lat!!, pickupLocationGeocode.lng!!)
                    val pickupLocationLocation = Location("pickupLocation")
                    pickupLocationLocation.latitude = pickupLocationLatLng.latitude
                    pickupLocationLocation.longitude = pickupLocationLatLng.longitude
                    val distance = deviceLocation.distanceTo(pickupLocationLocation)
                    offer.distance = distance
                }

                // Sort the offers by distance
                val sortedOffers = offerResponse.sortedBy { offer -> offer.distance }
                offerResult.value = sortedOffers







//                offerResult.value = offerResponse
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


    fun getListOfCarLocations() {
        viewModelScope.launch {
            Log.d("[OfferVM] getListOfCar", "getListOfCarLocations")
            try {
                val offerResponse = offerRepository.getOpenOffers()
                offerResult.value = offerResponse

                val _carLocationList = mutableMapOf<Long,LatLng>()
                val _carDistanceList = mutableMapOf<Long,Float>()

                offerResponse.forEach {
                    val geocodeResponse = MapsApiService.getApi()?.getLatLongFromAddress(it.pickupLocation)
                    Log.d("[OfferVM] geocodeResp", "geocodeResponse: $geocodeResponse")
                    val lat = geocodeResponse?.results?.get(0)?.geometry?.location?.lat
                    val lng = geocodeResponse?.results?.get(0)?.geometry?.location?.lng
                    Log.d("[OfferVM] lat lng", "lat: $lat, lng: $lng")
                    if (lat != null && lng != null) {
                        var latlngToAdd = LatLng(lat, lng)
                        Log.d("[OfferVM] latlngToAdd", "latlngToAdd: $latlngToAdd")
                        // Add the latlng to the collection
                        _carLocationList.put(it.id,latlngToAdd)
                        carLocationList.value = _carLocationList

                        // Get the distance from the user to the car
                        val userLocation = SessionManager.getUserLocation()
                        val userLat = userLocation?.latitude
                        val userLng = userLocation?.longitude

                        val distance = FloatArray(2)
                        Location.distanceBetween(
                            userLat!!,
                            userLng!!,
                            latlngToAdd.latitude,
                            latlngToAdd.longitude,
                            distance
                        )
                        _carDistanceList.put(it.id,distance[0])
                        carDistanceList.value = _carDistanceList


//                        carLocationList.value?.add(latlngToAdd)
                        Log.d("[OfferVM] carLocList", "carLocationList: ${carLocationList} ")
                    }
                }
            } catch (e: Exception) {
                Log.e("[OfferVM] getCarLoc", e.message.toString())
            }
        }

    }




}