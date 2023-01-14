package com.avans.rentmycar.ui.ride

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.BaseApplication
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentRideBinding
import com.avans.rentmycar.room.Ride
import com.avans.rentmycar.ui.home.HomeDetailFragmentArgs
import com.avans.rentmycar.utils.*
import com.avans.rentmycar.viewmodel.RideViewModel
import com.avans.rentmycar.viewmodel.RideViewModelFactory
import java.time.Instant

import java.util.*


class RideFragment : Fragment(R.layout.fragment_ride) {
    private var binding: FragmentRideBinding? = null
    private val navigationArgs: RideFragmentArgs by navArgs()

    private lateinit var ride: Ride

    private val rideViewModel: RideViewModel by activityViewModels{
        RideViewModelFactory(
            (activity?.application as BaseApplication).database.rideDao()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRideBinding.bind(view)

        binding!!.btnEndride.setOnClickListener {
            endRide()
        }
    }


    /** end ride fragment
    - save end geolocation + timestamp to Room
    - put api to update ride
    - btn stop RIde navigate to rideDetail and show ride details
     **/

    private fun endRide() {
        // get id
        val args: HomeDetailFragmentArgs by navArgs()
        val rideId = args.id


        //  Observe a ride that is retrieved by id, set the Ride variable,
        rideViewModel.getRide(rideId).observe(this.viewLifecycleOwner) {selectedItem ->
            ride = selectedItem
        }
//        val ride = rideViewModel.getRide(rideId)
//        val startLatitude = ride.startLatitude
//        val startLongitude = ride.value!!.startLongitude

        // get current long/lat of device
        var location = SessionManager.getDeviceLocation()

        // get current timedate
        var timeNow = Instant.now().toString()


        rideViewModel.endRide(rideId, startLatitude = ride.startLatitude, startLongitude = ride.startLongitude, startTimeStamp = ride.startTimeStamp, endTimeStamp = timeNow, endLatitude = location.latitude, endLongitude = location.longitude )

        // TODO do api call
    }



    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }



}