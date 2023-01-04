package com.avans.rentmycar.model

import faker.com.fasterxml.jackson.annotation.JsonIdentityInfo

// CarModel is the representation of items you see on the UI (recyclerview screen)
data class CarModel (
    var image: Int,
    var title: String,
    var description1: String,
    var description2: String
    )