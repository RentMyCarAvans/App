package com.avans.rentmycar.api


import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.model.OfferResponse
import com.avans.rentmycar.rest.ApiClient
import com.avans.rentmycar.rest.response.BaseResponse
import retrofit2.Response
import retrofit2.http.*

interface OfferService {

    @GET("/api/v1/offers")
    fun getOffers(): Response<OfferResponse>

    @GET("/api/v1/offers/unbooked")
    suspend fun getOpenOffers(): Response<OfferResponse>

    @POST("/api/v1/bookings/")
    suspend fun createBooking(@Body bookingDTO: BookingDTO): Response<Any>

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
