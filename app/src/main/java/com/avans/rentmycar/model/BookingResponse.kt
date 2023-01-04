package com.avans.rentmycar.model

data class BookingResponse(
    // TODO: val data can either be a BookingData or a String because of the error message. API should return empty data array on error en report message in string
    val data: Collection<BookingData>,
    val message: String,
    val status: Int
)

data class BookingData(
    val id: Long,
    val offer: OfferData,
    val ride: String,
    val customer: OfferUserData,
)

