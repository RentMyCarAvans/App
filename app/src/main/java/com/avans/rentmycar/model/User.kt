package com.avans.rentmycar.model

data class User(
    val admin: Boolean,
    val adult: Boolean,
    val bonusPoints: Int,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val lastName: String
)