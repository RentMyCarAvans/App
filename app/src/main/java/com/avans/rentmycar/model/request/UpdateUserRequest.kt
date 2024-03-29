package com.avans.rentmycar.model.request

import com.google.gson.annotations.SerializedName


data class UpdateUserRequest (
    @field:SerializedName("id")
    var id: Long,

    @field:SerializedName("firstName")
    var firstName: String,

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
    val address: String?,

    @field:SerializedName("city")
    val city: String?

)