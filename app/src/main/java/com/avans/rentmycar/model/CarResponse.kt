package com.avans.rentmycar.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// CarResponse is the response of the Api call to cars
// Note: @Json is Moshi’s equivalent to @SerializedName from Gson.
data class CarResponse(
    @field:Json(name="data") val data: Collection<CarList>,
    @field:Json(name="status") val status: Int
)

data class CreateCarResponse(
    @field:Json(name="data") val data: CarList,
    @field:Json(name="status") val status: Int
)

@Keep
@JsonClass(generateAdapter = true)
data class CarList(
    @field:Json(name="id") val id: Int,
    @field:Json(name="license_plate") val licensePlate: String,
    @field:Json(name="year_of_manufacture")  val yearOfManufacture: Int,
    @field:Json(name="model") val model: String,
    @field:Json(name="color_type") val colorType: String,
    @field:Json(name="mileage") val mileage: Int?,
    @field:Json(name="number_of_seats")  val numberOfSeats: Int,
    @field:Json(name="created_at") val createdAt: String,
    @field:Json(name="updated_at")  val updatedAt: String,
    @field:Json(name="vehicle_type") val vehicleType: String?,
    @field:Json(name="image") val image: String?,
    @field:Json(name="user") val user: User
)