package com.avans.rentmycar.model.response

data class BookingResponse(
    val data: Collection<BookingData>,
    val message: String,
    val status: Int
)

data class CreateBookingResponse(
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

