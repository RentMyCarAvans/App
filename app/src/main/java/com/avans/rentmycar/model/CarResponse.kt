package com.avans.rentmycar.model

import com.squareup.moshi.Json

// CarResponse is the response of the Api call to cars
// Note: @Json is Moshi’s equivalent to @SerializedName from Gson.
data class CarResponse(
    @field:Json(name="data") val data: List<CarList>,
    @field:Json(name="status") val status: Int
)