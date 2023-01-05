package com.avans.rentmycar.repository

import android.util.Log
import com.avans.rentmycar.api.BookingDTO
import com.avans.rentmycar.api.OfferService
import com.avans.rentmycar.model.*

class OfferRepository {

    suspend fun getOpenOffers(): Collection<OfferData> {
        return OfferService.getApi()?.getOpenOffers()?.body()?.data ?: emptyList()
    }

    suspend fun getBookings(userId: Long): Collection<BookingData> {
        if(userId == 0L) {
            return OfferService.getApi()?.getBookings()?.body()?.data ?: emptyList()
        } else {
            return OfferService.getApi()?.getBookingsForUser(userId)?.body()?.data ?: emptyList()
        }
    }



    suspend fun createBooking(offerId: Long, customerId: Long): BookingResponse? {
        Log.d("[OfferRep] crBooking", "offerId: $offerId, customerId: $customerId")
        var bookingDTO = BookingDTO()
        bookingDTO.offerId = offerId
        bookingDTO.customerId = customerId
        Log.d("[OfferRep] crBooking", "bookingDTO: $bookingDTO")
        return OfferService.getApi()?.createBooking(bookingDTO)?.body()
    }





//    fun getMockOffers(): List<OfferData> {
//        val offers = mutableListOf<OfferData>()
//
//        val offerService = OfferService
//        offers.add(
//            OfferData(
//                1,
//                "2023-01-02T17:22:52",
//                "2023-01-02T17:22:53",
//                "Tilburg",
//                OfferCarData(
//                    "BEV",
//                    3,
//                    "AB12DC",
//                    2022,
//                    "Tesla",
//                    "Black",
//                    12345,
//                    4,
//                    "",
//                    "",
//                    OfferUserData(
//                        3,
//                        "email@adres.nl",
//                        "Jan",
//                        "Jansen",
//                        "",
//                        "",
//                        "",
//                        true,
//                        "2000-01-02",
//                        666,
//                        "",
//                        true,
//                        false
//                    )
//                )
//
//            )
//        )
//
//        return offers
//    }


}