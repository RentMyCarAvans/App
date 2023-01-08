package com.avans.rentmycar.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.avans.rentmycar.R
import com.google.android.gms.maps.model.LatLng

object SessionManager {

    private var deviceLocation: LatLng = LatLng(51.925959,3.9226572) // TODO: Figuer out if the SessionManager is the right place for this
    var deviceLocationHasBeenSet: MutableLiveData<Boolean> = MutableLiveData(false)

    const val USER_TOKEN = "user_token"
    const val USER_ID = "user_id"

    /**
     * Function to save auth token
     */
    fun saveAuthToken(context: Context, token: String) {
        saveString(context, USER_TOKEN, token)
    }

    /**
     * Function to fetch auth token
     */
    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }
    /**
     * Function to get userId
     */
    fun getUserId(context: Context): Long? {
        return getLong(context, USER_ID)
        }

    fun saveUserId(context: Context, userId: Long) {
        saveLong(context, USER_ID, userId)
    }
    /**
     * Helper functions to save data to shared prefs
     */
    fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()

    }

    fun saveLong(context: Context, key: String, value: Long) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun getLong(context: Context, key: String): Long? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getLong(key, 1L )
    }

    /**
     * Function to remove the shared prefs with tag "RMC"
     */
    fun clearData(context: Context){
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    // ===== Device location =====
    fun getDeviceLocation(): LatLng {
        Log.d("[SM] getDeviceLocation", "getDeviceLocation() called")
        return deviceLocation
    }

    fun setDeviceLocation(deviceLocation: LatLng) {
        Log.d("[SM] setDeviceLocation", "setDeviceLocation: $deviceLocation")
        deviceLocationHasBeenSet.value = true
        Log.d("[SM] setDeviceLocation", "deviceLocationHasBeenSet: $deviceLocationHasBeenSet")
        this.deviceLocation = deviceLocation
    }


}