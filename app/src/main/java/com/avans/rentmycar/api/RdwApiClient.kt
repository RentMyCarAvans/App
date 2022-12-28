package com.avans.rentmycar.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.avans.rentmycar.utils.Constant.BASE_URLRDW

private val TAG = "[RMC][RdwApiClient]"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URLRDW)
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RdwApiClient {
        val retrofitService: RdwApiService by lazy {  retrofit.create(RdwApiService::class.java) }
    }
