package com.avans.rentmycar.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.avans.rentmycar.R
import com.avans.rentmycar.ui.login.LoginFragment

object SessionManager {

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

    fun getUserId(context: Context): Long? {
        return getLong(context, USER_ID)
        }

    fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
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

    fun clearData(context: Context){
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
        // to remove the userId
//        val editor2 = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
//        editor2.clear()
//        editor2.apply()
    }

    fun saveUserId(context: Context, userId: Long) {
        saveLong(context, USER_ID, userId)
    }

    private fun saveLong(context: Context, key: String, value: Long) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(key, value)
        editor.apply()
    }
}