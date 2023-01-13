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

    // ===== Bookings =====
    fun setBookingCollection(bookings: Collection<BookingData>) {
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

    // TODO: Move all references to this method to the BookingViewModel
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


}