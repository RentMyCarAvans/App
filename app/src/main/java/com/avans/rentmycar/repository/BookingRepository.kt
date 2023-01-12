package com.avans.rentmycar.repository

import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.model.CreateBookingResponse
import com.avans.rentmycar.model.SingleBookingResponse

class BookingRepository {

    suspend fun getBookings(userId: Long): Collection<BookingData> {
        return if(userId == 0L) {
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

    suspend fun createBooking(offerId: Long, customerId: Long): CreateBookingResponse? {
        val bookingDTO = BookingDTO()
        bookingDTO.offerId = offerId
        bookingDTO.customerId = customerId
        return OfferService.getApi()?.createBooking(bookingDTO)?.body()
    }


}