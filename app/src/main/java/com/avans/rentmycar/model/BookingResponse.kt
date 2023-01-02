package com.avans.rentmycar.model

data class BookingResponse(
    val data: BookingData,
    val message: String,
    val status: Int
)

data class BookingData(
    val id: Long,
    val offer: OfferData,
    val ride: String,
    val customer: OfferUserData,
)

