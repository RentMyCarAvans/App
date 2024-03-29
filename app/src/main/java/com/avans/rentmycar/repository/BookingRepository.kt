package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.BookingUpdateDTO
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.response.BookingData
import com.avans.rentmycar.model.response.CreateBookingResponse


class BookingRepository {

    suspend fun getBookings(userId: Long?): Collection<BookingData> {
        return if(userId == null) {
            // TODO: Create BookingService
            OfferService.getApi()?.getBookings()?.body()?.data ?: emptyList()
        } else {
            OfferService.getApi()?.getBookingsForUser(userId)?.body()?.data ?: emptyList()
        }
    }

    suspend fun getBookingById(bookingId: Long): BookingData? {
        // TODO: Create BookingService
        return OfferService.getApi()?.getBookingById(bookingId)?.body()?.data
    }

    suspend fun getBookingForOfferById(offerId: Long): BookingData? {
        Log.d("[BRepo] getBookingForOfferById", "getBookingForOfferById: $offerId")
        val result = OfferService.getApi()?.getBookingForOfferById(offerId)?.body()?.data
        Log.d("[BRepo] getBookingForOfferById", "result: $result")
        return result
    }

    suspend fun createBooking(offerId: Long, customerId: Long): CreateBookingResponse? {
        val bookingDTO = BookingDTO()
        bookingDTO.offerId = offerId
        bookingDTO.customerId = customerId
        return OfferService.getApi()?.createBooking(bookingDTO)?.body()
    }

    suspend fun cancelBooking(bookingId: Long): Boolean {
        return OfferService.getApi()?.cancelBooking(bookingId)?.isSuccessful ?: false
    }

    suspend fun approveBooking(bookingId: Long): Boolean {
        val bookingUpdateDTO = BookingUpdateDTO()
        bookingUpdateDTO.status = "APPROVED"
        return OfferService.getApi()?.approveBooking(bookingId, bookingUpdateDTO)?.isSuccessful ?: false

    }


}