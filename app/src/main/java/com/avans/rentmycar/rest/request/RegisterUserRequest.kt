package com.avans.rentmycar.rest.request

data class RegisterUserRequest(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var dateOfBirth: String
)