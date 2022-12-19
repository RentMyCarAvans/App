package com.avans.rentmycar.rest.request

import com.google.gson.annotations.SerializedName

data class CreateUserRequest (
    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("bonusPoints")
    val bonusPoints: Int,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("email")
    val email: String
)
