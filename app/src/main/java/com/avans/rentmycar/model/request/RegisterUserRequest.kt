package com.avans.rentmycar.model.request

data class RegisterUserRequest(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var dateOfBirth: String
)