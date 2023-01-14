package com.avans.rentmycar.utils

import android.location.Location

object LocationUtils {
        fun calculateDistance(startPointLatitude: Double, startPointLongitude: Double, endPointLatitude: Double,  endpointLongitude: Double): Double {
            val startPoint = Location("locationA")
            startPoint.latitude = startPointLatitude
            startPoint.longitude = startPointLongitude

            val endPoint = Location("locationA")
            endPoint.latitude = endPointLatitude
            endPoint.longitude = endpointLongitude

            return startPoint.distanceTo(endPoint).toDouble()
        }
}