package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.OfferDTO
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.*

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


    suspend fun createBooking(offerId: Long, customerId: Long): BookingResponse? {
        Log.d("[OfferRep] crBooking", "offerId: $offerId, customerId: $customerId")
        val bookingDTO = BookingDTO()
        bookingDTO.offerId = offerId
        bookingDTO.customerId = customerId
        Log.d("[OfferRep] crBooking", "bookingDTO: $bookingDTO")
        return OfferService.getApi()?.createBooking(bookingDTO)?.body()
    }

    suspend fun createOffer(
        startDateTime: String,
        endDateTime: String,
        pickupLocation: String,
        carId: Long,
    ): OfferResponse? {
        Log.d("[OfferRep] crOffer", "startDateTime: $startDateTime, endDateTime: $endDateTime, pickupLocation: $pickupLocation, carId: $carId")
        val offerDTO = OfferDTO()
        offerDTO.startDateTime = startDateTime
        offerDTO.endDateTime = endDateTime
        offerDTO.pickupLocation = pickupLocation
        offerDTO.carId = carId
        return OfferService.getApi()?.createOffer(offerDTO)?.body()
    }



}
