package com.avans.rentmycar.viewmodel

import android.app.Activity
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.model.request.CarRequest
import com.avans.rentmycar.model.request.OfferRequest
import com.avans.rentmycar.model.response.*
import com.avans.rentmycar.repository.OfferRepository
import com.avans.rentmycar.utils.SessionManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    private val offerRepository = OfferRepository()

    var currentUserId: Long? = null
    val isLoading:MutableLiveData<Boolean> = MutableLiveData(true)

    // ===== Results of the API calls =====
    val createOfferResult: MutableLiveData<CreateOfferResponse?> = MutableLiveData()
    val updateOfferResult: MutableLiveData<CreateOfferResponse?> = MutableLiveData()

    // ===== Variables for the API calls =====
    val offerCollection = MutableLiveData<Collection<OfferData>>()
    val myOfferCollection = MutableLiveData<Collection<OfferData>>()
    val singleOffer = MutableLiveData<OfferData?>()

    // ===== Filter options =====
    // Checkbox Filters
    val checkboxFuelTypeIceFilter: MutableLiveData<Boolean> = MutableLiveData(true)
    val checkboxFuelTypeBevFilter: MutableLiveData<Boolean> = MutableLiveData(true)
    val checkboxFuelTypeFcevFilter: MutableLiveData<Boolean> = MutableLiveData(true)
    val checkboxShowOwnCarsFilter: MutableLiveData<Boolean> = MutableLiveData(true)

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

    fun setCheckboxShowOwnCarsFilter(boolean: Boolean) {
        checkboxShowOwnCarsFilter.value = boolean
    }

    fun setNumberOfSeatsFilter(number: Int) {
        numberOfSeatsFilter.value = number
    }

    fun setMaxDistanceInKmFilter(number: Float) {
        maxDistanceInKmFilter.value = number
    }


    // ===== Offers =====
    fun setOfferCollection(offers: Collection<OfferData>) {

//        Log.d("[OVM] setOffColl", "setOfferCollection: $offers")

//        Log.d("[OVM] setOffColl", "setOfferCollection numberOfSeatsFilter.value: ${numberOfSeatsFilter.value}")
//        Log.d("[OVM] setOffColl", "setOfferCollection maxDistanceInKmFilter.value: ${maxDistanceInKmFilter.value}")

        var filteredOffers = offers.filter { it.car.numberOfSeats >= numberOfSeatsFilter.value!! }
//                Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na seats: $filteredOffers")

//        filteredOffers = filteredOffers.filter { it.distance <= (maxDistanceInKmFilter.value!! * 1000) }
//        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na distance: $filteredOffers")



        if (!checkboxFuelTypeIceFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "ICE" }
        }

//        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na ICE check: $filteredOffers")


        if (!checkboxFuelTypeBevFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "BEV" }
        }

//        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na BEV check: $filteredOffers")


        if (!checkboxFuelTypeFcevFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "FCEV" }
        }

//        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na FCEV check: $filteredOffers")

        if(currentUserId != null) {
            if (!checkboxShowOwnCarsFilter.value!!) {
                filteredOffers = filteredOffers.filter { it.car.user.id != currentUserId }
            }
            Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na showOwnCars check: $filteredOffers")
        }



