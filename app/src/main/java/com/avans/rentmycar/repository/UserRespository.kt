package com.avans.rentmycar.repository

import android.media.Image
import com.avans.rentmycar.rest.methods.UserService
import com.avans.rentmycar.rest.request.CreateUserRequest
import com.avans.rentmycar.rest.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part


class UserRepository {
    suspend fun getUser(): Response<UserResponse>? {
        return  UserService.getApi()?.getUser()
    }

    suspend fun uploadProfilePhoto(profile_picture: MultipartBody.Part): Response<UserResponse>? {
        return UserService.getApi()?.uploadProfilePhoto(body = profile_picture)
    }

//    suspend fun updateUser(createUserRequest: CreateUserRequest): Response<UserResponse>? {
//        return  UserService.getApi()?.putUser(createUserRequest = CreateUserRequest)
//    }
}