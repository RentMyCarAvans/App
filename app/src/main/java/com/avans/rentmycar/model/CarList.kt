package com.avans.rentmycar.model

import com.squareup.moshi.Json

data class CarList(
    @field:Json(name="id") val id: Int,
    @field:Json(name="license_plate") val licensePlate: String,
    @field:Json(name="year_of_manufacture")  val yearOfManufacture: Int,
    @field:Json(name="model") val model: String,
    @field:Json(name="color_type") val colorType: String,
    @field:Json(name="mileage") val mileage: Int,
    @field:Json(name="number_of_seats")  val numberOfSeats: Int,
    @field:Json(name="created_at") val createdAt: String,
    @field:Json(name="updated_at")  val updatedAt: String,
    @field:Json(name="user") val user: User,
)