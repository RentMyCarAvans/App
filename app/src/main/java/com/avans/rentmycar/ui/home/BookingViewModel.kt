package com.avans.rentmycar.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {

    private val bookingRepository = BookingRepository()

    // ===== Variables for the API calls =====
    var bookingCollection = MutableLiveData<Collection<BookingData>>()
    var bookingSingle = MutableLiveData<BookingData?>()

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
    fun getBookingsForUser(userId: Long) {
        Log.d("[BVM] getBookings", "getBookings called")

        viewModelScope.launch {
            try {
                val bookingResponse = bookingRepository.getBookings(userId)
                Log.d("[BVM] getBookings", "bookingResponse: $bookingResponse")
                setBookingCollection(bookingResponse)
            } catch (e: Exception) {
                Log.e("[BVM] getBookings", e.message.toString())
            }
        }
    }


}