package com.avans.rentmycar.rest.request

import com.google.gson.annotations.SerializedName

data class CreateUpdateUserRequest (
    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("bonusPoints")
    val bonusPoints: Int?,

    @field:SerializedName("password")
    val password: String?,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("city")
    val city: String

)
