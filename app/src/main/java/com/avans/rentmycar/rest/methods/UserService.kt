package com.avans.rentmycar.rest.methods

import com.avans.rentmycar.rest.ApiClient
import com.avans.rentmycar.rest.request.CreateUserRequest
import com.avans.rentmycar.rest.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("/api/v1/users/1")
    suspend fun getUser(): Response<UserResponse>

    @PUT("/api/v1/users/1")
    suspend fun putUser(@Body createUserRequest: CreateUserRequest): Response<UserResponse>

    @POST("/api/v1/users/1")
    suspend fun createUser(@Body createUserRequest: CreateUserRequest): Response<UserResponse>

    @Multipart
    @POST("/api/v1/users/profilephoto")
    suspend fun uploadProfilePhoto(@Part body: MultipartBody.Part) : Response<UserResponse>

    companion object {
        fun getApi(): UserService? {
            return ApiClient.client?.create(UserService::class.java)
        }
    }
}