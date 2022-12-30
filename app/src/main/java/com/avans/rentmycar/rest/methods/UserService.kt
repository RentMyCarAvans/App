package com.avans.rentmycar.rest.methods

import com.avans.rentmycar.rest.ApiClient
import com.avans.rentmycar.rest.request.CreateUpdateUserRequest
import com.avans.rentmycar.rest.request.LoginRequest
import com.avans.rentmycar.rest.request.RegisterUserRequest
import com.avans.rentmycar.rest.response.LoginResponse
import com.avans.rentmycar.rest.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    // TODO: need to implement dynamic urls: https://stackoverflow.com/a/41341057


    @POST("/api/v1/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/v1/users/1")
    suspend fun getUser(): Response<UserResponse>

    @PUT("/api/v1/users/1")
    suspend fun putUser(@Body createUpdateUserRequest: CreateUpdateUserRequest): Response<UserResponse>

    @POST("/api/v1/users")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<UserResponse>

    @Multipart
    @POST("/api/v1/users/profilephoto/1")
    suspend fun uploadProfilePhoto(@Part file: MultipartBody.Part) : Response<UserResponse>

    companion object {
        fun getApi(): UserService? {
            return ApiClient.client?.create(UserService::class.java)
        }
    }
}