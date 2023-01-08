package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // TODO: Set up all the fields here
//    val checkboxBlue = MutableLiveData<Boolean>()

    val checkboxFuelTypeIceFilter = MutableLiveData<Boolean>()
    val checkboxFuelTypeBevFilter = MutableLiveData<Boolean>()
    val checkboxFuelTypeFcevFilter = MutableLiveData<Boolean>()

    val numberOfSeatsFilter = MutableLiveData<Int>()
    val maxDistanceInKmFilter = MutableLiveData<Float>()


    fun setCheckboxFuelTypeIceFilter(boolean: Boolean) {
        Log.d("[HVM] chBevFilter", "storing checkboxFuelTypeIceFilter!: $boolean")
        checkboxFuelTypeIceFilter.value = boolean
    }

    fun setCheckboxFuelTypeBevFilter(boolean: Boolean) {
        Log.d("[HVM] chBevFilter", "storing checkboxFuelTypeBevFilter!: $boolean")
        checkboxFuelTypeBevFilter.value = boolean
    }

    fun setCheckboxFuelTypeFcevFilter(boolean: Boolean) {
        Log.d("[HVM] chBevFilter", "storing checkboxFuelTypeFcevFilter!: $boolean")
        checkboxFuelTypeFcevFilter.value = boolean
    }

    fun setNumberOfSeatsFilter(number: Int) {
        Log.d("[HVM] setNumbOfSeatsF", "storing numberOfSeatsFilter!: $number")
        numberOfSeatsFilter.value = number
    }

    fun seMaxDistanceInKmFilter(number: Float) {
        Log.d("[HVM] seMaxDistanceF", "storing seMaxDistanceInKmFilter!: $number")
        maxDistanceInKmFilter.value = number
    }

}