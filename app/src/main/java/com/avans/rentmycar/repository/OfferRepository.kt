package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.CreateBookingResponse
import com.avans.rentmycar.model.OfferData

class OfferRepository {

    suspend fun getOpenOffers(): Collection<OfferData> {
        return OfferService.getApi()?.getOpenOffers()?.body()?.data ?: emptyList()
    }

    suspend fun getBookings(userId: Long): Collection<BookingData> {
        Log.d("[OfferRepo] getBookings", "userId: $userId")
        return if(userId == 0L) {
            Log.d("[OfferRepo] getBookings", "userId is 0")
            OfferService.getApi()?.getBookings()?.body()?.data ?: emptyList()
        } else {
            Log.d("[OfferRepo] getBookings", "userId is not 0")
            OfferService.getApi()?.getBookingsForUser(userId)?.body()?.data ?: emptyList()
        }
    }



    suspend fun createBooking(offerId: Long, customerId: Long): CreateBookingResponse? {
        Log.d("[OfferRep] crBooking", "offerId: $offerId, customerId: $customerId")
        val bookingDTO = BookingDTO()
        bookingDTO.offerId = offerId
        bookingDTO.customerId = customerId
        return OfferService.getApi()?.createBooking(bookingDTO)?.body()
    }


}