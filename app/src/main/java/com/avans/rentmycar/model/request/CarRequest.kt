package com.avans.rentmycar.model.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class CarRequest(
    @Json(name = "colorType") var colorType: String?,
    @Json(name = "image") var image: String?,
    @Json(name = "licensePlate") var licensePlate: String?,
    @Json(name = "mileage") var mileage: Int?,
    @Json(name = "model") var model: String?,
    @Json(name = "numberOfSeats") var numberOfSeats: Int?,
    @Json(name = "type") var type: String?,
    @Json(name = "userId") var userId: Int?,
    @Json(name = "vehicleType") var vehicleType: String?,
    @Json(name = "yearOfManufacture") var yearOfManufacture: Int?
)