package com.avans.rentmycar.repository

import com.avans.rentmycar.rest.methods.UserService
import com.avans.rentmycar.rest.request.CreateUpdateUserRequest
import com.avans.rentmycar.rest.request.LoginRequest
import com.avans.rentmycar.rest.request.PasswordResetRequest
import com.avans.rentmycar.rest.request.RegisterUserRequest
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.rest.response.LoginResponse
import com.avans.rentmycar.rest.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response


class UserRepository {
    suspend fun getUser(userId: Long): Response<UserResponse>? {

        return UserService.getApi()?.getUser(url = "/api/v1/users/${userId}")
    }

    suspend fun uploadProfilePhoto(profile_picture: MultipartBody.Part): Response<UserResponse>? {
        return UserService.getApi()?.uploadProfilePhoto(file = profile_picture)
    }

    suspend fun updateUser(updatedUser: CreateUpdateUserRequest, userId: Long): Response<UserResponse>? {
        return UserService.getApi()?.putUser(createUpdateUserRequest = updatedUser, url = "/api/v1/users/${userId}")
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return UserService.getApi()?.loginUser(loginRequest)
    }

    suspend fun registerUser(registerUserRequest: RegisterUserRequest): Response<UserResponse>? {
        return UserService.getApi()?.registerUser(registerUserRequest)
    }

    suspend fun forgotPassword(passwordResetRequest: PasswordResetRequest): Response<Any>? {
        return UserService.getApi()?.resetPassword(passwordResetRequest)
    }
}