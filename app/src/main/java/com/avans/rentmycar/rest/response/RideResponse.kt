package com.avans.rentmycar.rest.response

import com.squareup.moshi.Json

data class RideResponse(

    @Json(name="data")
    val data: List<DataItem?>? = null,

    @Json(name="status")
    val status: Int? = null
)

data class DataItem(

    @Json(name="startRideLatitude")
    val startRideLatitude: Any? = null,

    @Json(name="endRideLongitude")
    val endRideLongitude: Any? = null,

    @Json(name="startDateTime")
    val startDateTime: String? = null,

    @Json(name="startRideLongitude")
    val startRideLongitude: Any? = null,

    @Json(name="totalKilometersDriven")
    val totalKilometersDriven: Any? = null,

    @Json(name="endRideLatitude")
    val endRideLatitude: Any? = null,

    @Json(name="id")
    val id: Int? = null,

    @Json(name="endDateTime")
    val endDateTime: String? = null,

    @Json(name="maxAccelerationForce")
    val maxAccelerationForce: Any? = null
)
