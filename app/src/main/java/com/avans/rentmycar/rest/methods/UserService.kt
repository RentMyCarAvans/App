package com.avans.rentmycar.rest.methods

import com.avans.rentmycar.rest.ApiClient
import com.avans.rentmycar.rest.request.CreateUserRequest
import com.avans.rentmycar.rest.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @GET("/api/v1/users/1")
    suspend fun getUser(): Response<UserResponse>

    @PUT("/api/v1/users/1")
    suspend fun putUser(@Body createUserRequest: CreateUserRequest): Response<UserResponse>

    @POST("/api/v1/users/1")
    suspend fun createUser(@Body createUserRequest: CreateUserRequest): Response<UserResponse>


    companion object {
        fun getApi(): UserService? {
            return ApiClient.client?.create(UserService::class.java)
        }
    }
}