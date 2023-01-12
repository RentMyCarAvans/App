package com.avans.rentmycar.model.response

import com.squareup.moshi.Json

data class GeocodeResponsePositionstack(

	@Json(name="data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@Json(name="continent")
	val continent: String? = null,

	@Json(name="country")
	val country: String? = null,

	@Json(name="latitude")
	val latitude: Double? = 0.0,

	@Json(name="confidence")
	val confidence: Int? = null,

	@Json(name="county")
	val county: String? = null,

	@Json(name="locality")
	val locality: String? = null,

	@Json(name="administrative_area")
	val administrativeArea: Any? = null,

	@Json(name="label")
	val label: String? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="number")
	val number: String? = null,

	@Json(name="country_code")
	val countryCode: String? = null,

	@Json(name="street")
	val street: String? = null,

	@Json(name="neighbourhood")
	val neighbourhood: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="postal_code")
	val postalCode: String? = null,

	@Json(name="region")
	val region: String? = null,

	@Json(name="longitude")
	val longitude: Double? = 0.0,

	@Json(name="region_code")
	val regionCode: String? = null
)
