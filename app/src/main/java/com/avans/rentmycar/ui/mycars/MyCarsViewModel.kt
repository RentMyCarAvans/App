package com.avans.rentmycar.ui.mycars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyCarsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is My cars Fragment"
    }
    val text: LiveData<String> = _text
}