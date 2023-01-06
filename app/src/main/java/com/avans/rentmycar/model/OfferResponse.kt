package com.avans.rentmycar.model

data class CreateOfferResponse(
	val data: OfferData,
	val message: String,
	val status: Int
)

data class OfferResponse(
	val data: Collection<OfferData>,
	val message: String,
	val status: Int
)

data class OfferData(
	val id: Long,
	val startDateTime: String,
	val endDateTime: String,
	val pickupLocation: String,
	val car: OfferCarData
)

data class OfferCarData(
	val type: String,
	val id: Long,
	val licensePlate: String,
	val yearOfManufacture: Int,
	val model: String,
	val colorType: String,
	val mileage: Int,
	val numberOfSeats: Int,
	val image: String,
	val vehicleType: String,
	val createdAt: String,
	val updatedAt: String,
	val owner: OfferUserData
)

data class OfferUserData(
	val id: Long,
	val email: String,
	val firstName: String,
	val lastName: String,
	val telephone: String,
	val address: String,
	val city: String,
	val isVerifiedUser: Boolean,
	val dateOfBirth: String,
	val bonusPoints: Int,
	val profileImageUrl: String,
	val adult: Boolean,
	val admin: Boolean
)
