package com.avans.rentmycar.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.repository.UserRepository
import com.avans.rentmycar.rest.request.LoginRequest
import com.avans.rentmycar.rest.request.RegisterUserRequest
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.rest.response.LoginResponse
import com.avans.rentmycar.rest.response.UserResponse
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val registerResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()

    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                Log.d("APP", response.toString())
                if (response?.code() == 200) {
                    Log.d("APP", "code 200")

                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    Log.d("APP", "not working this")

                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val registerUserRequest = RegisterUserRequest(firstName= firstName, lastName = lastName,
                    password = password,
                    email = email,
                )
                val response = userRepo.registerUser(registerUserRequest = registerUserRequest)
                Log.d("APP", response.toString())
                if (response?.code() == 200) {
                    Log.d("APP", "code 200")

                   registerResult.value = BaseResponse.Success(response.body())
                } else {
                    Log.d("APP", "not working this")

                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }    }
}