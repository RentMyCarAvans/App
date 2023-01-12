package com.avans.rentmycar.model.request

import com.google.gson.annotations.SerializedName

class PasswordResetRequest(
    @SerializedName("email")
    var email: String,
    )

