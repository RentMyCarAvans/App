package com.avans.rentmycar.rest.response

data class UserResponse(
	val data: Data,
	val status: Int
)

data class Data(
	val firstName: String,
	val lastName: String,
	val bonusPoints: Int,
	val admin: Boolean,
	val dateOfBirth: String,
	val id: Int,
	val adult: Boolean,
	val email: String
)

