package com.avans.rentmycar.model.response

import com.squareup.moshi.Json

data class DeleteResponse(

	@Json(name="message")
	val message: String? = null,

	@Json(name="status")
	val status: Int? = null
)
