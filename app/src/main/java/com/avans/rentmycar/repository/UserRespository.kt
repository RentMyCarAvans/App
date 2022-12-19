package com.avans.rentmycar.repository

import com.avans.rentmycar.rest.methods.UserService
import com.avans.rentmycar.rest.request.CreateUserRequest
import com.avans.rentmycar.rest.response.UserResponse
import retrofit2.Response


class UserRepository {
    suspend fun getUser(): Response<UserResponse>? {
        return  UserService.getApi()?.getUser()
    }

//    suspend fun updateUser(createUserRequest: CreateUserRequest): Response<UserResponse>? {
//        return  UserService.getApi()?.putUser(createUserRequest = CreateUserRequest)
//    }
}