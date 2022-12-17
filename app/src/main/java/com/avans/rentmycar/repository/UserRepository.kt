package com.avans.rentmycar.repository


import com.avans.rentmycar.rest.UserApi
import com.avans.rentmycar.rest.request.LoginRequest
import com.avans.rentmycar.rest.response.LoginResponse
import retrofit2.Response

class UserRepository {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
}