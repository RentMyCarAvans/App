package com.avans.rentmycar.rest.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("data")
    val data: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: Int
)