package com.avans.rentmycar.model.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class OfferRequest(
    @Json(name = "startDateTime") var startDateTime: String?,
    @Json(name = "endDateTime") var endDateTime: String?,
    @Json(name = "pickupLocation") var pickupLocation: String?,
    @Json(name = "carId") var carId: Long?,
)