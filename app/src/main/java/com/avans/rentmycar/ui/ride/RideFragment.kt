package com.avans.rentmycar.ui.ride

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.BaseApplication
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentBookingDetailBinding
import com.avans.rentmycar.databinding.FragmentRideBinding
import com.avans.rentmycar.model.request.CreateUpdateUserRequest
import com.avans.rentmycar.model.response.BaseResponse
import com.avans.rentmycar.room.Ride
import com.avans.rentmycar.ui.home.BookingDetailFragmentArgs
import com.avans.rentmycar.ui.home.BookingDetailFragmentDirections
import com.avans.rentmycar.ui.home.HomeDetailFragmentArgs
import com.avans.rentmycar.utils.*
import com.avans.rentmycar.viewmodel.RideViewModel
import com.avans.rentmycar.viewmodel.RideViewModelFactory
import kotlinx.coroutines.launch
import java.time.Instant

import java.util.*


class RideFragment : Fragment(R.layout.fragment_ride) {
    private var _binding: FragmentRideBinding? = null

    private lateinit var ride: Ride
    // init bookingId for global space
    private var bookingId : Long = 0L

    private val binding get() = _binding!!

    private val args: RideFragmentArgs by navArgs()

    private val rideViewModel: RideViewModel by activityViewModels{
        RideViewModelFactory(
            (activity?.application as BaseApplication).database.rideDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRideBinding.inflate(inflater, container, false)
        bookingId = args.id

        //hide actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get id
        val args: HomeDetailFragmentArgs by navArgs()
        val rideId = args.id

        Log.d("RMC_APP", rideId.toString())
        if (id > 0) {
            //  Observe a ride that is retrieved by id, set the Ride variable,
            rideViewModel.getRide(rideId).observe(this.viewLifecycleOwner) { selectedItem ->
                ride = selectedItem
                bindRide(ride)
            }
        } else {
            binding.btnEndride.setOnClickListener {
                endRide()
            }
        }


    }



    /** end ride fragment
    - save end geolocation + timestamp to Room
    - put api to update ride
    - btn stop RIde navigate to rideDetail and show ride details
     **/

    private fun endRide() {
        showLoading()

        // get id
        val args: HomeDetailFragmentArgs by navArgs()
        val rideId = args.id

        // get current long/lat of device
        var location = SessionManager.getDeviceLocation()

        // get current timedate
        var timeNow = Instant.now().toString()


        rideViewModel.endRide(rideId, startLatitude = ride.startLatitude, startLongitude = ride.startLongitude, startTimeStamp = ride.startTimeStamp, endTimeStamp = timeNow, endLatitude = location.latitude, endLongitude = location.longitude )

        // TODO do api call to update Ride
        updateApi()
        stopLoading()

        // navigate to rideDetailFragment
        val action = RideFragmentDirections.actionRideFragmentToRideDetailFragment(bookingId)

        findNavController().navigate(action)

    }

    private fun updateApi() {
   // TODO
    }

    private fun bindRide(ride: Ride) {
        binding.apply{

            binding.btnEndride.setOnClickListener {
                endRide()
            }
        }
    }

    private fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}