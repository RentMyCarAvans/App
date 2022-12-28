package com.avans.rentmycar.rest.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("data")
    val data: DataLogin,

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: Int
)


data class DataLogin(
    val userId: String,
    val token: String,

)