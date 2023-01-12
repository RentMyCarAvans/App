package com.avans.rentmycar.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.repository.UserRepository
import com.avans.rentmycar.model.request.LoginRequest
import com.avans.rentmycar.model.request.PasswordResetRequest
import com.avans.rentmycar.model.request.RegisterUserRequest
import com.avans.rentmycar.model.response.BaseResponse
import com.avans.rentmycar.model.response.LoginResponse
import com.avans.rentmycar.model.response.UserResponse
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val registerResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val forgotPasswordResult: MutableLiveData<BaseResponse<Any>> = MutableLiveData()

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

    fun registerUser(firstName: String, lastName: String, dateOfBirth: String, email: String, password: String) {
        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val registerUserRequest = RegisterUserRequest(firstName= firstName, lastName = lastName,
                    password = password,
                    email = email, dateOfBirth = dateOfBirth
                )
                val response = userRepo.registerUser(registerUserRequest = registerUserRequest)
                Log.d("APP", response.toString())
                if (response?.code() == 201) {
                    Log.d("APP", "code 201")

                   registerResult.value = BaseResponse.Success(response.body())
                } else {
                    Log.d("APP", "not working this")

                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }    }

    fun forgotPassword(email: String) {
        forgotPasswordResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val passwordResetRequest = PasswordResetRequest(email)
                val response = userRepo.forgotPassword(passwordResetRequest)
                if (response?.code() == 200) {
                    Log.d("RMC_APP", "code 200")
                    forgotPasswordResult.value = BaseResponse.Success(response.body())
                } else {
                    response?.toString()?.let { Log.d("RMC_APP", it) }

                    forgotPasswordResult.value = BaseResponse.Error(response?.body().toString())
                }
            } catch (ex: Exception) {
                ex?.toString()?.let { Log.d("RMC_APP", it) }

                forgotPasswordResult.value = BaseResponse.Error(ex.message)
            }
    }
    }
}