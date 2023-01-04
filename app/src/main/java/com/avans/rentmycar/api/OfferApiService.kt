package com.avans.rentmycar.api


import com.avans.rentmycar.model.BookingResponse
import com.avans.rentmycar.model.OfferResponse
import com.avans.rentmycar.rest.ApiClient
import retrofit2.Response
import retrofit2.http.*

interface OfferService {

    @GET("/api/v1/offers")
    fun getOffers(): Response<OfferResponse>

    @GET("/api/v1/offers/unbooked")
    suspend fun getOpenOffers(): Response<OfferResponse>

    @POST("/api/v1/bookings/")
    suspend fun createBooking(@Body bookingDTO: BookingDTO): Response<BookingResponse>

    @GET("/api/v1/bookings")
    suspend fun getBookings(): Response<BookingResponse>

    @GET("/api/v1/bookings")
    suspend fun getBookingsForUser(@Query("customerId") userId: Long): Response<BookingResponse>

    companion object {
        fun getApi(): OfferService? {
            return ApiClient.client?.create(OfferService::class.java)
        }

    }
}

class BookingDTO {
    var offerId: Long = 0
    var customerId: Long = 0
}
