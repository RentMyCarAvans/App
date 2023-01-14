package com.avans.rentmycar.model

import com.squareup.moshi.Json

data class GeocodeResponsePositionstackReverse(

	@Json(name="data")
	val data: List<ReverseDataItem?>? = null
)

data class ReverseDataItem(

	@Json(name="continent")
	val continent: String? = null,

	@Json(name="country")
	val country: String? = null,

	@Json(name="distance")
	val distance: Any? = null,

	@Json(name="latitude")
	val latitude: Any? = null,

	@Json(name="confidence")
	val confidence: Any? = null,

	@Json(name="county")
	val county: Any? = null,

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
	val longitude: Any? = null,

	@Json(name="region_code")
	val regionCode: String? = null
)
