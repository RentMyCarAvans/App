package com.avans.rentmycar

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avans.rentmycar.databinding.ActivityMainBinding
import com.avans.rentmycar.utils.SessionManager
import com.bumptech.glide.annotation.GlideModule
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


@GlideModule
class MainActivity : AppCompatActivity() {
    private val TAG = "[RMC][MainActivity]"
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf( R.id.homeFragment2,  R.id.mycars, R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // hide the bottommenubar for these fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.introFragment || destination.id == R.id.rideFragment) {

                bottomNavigationView.visibility = View.GONE
            } else {

                bottomNavigationView.visibility = View.VISIBLE
            }
        }


        // ===== Check for permission for the device location =====
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    Log.d("[Main]", "Precise location access granted.")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Log.d("[Main]", "Only approximate location access granted.")
                } else -> {
                // No location access granted.
                // Show snack bar with explanation.
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    "Location access is required to show nearby cars.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()

            }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // ===== Get the device location =====
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "Checking if location permission is granted")
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Location permission is granted")
                SessionManager.setLocationPermissionGranted(true)

                fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null

                ).addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        Log.d("[Main] getCurLoc", "Location: ${location.latitude}, ${location.longitude}")
                        SessionManager.setDeviceLocation(LatLng(location.latitude, location.longitude))
                    } else {
                        Log.e("[Main] getCurLoc", "Location is null")
                    }
                }

            }
        } else {
            Log.w("[Main] Location", "Location permission not granted")
            SessionManager.setLocationPermissionGranted(false)
        }

    }

        override fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp(appBarConfiguration)
        }



}