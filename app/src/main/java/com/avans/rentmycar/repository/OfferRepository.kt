package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.OfferDTO
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.response.BookingData
import com.avans.rentmycar.model.response.CreateBookingResponse
import com.avans.rentmycar.model.response.CreateOfferResponse
import com.avans.rentmycar.model.response.OfferData

class OfferRepository {

    suspend fun getOpenOffers(): Collection<OfferData> {
        return OfferService.getApi()?.getOpenOffers()?.body()?.data ?: emptyList()
    }

    suspend fun getOfferById(id: Long): OfferData? {
        return OfferService.getApi()?.getOfferById(id)?.body()?.data
    }

    suspend fun getOffersByUserId(userId: Long): Collection<OfferData> {
        return OfferService.getApi()?.getOffersByUserId(userId)?.body()?.data ?: emptyList()
    }

    suspend fun getBookings(userId: Long): Collection<BookingData> {
        return if(userId == 0L) {
            OfferService.getApi()?.getBookings()?.body()?.data ?: emptyList()
        } else {
            OfferService.getApi()?.getBookingsForUser(userId)?.body()?.data ?: emptyList()
        }
    }



    suspend fun createBooking(offerId: Long, customerId: Long): CreateBookingResponse? {
        val bookingDTO = BookingDTO()
        bookingDTO.offerId = offerId
        bookingDTO.customerId = customerId
        return OfferService.getApi()?.createBooking(bookingDTO)?.body()
    }

    suspend fun createOffer(
        startDateTime: String,
        endDateTime: String,
        pickupLocation: String,
        carId: Long,
    ): CreateOfferResponse? {
        Log.d("[OfferRep] crOffer", "startDateTime: $startDateTime, endDateTime: $endDateTime, pickupLocation: $pickupLocation, carId: $carId")
        val offerDTO = OfferDTO()
        offerDTO.startDateTime = startDateTime
        offerDTO.endDateTime = endDateTime
        offerDTO.pickupLocation = pickupLocation
        offerDTO.carId = carId
        return OfferService.getApi()?.createOffer(offerDTO)?.body()
    }

}



