package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.model.response.BookingData
import com.avans.rentmycar.model.response.CreateBookingResponse
import com.avans.rentmycar.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {

    private val bookingRepository = BookingRepository()

    // ===== Variables for the API calls =====
    var bookingCollection = MutableLiveData<Collection<BookingData>>()
    var bookingSingle = MutableLiveData<BookingData?>()
    val createBookingResult: MutableLiveData<CreateBookingResponse?> = MutableLiveData()
    var cancelBookingResult: MutableLiveData<Boolean> = MutableLiveData(false)
    var declineBookingResult: MutableLiveData<Boolean> = MutableLiveData(false)
    var approveBookingResult: MutableLiveData<Boolean> = MutableLiveData(false)



    // ===== Bookings =====
    private fun setBookingCollection(bookings: Collection<BookingData>) {
        Log.d("[BVM] setBookColl", "setBookingCollection: $bookings")
        bookingCollection.value = bookings
    }

    fun getBookingById(id: Long) {

        Log.d("[BVM] getBookingWithId", "getBookingWithId: $id")
        if(bookingCollection.value != null) {
            bookingSingle.value = bookingCollection.value?.find { it.id == id }
        } else {
            Log.d("[BVM] getBookingById", "bookingCollection is null")
            viewModelScope.launch {
                try {
                    val bookingResponse = bookingRepository.getBookingById(id)
                    Log.d("[BVM] getBookings", "bookingResponse: $bookingResponse")
                    if (bookingResponse != null) {
                        bookingSingle.value = bookingResponse
                    }
                } catch (e: Exception) {
                    Log.e("[BVM] getBookings", e.message.toString())
                }
            }
        }
    }

    fun getBookingForOfferById(id: Long) {
        Log.d("[BVM] getBookingForOfferById", " *************** getBookingForOfferById: $id")
//        if(bookingCollection.value != null) {
//            Log.d("[BVM] getBookingForOfferById", " *************** bookingCollection is not null")
//            // Find the booking where the offer.id == id
//            bookingSingle.value = bookingCollection.value?.find { it.offer.id == id }
//            Log.d("[BVM] getBookingForOfferById", " *************** bookingCollection: ${bookingCollection.value}")
//            Log.d("[BVM] getBookingForOfferById", " *************** bookingSingle: ${bookingSingle.value}")
//        } else {
            Log.d("[BVM] getBookingForOfferById", "**************bookingCollection is null")
            viewModelScope.launch {
                try {
                    val bookingResponse = bookingRepository.getBookingForOfferById(id)
                    Log.d("[BVM] getBookingForOfferById", "bookingResponse: $bookingResponse")
                    if (bookingResponse != null) {
                        bookingSingle.value = bookingResponse
                        Log.d("[BVM] getBookingForOfferById", "bookingSingle: $bookingSingle")
                    }
                } catch (e: Exception) {
                    Log.e("[BVM] getBookingForOfferById", e.message.toString())
                }
            }
//        }
    }

    fun clearSingleBooking() {
        bookingSingle.value = null
    }



    // ===== Repository Interaction =====
    fun getBookings(userId: Long? = null) {
        Log.d("[BVM] getBookings", "getBookings called")

        viewModelScope.launch {
            try {
                val bookingResponse = bookingRepository.getBookings(userId?:0L)
                Log.d("[BVM] getBookings", "bookingResponse: $bookingResponse")
                setBookingCollection(bookingResponse)
            } catch (e: Exception) {
                Log.e("[BVM] getBookings", e.message.toString())
            }
        }
    }

    fun createBooking(offerId: Long, customerId: Long) {
        viewModelScope.launch {
            try {
                val bookingRepository = BookingRepository()
                val createBookingResponse = bookingRepository.createBooking(offerId, customerId)
                createBookingResult.value = createBookingResponse
                Log.d("[OVM] suc crBookingResp", createBookingResponse.toString())
                Log.d("[OVM] suc crBookingResu", createBookingResult.value.toString())
            } catch (e: Exception) {
                createBookingResult.value = null
                Log.d("[OVM] fail bookingresu", createBookingResult.value.toString())
                Log.e("[OVM] fail crBResu", e.message.toString())
            }
        }
    }

    fun cancelBooking(offerId: Long){
        viewModelScope.launch {
            try {
                val bookingRepository = BookingRepository()
                val cancelBookingResponse = bookingRepository.cancelBooking(offerId)
                Log.d("[OVM] suc cancelBooking", cancelBookingResponse.toString())
                cancelBookingResult.value = cancelBookingResponse
            } catch (e: Exception) {
                Log.e("[OVM] fail cancelBookng", e.message.toString())
                cancelBookingResult.value = false
            }
        }
    }

    fun declineBooking(offerId: Long) {
        Log.d("[BVM] declineBooking", "declineBooking called for offerId: $offerId")
        viewModelScope.launch {
            try {
                val bookingRepository = BookingRepository()
                val bookingIdBelonigingToOffer = bookingRepository.getBookingForOfferById(offerId)?.id
                if(bookingIdBelonigingToOffer != null) {
                    val declineBookingResponse = bookingRepository.cancelBooking(bookingIdBelonigingToOffer)
                    Log.d("[BVM] declineBooking", "declineBookingResponse: $declineBookingResponse")
//                    declineBookingResult.value = declineBookingResponse
                }
            } catch (e: Exception) {
                Log.e("[BVM] declineBooking", e.message.toString())
            }
        }

    }

    fun approveBooking(offerId: Long) {
        Log.d("[BVM] approveBooking", "approveBooking called for offerId: $offerId")
        viewModelScope.launch {
            try {
                val bookingRepository = BookingRepository()
                val bookingIdBelonigingToOffer = bookingRepository.getBookingForOfferById(offerId)?.id
                if(bookingIdBelonigingToOffer != null) {
                    Log.d("[BVM] approveBooking", "bookingIdBelonigingToOffer: $bookingIdBelonigingToOffer")
                    bookingRepository.approveBooking(bookingIdBelonigingToOffer)
                }
            } catch (e: Exception) {
                Log.e("[BVM] approveBooking", e.message.toString())
                Log.e("[BVM] approveBooking", e.localizedMessage.toString())
            }
        }


    }


}