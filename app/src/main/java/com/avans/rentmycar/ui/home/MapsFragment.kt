package com.avans.rentmycar.ui.home

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
//import androidx.lifecycle.viewmodel.CreationExtras.Empty.map
//import androidx.lifecycle.viewmodel.CreationExtras.Empty.map
import com.avans.rentmycar.R
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {


    // Get the current Location of the device using the FusedLocationProviderClient.
    private lateinit var lastLocation: Location





    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }

        locationPermissionRequest.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))

        // Get the current location of the device
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Check if the user has granted permission to access the device location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        lastLocation = location
                        Log.d("[MAPS] lastLocation", lastLocation.toString())
                        val currentLatLng = LatLng(location.latitude, location.longitude)
//                        setMapLocation(currentLatLng.latitude, currentLatLng.longitude)
                        val mapFragment =
                            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                        mapFragment?.getMapAsync { googleMap ->
                            val location = LatLng(currentLatLng.latitude, currentLatLng.longitude)
                            Log.d("[MAPS]", "Location: $location")

                            googleMap.addMarker(MarkerOptions().position(location))
                            // Change the marker icon

                            // Adjust Zoom level
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f))
                        }
                    }
                }
            }
        }








        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    // Create a method to set the location of the map
    // This method will be called from the HomeDetailFragment

    fun setMapLocation(latToSet: Double, lngToSet: Double) {
        Log.d("[MAPS]", "setMapLoc called")
        if (!isAdded()) return
        Log.d("[MAPS]", "setMapLoc called 2")
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync({ googleMap ->
            val location = LatLng(latToSet, lngToSet)
            Log.d("[MAPS]", "Location: $location")

            // remove all other markers
//            googleMap.clear()

            googleMap.addMarker(MarkerOptions().position(location))
//            googleMap.addMarker(MarkerOptions().position(location).icon(
//                BitmapDescriptorFactory.fromResource(R.drawable.car_black_24dp)))
            // Adjust Zoom level
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f))
        })
    }

}