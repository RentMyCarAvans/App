package com.avans.rentmycar.rest.methods

import com.avans.rentmycar.rest.ApiClient
import com.avans.rentmycar.rest.request.CreateUpdateUserRequest
import com.avans.rentmycar.rest.request.LoginRequest
import com.avans.rentmycar.rest.request.PasswordResetRequest
import com.avans.rentmycar.rest.request.RegisterUserRequest
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.rest.response.LoginResponse
import com.avans.rentmycar.rest.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET
    suspend fun getUser(@Url url: String): Response<UserResponse>

    @PUT()
    suspend fun putUser(@Body createUpdateUserRequest: CreateUpdateUserRequest, @Url url: String): Response<UserResponse>

    @POST("/api/v1/users")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<UserResponse>

    @Multipart
    @POST("/api/v1/users/profilephoto/1")
    suspend fun uploadProfilePhoto(@Part file: MultipartBody.Part) : Response<UserResponse>

    @POST("/api/v1/auth/reset")
    suspend fun resetPassword(@Body passwordResetRequest: PasswordResetRequest): Response<Any>

    companion object {
        fun getApi(): UserService? {
            return ApiClient.client?.create(UserService::class.java)
        }
    }
}