//        Log.d("[OVM] setOffColl", "setOfferCollection: filteredOffers: $filteredOffers")

        offerCollection.value = filteredOffers
        isLoading.value = false
    }

    fun getOfferById(id: Long) {
        isLoading.value = true
//        Log.d("[OVM] getOfferById", "getOfferById called for id: $id")

        if (offerCollection.value?.find { it.id == id } != null) {
//            Log.d("[OVM] getOfferById", "getOfferById: offer found in offerCollection")
            singleOffer.value = offerCollection.value?.find { it.id == id }
        }


        viewModelScope.launch {
            try {
                val offer = offerRepository.getOfferById(id)
                singleOffer.value = offer
            } catch (e: Exception) {
                Log.d("[OVM] getOfferById", "getOfferById: error: $e")
                singleOffer.value = null
            }

        }
    }




    // ===== Repository Interaction =====
    fun getOffers() {
//        Log.d("[OVM] getOffers", "getOffers called")
        viewModelScope.launch {
            try {
                val offerResponse = offerRepository.getOpenOffers()
                Log.d("[OVM] getOffers", "offerResponse: $offerResponse")

                if(SessionManager.getLocationPermissionGranted()) {
                    Log.d("[OVM] getOffers", "Location permission granted. Show offers with distances.")


                    val stringFromCoordinatesDeviceLocation = SessionManager.getDeviceLocation().latitude.toString() + "," + SessionManager.getDeviceLocation().longitude.toString()
                    Log.d("[OVM] getOffers", "stringFromCoordinatesDeviceLocation: $stringFromCoordinatesDeviceLocation")
                    val readableDeviceLocation = MapsApiService.getApi()?.getAddressFromLatLong(stringFromCoordinatesDeviceLocation)
                    Log.d("[OVM] getOffers", "readableDeviceLocation: $readableDeviceLocation")
                    SessionManager.setDeviceLocationReadable(readableDeviceLocation?.data?.get(0)?.label!!)

//                    TODO: Check why distance is not working correctly anymore
                    setOfferCollection(updateOfferDataWithDistance(offerResponse))

                } else {
                    Log.d("[OVM] getOffers", "Location permission not granted. Show offers without distances.")
                    setOfferCollection(offerResponse)
                }

            } catch (e: Exception) {
                Log.e("[OfferVM] getOffers", e.message.toString())
            }
        }
    }




    suspend fun updateOfferDataWithDistance(offers: Collection<OfferData>): Collection<OfferData> {

        val deviceLocation = Location("deviceLocation")
        deviceLocation.latitude = SessionManager.getDeviceLocation().latitude
        deviceLocation.longitude = SessionManager.getDeviceLocation().longitude
        offers.forEach { offer ->

            val carLocation = Location("carLocation")
            carLocation.latitude = offer.pickupLocationLatitude
            carLocation.longitude = offer.pickupLocationLongitude

            val distance = carLocation.distanceTo(deviceLocation)
            offer.distance = distance

        }

        return offers.sortedBy { offer -> offer.distance }
    }


    fun getMyOffers(userId: Long) {
        Log.d("[RMC][OfferVM]", "getMyOffers() => userid: "+userId)
        viewModelScope.launch {
            try {
                val offerResponse = offerRepository.getOffersByUserId(userId)
                Log.d("[RMC][OfferVM]", "getMyOffers() => Response: " + offerResponse.toString())
                myOfferCollection.value = offerResponse

            } catch (e: Exception) {
                Log.d("[RMC][OfferVM]", "getMyOffers() => ERROR: " + e.message.toString())
            }
        }
    }

    fun createOffer( startDateTime: String,
                     endDateTime: String,
                     pickupLocation: String,
                     carId: Long) {
        Log.d("[RMC][OfferViewModel]", "createOffer() => startDateTime $startDateTime, endDateTime $endDateTime location $pickupLocation carid $carId" )
        viewModelScope.launch {
            try {
                val createOfferResponse = offerRepository.createOffer(startDateTime, endDateTime, pickupLocation, carId)
                createOfferResult.value = createOfferResponse
                Log.d("[OfferVM] crOfferResp", createOfferResponse.toString())
                Log.d("[RMC][OfferViewModel]", "createOffer() => Response: " + createOfferResponse.toString() )
            } catch (e: Exception) {
                Log.d("[OfferVM] offerResult", createOfferResult.value.toString())
                Log.e("[OfferVM] crOfferResu", e.message.toString())
                Log.d("[RMC][OfferViewModel]", "createOffer() => Exception: " + createOfferResult.value.toString() )
                Log.d("[RMC][OfferViewModel]", "createOffer() => Exception message: " + e.message.toString() )
            }
        }
    }

    fun updateOffer( id: Long,
                     startDateTime: String,
                     endDateTime: String,
                     pickupLocation: String,
                     carId: Long) {
        Log.d("[RMC][OfferViewModel]", "updateOffer() => id $id, startDateTime $startDateTime, endDateTime $endDateTime location $pickupLocation carid $carId" )
        viewModelScope.launch {
            try {
                val offerRequest = OfferRequest(startDateTime = startDateTime,
                    endDateTime = endDateTime,
                    pickupLocation = pickupLocation,
                    carId = carId)
                Log.d("[RMC][OfferVM", "updateOffer() => offerRequest = " + offerRequest)
                val response = offerRepository.updateOffer(id, offerRequest = offerRequest)
                updateOfferResult.value = response
            } catch (e: Exception) {
                Log.d("[RMC][OfferViewModel]", "updateOffer() => Exception: " + createOfferResult.value.toString() )
                Log.d("[RMC][OfferViewModel]", "updateOffer() => Exception message: " + e.message.toString() )
            }
        }


    }

//    fun getGeocodeResponse(pickupLocation: String) {
//        viewModelScope.launch {
//            try {
//                val geocodeResponse = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)
//                Log.i("[OfferVM] geocodeResp", geocodeResponse.toString())
//                geocodeResult?.value = geocodeResponse
//            } catch (e: Exception) {
//                Log.e("[OfferVM] getGeoRespon", e.message.toString())
//            }
//        }
//
//    }



}