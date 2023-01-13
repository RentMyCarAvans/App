package com.avans.rentmycar.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.model.response.*
import com.avans.rentmycar.repository.BookingRepository
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
    var geocodeResult: MutableLiveData<GeocodeResponsePositionstack?>? = MutableLiveData()
    val createOfferResult: MutableLiveData<CreateOfferResponse?> = MutableLiveData()
//    var geocodeResult: MutableLiveData<GeocodeResponse?>? = MutableLiveData()


    // ===== Variables for the API calls =====
    val offerCollection = MutableLiveData<Collection<OfferData>>()
    val myOfferCollection = MutableLiveData<Collection<OfferData>>()
    val singleOffer = MutableLiveData<OfferData?>()


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

        Log.d("[OVM] setOffColl", "setOfferCollection: $offers")

        Log.d("[OVM] setOffColl", "setOfferCollection numberOfSeatsFilter.value: ${numberOfSeatsFilter.value}")
        Log.d("[OVM] setOffColl", "setOfferCollection maxDistanceInKmFilter.value: ${maxDistanceInKmFilter.value}")

        var filteredOffers = offers.filter { it.car.numberOfSeats >= numberOfSeatsFilter.value!! }
                Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na seats: $filteredOffers")

//        filteredOffers = filteredOffers.filter { it.distance <= (maxDistanceInKmFilter.value!! * 1000) }
//        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na distance: $filteredOffers")



        if (!checkboxFuelTypeIceFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "ICE" }
        }

        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na ICE check: $filteredOffers")


        if (!checkboxFuelTypeBevFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "BEV" }
        }

        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na BEV check: $filteredOffers")


        if (!checkboxFuelTypeFcevFilter.value!!) {
            filteredOffers = filteredOffers.filter { it.car.type != "FCEV" }
        }

        Log.d("[OVM] setOffColl", "setOfferCollection filteredOffers na FCEV check: $filteredOffers")


        Log.d("[OVM] setOffColl", "setOfferCollection: filteredOffers: $filteredOffers")

        offerCollection.value = filteredOffers
    }

    fun getOfferById(id: Long) {
        Log.d("[OVM] getOfferById", "getOfferById called for id: $id")

        if (offerCollection.value?.find { it.id == id } != null) {
            Log.d("[OVM] getOfferById", "getOfferById: offer found in offerCollection")
            singleOffer.value = offerCollection.value?.find { it.id == id }
        }

        Log.d(
            "[OVM] getOfferById",
            "getOfferById: offer not found in offerCollection, getting it from repository"
        )
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
        Log.d("[OVM] getOffers", "getOffers called")
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

                    setOfferCollection(offerResponse)

//                    setOfferCollection(updateOfferDataWithDistance(offerResponse))



//                    setOfferCollection(offerResponse) // TODO: Remove this line when distance is implemented correctly again

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

        val userLocation = SessionManager.getDeviceLocation()
        val deviceLocation = Location("deviceLocation")
        deviceLocation.latitude = userLocation.latitude
        deviceLocation.longitude = userLocation.longitude
        offers.forEach { offer ->

            Log.d("[OVM] uOfferWithDist", "offer: $offer")

            val pickupLocation = offer.pickupLocation
            Log.d("[OVM] uOfferWithDist", "pickupLocation: $pickupLocation")
            val pickupGeolocationResponse = MapsApiService.getApi()
                ?.getLatLongFromAddress(pickupLocation)?.data?.get(0)
            Log.d("[OVM] uOfferWithDist", "pickupGeolocationResponse: $pickupGeolocationResponse")

            val pickupLocationLatLng = LatLng(
                pickupGeolocationResponse?.latitude!!,
                pickupGeolocationResponse.longitude as Double
            )
            Log.d("[OVM] uOfferWithDist", "pickupLocationLatLng: $pickupLocationLatLng")

            val pickupLocationLocation = Location("pickupLocation")

            Log.d("[OVM] uOfferWithDist", "pickupLocationLocation: $pickupLocationLocation")

            if (pickupLocationLatLng != null) {
                pickupLocationLocation.latitude = pickupLocationLatLng.latitude
            }
            if (pickupLocationLatLng != null) {
                pickupLocationLocation.longitude = pickupLocationLatLng.longitude
            }

            Log.d("[OVM] uOfferWithDist", "pickupLocationLocation 2: $pickupLocationLocation")


            val distance = deviceLocation.distanceTo(pickupLocationLocation)
            Log.d("[OVM] uOfferWithDist", "distance: $distance")
            offer.distance = distance
        }

        Log.d("[OVM] uOfferWithDist", "offers after everything: $offers")

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

//    fun getBookings(userId: Long) {
//        viewModelScope.launch {
//            try {
//                val getBookingResponse = offerRepository.getBookings(userId)
//
//                // TODO: Refactor this sorting and that of getOffers() to a separate function
//                val userLocation = SessionManager.getDeviceLocation()
//                val deviceLocation = Location("deviceLocation")
//                deviceLocation.latitude = userLocation.latitude
//                deviceLocation.longitude = userLocation.longitude
//                getBookingResponse.forEach { booking ->
//                    val pickupLocation = booking.offer.pickupLocation
//                    val pickupLocationLatLng = MapsApiService.getApi()
//                        ?.getLatLongFromAddress(pickupLocation)?.data?.get(0)?.let {
//                            LatLng(
//                                it.latitude as Double,
//                                it.longitude as Double
//                            )
//                        }
//                    val pickupLocationLocation = Location("pickupLocation")
//                    if (pickupLocationLatLng != null) {
//                        pickupLocationLocation.latitude = pickupLocationLatLng.latitude
//                    }
//                    if (pickupLocationLatLng != null) {
//                        pickupLocationLocation.longitude = pickupLocationLatLng.longitude
//                    }
//                    val distance = deviceLocation.distanceTo(pickupLocationLocation)
//                    booking.offer.distance = distance
//                }
//
//                val sortedBookings = getBookingResponse.sortedBy { booking -> booking.offer.distance }
//                bookingsResult.value = sortedBookings
//
//            } catch (e: Exception) {
//                Log.e("[OfferVM] getB error", e.message.toString())
//            }
//        }
//    }

//    // TODO: Move all references to this method to the BookingViewModel
//    fun createBooking(offerId: Long, customerId: Long) {
//        viewModelScope.launch {
//            try {
//                val bookingRepository = BookingRepository()
//                val createBookingResponse = bookingRepository.createBooking(offerId, customerId)
//                createBookingResult.value = createBookingResponse
//                Log.d("[OfferVM] crBookingResp", createBookingResponse.toString())
//                Log.d("[OfferVM] crBookingResu", createBookingResult.value.toString())
//            } catch (e: Exception) {
//                Log.d("[OfferVM] bookingresult", createBookingResult.value.toString())
//                Log.e("[OfferVM] crBookingResu", e.message.toString())
//                Log.e("[OfferVM] createBooking", e.message.toString())
//            }
//        }
//    }

    fun createOffer( startDateTime: String,
                     endDateTime: String,
                     pickupLocation: String,
                     carId: Long) {
        viewModelScope.launch {
            try {
                val createOfferResponse = offerRepository.createOffer(startDateTime, endDateTime, pickupLocation, carId)
                createOfferResult.value = createOfferResponse
                Log.d("[OfferVM] crOfferResp", createOfferResponse.toString())
            } catch (e: Exception) {
                Log.d("[OfferVM] offerResult", createOfferResult.value.toString())
                Log.e("[OfferVM] crOfferResu", e.message.toString())
            }
        }
    }

    fun getGeocodeResponse(pickupLocation: String) {
        viewModelScope.launch {
            try {
                val geocodeResponse = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)
                Log.i("[OfferVM] geocodeResp", geocodeResponse.toString())
                geocodeResult?.value = geocodeResponse
            } catch (e: Exception) {
                Log.e("[OfferVM] getGeoRespon", e.message.toString())
            }
        }

    }



}