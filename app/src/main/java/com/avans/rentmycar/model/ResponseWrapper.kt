package com.avans.rentmycar.model

import com.squareup.moshi.Json

data class ResponseWrapper<T> (
    @Json(name="data") val data: List<T>,
    @Json(name="status") val status: Int
)