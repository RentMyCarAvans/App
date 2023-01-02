package com.avans.rentmycar.model

import com.squareup.moshi.Json

data class OfferList(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "startDateTime") val licensePlate: String,
    @field:Json(name = "endDateTime") val yearOfManufacture: Int,
    @field:Json(name = "pickupLocation") val model: String,
    @field:Json(name = "car") val car: CarList
)