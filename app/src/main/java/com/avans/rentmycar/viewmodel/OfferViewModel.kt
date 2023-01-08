package com.avans.rentmycar.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.BookingResponse
import com.avans.rentmycar.model.CreateBookingResponse
import com.avans.rentmycar.model.GeocodeResponse
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.repository.OfferRepository
import com.avans.rentmycar.utils.SessionManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class OfferViewModel : ViewModel() {

    // Declare the repository
    private val offerRepository = OfferRepository()

    // ===== Results of the API calls =====
    // TODO: Refactor this
    val offerResult: MutableLiveData<Collection<OfferData>> = MutableLiveData()
    val bookingsResult: MutableLiveData<Collection<BookingData>> = MutableLiveData()
    val createBookingResult: MutableLiveData<CreateBookingResponse?> = MutableLiveData()
    var geocodeResult: MutableLiveData<GeocodeResponse?>? = MutableLiveData()


    // ===== Variables for the API calls =====
    // TODO: Use these
    val offerCollection = MutableLiveData<Collection<OfferData>>()


    // ===== Lists for sorting =====
    // TODO: Refactor this and remove it
    var carLocationList: MutableLiveData<Map<Long,LatLng>> = MutableLiveData()
    var carDistanceList: MutableLiveData<Map<Long,Float>> = MutableLiveData()



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
        // TODO: Trigger a loading screen / spinner on the HomeFragment
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
        // TODO: Refactor this sorting and that of getOffers() to a separate function

        val userLocation = SessionManager.getUserLocation()
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

        // Sort the offers by distance
        val sortedOffers = offers.sortedBy { offer -> offer.distance }

        return sortedOffers

//        offerResult.value = sortedOffers
//        setOfferCollection(sortedOffers)
    }

    fun getBookings(userId: Long) {
        viewModelScope.launch {
            try {
                val getBookingResponse = offerRepository.getBookings(userId)

                // TODO: Refactor this sorting and that of getOffers() to a separate function
                val userLocation = SessionManager.getUserLocation()
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

//                val bookingResponse = offerRepository.createBooking(offerId, customerId)
//                bookingResult.value = createBookingResponse
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


//    fun getListOfCarLocations() {
//        viewModelScope.launch {
//            Log.d("[OfferVM] getListOfCar", "getListOfCarLocations")
//            try {
//                val offerResponse = offerRepository.getOpenOffers()
//                offerResult.value = offerResponse
//
//                val _carLocationList = mutableMapOf<Long,LatLng>()
//                val _carDistanceList = mutableMapOf<Long,Float>()
//
//                offerResponse.forEach {
//                    val geocodeResponse = MapsApiService.getApi()?.getLatLongFromAddress(it.pickupLocation)
//                    Log.d("[OfferVM] geocodeResp", "geocodeResponse: $geocodeResponse")
//                    val lat = geocodeResponse?.results?.get(0)?.geometry?.location?.lat
//                    val lng = geocodeResponse?.results?.get(0)?.geometry?.location?.lng
//                    Log.d("[OfferVM] lat lng", "lat: $lat, lng: $lng")
//                    if (lat != null && lng != null) {
//                        var latlngToAdd = LatLng(lat, lng)
//                        Log.d("[OfferVM] latlngToAdd", "latlngToAdd: $latlngToAdd")
//                        // Add the latlng to the collection
//                        _carLocationList.put(it.id,latlngToAdd)
//                        carLocationList.value = _carLocationList
//
//                        // Get the distance from the user to the car
//                        val userLocation = SessionManager.getUserLocation()
//                        val userLat = userLocation?.latitude
//                        val userLng = userLocation?.longitude
//
//                        val distance = FloatArray(2)
//                        Location.distanceBetween(
//                            userLat!!,
//                            userLng!!,
//                            latlngToAdd.latitude,
//                            latlngToAdd.longitude,
//                            distance
//                        )
//                        _carDistanceList.put(it.id,distance[0])
//                        carDistanceList.value = _carDistanceList
//
//
////                        carLocationList.value?.add(latlngToAdd)
//                        Log.d("[OfferVM] carLocList", "carLocationList: ${carLocationList} ")
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("[OfferVM] getCarLoc", e.message.toString())
//            }
//        }
//
//    }

//    fun getOffersBySeats(i: Int) {
//        viewModelScope.launch {
//            try {
//                val offerResponse = offerRepository.getOpenOffers()
//
//                // Filter all offers by the number of seats of the car
//                val filteredOffers = offerResponse.filter { offer -> offer.car.numberOfSeats >= i }
//
//
//                val userLocation = SessionManager.getUserLocation()
//                val deviceLocation = Location("deviceLocation")
//                deviceLocation.latitude = userLocation.latitude
//                deviceLocation.longitude = userLocation.longitude
//                filteredOffers.forEach { offer ->
//                    val pickupLocation = offer.pickupLocation
//                    val pickupLocationGeocode = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)?.results?.get(0)?.geometry?.location
//                    val pickupLocationLatLng = LatLng(pickupLocationGeocode?.lat!!, pickupLocationGeocode.lng!!)
//                    val pickupLocationLocation = Location("pickupLocation")
//                    pickupLocationLocation.latitude = pickupLocationLatLng.latitude
//                    pickupLocationLocation.longitude = pickupLocationLatLng.longitude
//                    val distance = deviceLocation.distanceTo(pickupLocationLocation)
//                    offer.distance = distance
//                }
//
//                // Sort the offers by distance
//                val sortedOffers = filteredOffers.sortedBy { offer -> offer.distance }
//
//
//
//                offerResult.value = sortedOffers
//
//            } catch (e: Exception) {
//                Log.e("[OVM] getOffersBySeats", e.message.toString())
//            }
//        }
//    }

//    fun getOffersByColor(s: String) {
//        Log.d("[OfferVM] getOByClr", "getOffersByColor")
//        viewModelScope.launch {
//            try {
//                val offerResponse = offerRepository.getOpenOffers()
//
//                // Filter all offers by the number of seats of the car
//                val filteredOffers = offerResponse.filter { offer -> offer.car.colorType == s }
//
//
//                val userLocation = SessionManager.getUserLocation()
//                val deviceLocation = Location("deviceLocation")
//                deviceLocation.latitude = userLocation.latitude
//                deviceLocation.longitude = userLocation.longitude
//                filteredOffers.forEach { offer ->
//                    val pickupLocation = offer.pickupLocation
//                    val pickupLocationGeocode = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)?.results?.get(0)?.geometry?.location
//                    val pickupLocationLatLng = LatLng(pickupLocationGeocode?.lat!!, pickupLocationGeocode.lng!!)
//                    val pickupLocationLocation = Location("pickupLocation")
//                    pickupLocationLocation.latitude = pickupLocationLatLng.latitude
//                    pickupLocationLocation.longitude = pickupLocationLatLng.longitude
//                    val distance = deviceLocation.distanceTo(pickupLocationLocation)
//                    offer.distance = distance
//                }
//
//                // Sort the offers by distance
//                val sortedOffers = filteredOffers.sortedBy { offer -> offer.distance }
//
//
//
//                offerResult.value = sortedOffers
//
//            } catch (e: Exception) {
//                Log.e("[OVM] getOffersBySeats", e.message.toString())
//            }
//        }
//    }


//    suspend fun calculateDistanceAndSortByDistance(data: Collection<OfferData>): Collection<OfferData> {
//        val userLocation = SessionManager.getUserLocation()
//        val deviceLocation = Location("deviceLocation")
//        deviceLocation.latitude = userLocation.latitude
//        deviceLocation.longitude = userLocation.longitude
//        data.forEach { offer ->
//            val pickupLocation = offer.pickupLocation
//            val pickupLocationGeocode = MapsApiService.getApi()?.getLatLongFromAddress(pickupLocation)?.results?.get(0)?.geometry?.location
//            val pickupLocationLatLng = LatLng(pickupLocationGeocode?.lat!!, pickupLocationGeocode.lng!!)
//            val pickupLocationLocation = Location("pickupLocation")
//            pickupLocationLocation.latitude = pickupLocationLatLng.latitude
//            pickupLocationLocation.longitude = pickupLocationLatLng.longitude
//            val distance = deviceLocation.distanceTo(pickupLocationLocation)
//            offer.distance = distance
//        }
//        val sortedOffers = data.sortedBy { offer -> offer.distance }
//        return sortedOffers
//
//    }


}