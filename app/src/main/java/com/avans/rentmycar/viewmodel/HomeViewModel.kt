package com.avans.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // TODO: Set up all the fields here
    val checkboxBlue = MutableLiveData<Boolean>()
    val numberOfSeatsFilter = MutableLiveData<Int>()



    fun setCheckboxBlue(boolean: Boolean) {
        Log.d("[HVM] setCheckboxBlue", "storing checkboxBlue!: $boolean")
        checkboxBlue.value = boolean
    }

    fun setNumberOfSeatsFilter(number: Int) {
        Log.d("[HVM] setNumbOfSeatsF", "storing numberOfSeatsFilter!: $number")
        numberOfSeatsFilter.value = number
    }

}