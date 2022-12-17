package com.avans.rentmycar.rest.response

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)
