package com.avans.rentmycar.model

import com.google.gson.annotations.SerializedName


data class Offer (

    @SerializedName("id"             ) var id             : Int?    = null,
    @SerializedName("startDateTime"  ) var startDateTime  : String? = null,
    @SerializedName("endDateTime"    ) var endDateTime    : String? = null,
    @SerializedName("pickupLocation" ) var pickupLocation : String? = null,
//    @SerializedName("car"            ) var car            : Car?    = Car()

)