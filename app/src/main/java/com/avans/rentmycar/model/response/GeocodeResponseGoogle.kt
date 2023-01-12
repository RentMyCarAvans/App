package com.avans.rentmycar.model.response

import com.squareup.moshi.Json

data class GeocodeResponseGoogle(

    @Json(name="results")
	val results: List<ResultsItem?>? = null,

    @Json(name="status")
	val status: String? = null
)

data class Bounds(

    @Json(name="southwest")
	val southwest: Southwest? = null,

    @Json(name="northeast")
	val northeast: Northeast? = null
)

data class Geometry(

    @Json(name="viewport")
	val viewport: Viewport? = null,

    @Json(name="bounds")
	val bounds: Bounds? = null,

    @Json(name="location")
	val location: Location? = null,

    @Json(name="location_type")
	val locationType: String? = null
)

data class Northeast(

	@Json(name="lng")
	val lng: Any? = null,

	@Json(name="lat")
	val lat: Any? = null
)

data class ResultsItem(

    @Json(name="formatted_address")
	val formattedAddress: String? = null,

    @Json(name="types")
	val types: List<String?>? = null,

    @Json(name="geometry")
	val geometry: Geometry? = null,

    @Json(name="address_components")
	val addressComponents: List<AddressComponentsItem?>? = null,

    @Json(name="place_id")
	val placeId: String? = null
)

data class Southwest(

	@Json(name="lng")
	val lng: Any? = null,

	@Json(name="lat")
	val lat: Any? = null
)

data class Viewport(

    @Json(name="southwest")
	val southwest: Southwest? = null,

    @Json(name="northeast")
	val northeast: Northeast? = null
)

data class AddressComponentsItem(

	@Json(name="types")
	val types: List<String?>? = null,

	@Json(name="short_name")
	val shortName: String? = null,

	@Json(name="long_name")
	val longName: String? = null
)

data class Location(

	@Json(name="lng")
	val lng: Double? = null,

	@Json(name="lat")
	val lat: Double? = null
)
