package com.avans.rentmycar.rest


import com.avans.rentmycar.model.OfferList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/api/v1/offers/")
    fun fetchOffers(@Query("tagged") tags: String): Call<OfferList>
}
