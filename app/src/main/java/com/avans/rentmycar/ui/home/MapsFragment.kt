package com.avans.rentmycar.ui.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.avans.rentmycar.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsFragment : Fragment() {

    private lateinit var lastLocation: Location
    private val mapBoundsBuilder = LatLngBounds.Builder()

    private val boundsPadding = 150

    var rmcChildFragmentManager: FragmentManager? = null



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


        // TODO Hier moeten dus blijkbaar de markers worden toegevoegd?

        Log.d("[MAPS] OnMapReady", "OnMapReadyCallback")

//        clearMap()

        val myDeviceLocation = LatLng(51.8598771,4.6457478)
        val selectedCarLocation = LatLng(51.8743837,4.1718)

//        addDeviceMarker(myDeviceLocation)
//        mapBoundsBuilder.include(myDeviceLocation)

//        addCarMarker(selectedCarLocation)
//        mapBoundsBuilder.include(selectedCarLocation)

//        val bounds = mapBoundsBuilder.build()
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, boundsPadding))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rmcChildFragmentManager = getChildFragmentManager();
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.clearMap()

        // TODO: This maybe should be in the MainActivity?
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
                    // Show snack bar with explanation.
                    val snackbar = Snackbar.make(
                        view,
                        R.string.location_permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackbar.show()

            }
            }
        }

        // TODO: This belongs in the MainActivity?
        locationPermissionRequest.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Check if the user has granted permission to access the device location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.lastLocation.addOnSuccessListener { deviceLocation: Location? ->
                    if (deviceLocation != null) {
                        lastLocation = deviceLocation
                        Log.d("[MAPS] lastLocation", lastLocation.toString())
                        val currentLatLng = LatLng(deviceLocation.latitude, deviceLocation.longitude)
//                        setMapLocation(currentLatLng.latitude, currentLatLng.longitude)
                        // Check if map has been attached yet

                        val mapFragment =
                            rmcChildFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
                        mapFragment?.getMapAsync { googleMap ->
                            val deviceLocation = LatLng(currentLatLng.latitude, currentLatLng.longitude)
                            Log.d("[MAPS] getMapsAc devloc", "Location: $deviceLocation")

                            googleMap.addMarker(MarkerOptions().position(deviceLocation))

                            addDeviceMarker(deviceLocation)
                            mapBoundsBuilder.include(deviceLocation)
                            val bounds = mapBoundsBuilder.build()
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, boundsPadding))


                        }
                    }
                }
            }
        }








        val mapFragment = rmcChildFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun clearMap() {
        val mapFragment = rmcChildFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            googleMap.clear()
        }
    }

    private fun addDeviceMarker(location: LatLng){
        Log.d("[MAPS] addDeviceMarker", "Location: $location")
        val mapFragment = rmcChildFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            googleMap.addMarker(MarkerOptions().position(location).title("Your location"))
            Log.d("[MAPS] addDeviceMarker", "Marker added to map at $location")
        }
    }

    fun addCarMarker(location: LatLng){
        Log.d("[MAPS] addCarMarker", "Location: $location")
        val mapFragment = rmcChildFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            googleMap.addMarker(MarkerOptions().position(location).title("CAR NAME HERE").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        }
    }



    fun setMapLocation(latToSet: Double, lngToSet: Double, markerTitle: String, pickupLocation: String) {
        Log.d("[MAPS]", "setMapLoc called")
        if (!isAdded) return
        Log.d("[MAPS]", "setMapLoc called 2")
        val mapFragment = rmcChildFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            val location = LatLng(latToSet, lngToSet)
            Log.d("[MAPS] setMapLocation", "Location: $location")

            // remove all other markers
//            googleMap.clear()

            mapBoundsBuilder.include(location)

            val bounds = mapBoundsBuilder.build()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, boundsPadding))


            val myMarkerOptions = MarkerOptions()
                .position(location)
                .title(markerTitle)
                .snippet(pickupLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            val carMarker = googleMap.addMarker(myMarkerOptions)

        }
    }

}