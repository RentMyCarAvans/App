package com.avans.rentmycar.ui.ride

import android.content.Intent.getIntent
import android.hardware.biometrics.BiometricPrompt
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON
import androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED
import androidx.core.view.isInvisible

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.BaseApplication
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.databinding.FragmentRideBinding
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.room.Ride
import com.avans.rentmycar.utils.SessionManager.clearData
import com.avans.rentmycar.ui.viewmodel.UserViewModel
import com.avans.rentmycar.utils.*
import com.avans.rentmycar.viewmodel.RideViewModel
import com.avans.rentmycar.viewmodel.RideViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate

import java.util.*
import java.util.concurrent.Executor


// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class RideFragment : Fragment(R.layout.fragment_ride)  {
    private var binding: FragmentRideBinding? = null
    private val navigationArgs: RideFragmentArgs by navArgs()

    private val viewModel: RideViewModel by activityViewModels{
        RideViewModelFactory(
            (activity?.application as BaseApplication).database.rideDao()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRideBinding.bind(view)

//        val id = navigationArgs.id
        var ride: Ride? = null
//        if (id > 0) {
//
//            //  Observe a ride that is retrieved by id
//            viewModel.getRide(id).observe(this.viewLifecycleOwner) {selectedItem ->
//                //update ride
//                ride = selectedItem
////                bindRide(forageable)
//            }
//            binding!!.btnEndride.visibility = View.VISIBLE
//            binding!!.btnEndride.setOnClickListener {
//
//                ride?.let { it1 -> endRide(it1) }
//            }
//        } else {
////            binding.saveBtn.setOnClickListener {
////                addForageable()
////            }
//        }
    }

    /**
     * get current geolocation, save to db
          call api to update ride
          button to navigate to rideDetailfragment (statistics)

     **/
//    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//


    @RequiresApi(Build.VERSION_CODES.O)
    private fun endRide(ride: Ride) {
      var locationLatitude: Double = 1337.0
      var locationLongitude: Double = 1338.0
//       fusedLocationClient.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, null).addOnSuccessListener { location: Location? ->
//            if (location != null) {
//                Log.d("RideFragment", "Location: ${location.latitude}, ${location.longitude}")
//                locationLatitude = location.latitude
//                locationLongitude = location.longitude
//            } else {
//                Log.e("RideFragment", "Location is null")
//            }
//        }

        ride.endTimeStamp = Instant.now()
        ride.endLatitude = locationLatitude
        ride.endLongitude = locationLongitude
    Log.d("APP_ROB", ride.toString())
        viewModel.updateRide(ride)
    }

    /** end ride fragment
     - save end geolocation + timestamp to Room
     - put api to update ride
     - btn stop RIde navigate to rideDetail and show ride details
    **/


    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }



}