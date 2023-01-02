package com.avans.rentmycar.api

import com.avans.rentmycar.rest.ApiClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.avans.rentmycar.utils.Constant.BASE_URLRDW
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

private val TAG = "[RMC][RdwApiClient]"

var mHttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

var mOkHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(mHttpLoggingInterceptor)
    .build()
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
    .client(mOkHttpClient)
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RdwApiClient {
        val retrofitService: RdwApiService by lazy {  retrofit.create(RdwApiService::class.java) }
    }
