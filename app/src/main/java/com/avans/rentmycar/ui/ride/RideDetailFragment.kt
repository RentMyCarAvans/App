package com.avans.rentmycar.ui.ride

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.BaseApplication
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentRideBinding
import com.avans.rentmycar.databinding.FragmentRideDetailBinding
import com.avans.rentmycar.room.Ride
import com.avans.rentmycar.ui.home.HomeDetailFragmentArgs
import com.avans.rentmycar.utils.*
import com.avans.rentmycar.utils.LocationUtils.calculateDistance
import com.avans.rentmycar.viewmodel.RideViewModel
import com.avans.rentmycar.viewmodel.RideViewModelFactory

import java.util.*
import kotlin.random.Random.Default.nextInt


// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class RideDetailFragment : Fragment(R.layout.fragment_ride_detail)  {
    private var _binding: FragmentRideDetailBinding? = null

    private lateinit var ride: Ride
    // init bookingId for global space
    private var bookingId : Long = 0L


    private val binding get() = _binding!!

    private val args: RideDetailFragmentArgs by navArgs()

    private val rideViewModel: RideViewModel by activityViewModels{
        RideViewModelFactory(
            (activity?.application as BaseApplication).database.rideDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRideDetailBinding.inflate(inflater, container, false)



        bookingId = args.id

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get id
        val args: HomeDetailFragmentArgs by navArgs()
        val rideId = args.id

        Log.d("APP_ROB", rideId.toString())
        if (id > 0) {
            //  Observe a ride that is retrieved by id, set the Ride variable,
            rideViewModel.getRide(rideId).observe(this.viewLifecycleOwner) { selectedItem ->
                ride = selectedItem
                bindRide(ride)

            }
        } else {
//            binding.btnEndride.setOnClickListener {
//                endRide()
//            }
        }

        binding.btnContinue.setOnClickListener {
            val navController: NavController =
                findNavController()
            navController.run {
                popBackStack()
                navigate(R.id.home)
            }
        }
    }




    private fun bindRide(ride: Ride) {
        binding.apply{

            binding.textviewBonuspointsValue.text = (100..1337).random().toString() // mock bonuspoints for now
            binding.textviewKmdrivenValue.text = calculateDistance(ride.startLatitude!!, ride.startLongitude!!, ride.endLatitude!!+2, ride.endLongitude!! + 2).toString()
            binding.textviewMaxspeedValue.text = (50..130).random().toString() // mock max speed for now
            binding.textviewTimestartValue.text = ride.startTimeStamp
            binding.textviewEndtime.text = ride.endTimeStamp


        }
    }




    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}