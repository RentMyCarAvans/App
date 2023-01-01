package com.avans.rentmycar.rest.request

import com.google.gson.annotations.SerializedName

class PasswordResetRequest(
    @SerializedName("email")
    var email: String,
    )

