package com.avans.rentmycar.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.repository.UserRepository
import com.avans.rentmycar.rest.request.CreateUpdateUserRequest
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.rest.response.UserResponse
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val userResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val userRequest: MutableLiveData<BaseResponse<CreateUpdateUserRequest>> = MutableLiveData()

    fun getUser(email: String, pwd: String) {

        userResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                Log.v("APP_RMC", "launched, now trying api  call")
                val response = userRepo.getUser()
                if (response?.code() == 200) {
                    userResult.value = BaseResponse.Success(response.body())
                } else {
                    userResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                userResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun setUser(createUpdateUserRequest: CreateUpdateUserRequest) {
        userRequest.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = userRepo.updateUser(createUpdateUserRequest)
                if (request?.code() == 200) {
                    userRequest.value = BaseResponse.Success()
                } else {
                    userRequest.value = BaseResponse.Error(request?.message())
                }
            } catch (ex: Exception) {
                userRequest.value = BaseResponse.Error(ex.message)
            }

        }
    }
}