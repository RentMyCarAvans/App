package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.BookingViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.gms.maps.model.LatLng


class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

//    private val viewModel: OfferViewModel by viewModels()
//    private val bookingViewModel: BookingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the TopButton colors to the default when the fragment is loaded
        // TODO: Replace this with a MD3 component (TabNavigation?)
        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_200))
        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_500))

        // Set the title of the actionbar
        // TODO: Can this be done in the activity? Or in the navigation graph?
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(com.avans.rentmycar.R.string.offers_title)
    }

    // Using onStart so the onViewCreated of the MainActivity is called first
    override fun onStart() {
        super.onStart()

        val offerView = OfferListFragment()
        val bookingView = BookingListFragment()


        //Create and add the default fragment, OfferListFragment
        childFragmentManager.beginTransaction().apply {
            // TODO: I can not get this to work with viewbinding :(
            add(com.avans.rentmycar.R.id.framelayout_home, offerView)
            commit()
        }


        // Get the id of the logged in user so we can use it to get the correct offers and bookings
//        val userId = SessionManager.getUserId(requireContext())


        // Topbar navigation button Available cars
        binding.buttonHomeAvailablecars.setOnClickListener {
            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_200))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_500))
            // TODO: Replace the displaying fragment with the OfferListFragment
//            model.getOffers()
            //Create and add the fragment
//            val offerView = OfferListFragment()
            childFragmentManager.beginTransaction().apply {
                replace(com.avans.rentmycar.R.id.framelayout_home, offerView)
                commit()
            }
        }

        // Topbar navigation button My bookings
        binding.buttonHomeMybookings.setOnClickListener {
            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_500))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_200))

            childFragmentManager.beginTransaction().apply {
                replace(com.avans.rentmycar.R.id.framelayout_home, bookingView)
                commit()
            }
//            if (userId != null) {
//                bookingViewModel.getBookings()
//                //Create and add the fragment
//                childFragmentManager.beginTransaction().apply {
//                    replace(com.avans.rentmycar.R.id.framelayout_home, bookingView)
//                    commit()
//                }
//
//
//                // TODO: Maybe create a new layout for My Bookings?
////                val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
////                offerAdapter.setData(offersFromMyBookings)
//                // TODO: Replace the displaying fragment with the BookingListFragment
//
//            } else {
//                // TODO: Show a text message that there are no bookings available. For now, just show a snackbar
//                view?.let { it1 ->
//                    Snackbar.make(it1, "No bookings found", Snackbar.LENGTH_LONG).show()
//                }
//            }
        }

        // Get the offers from the database. With location if the devicelocation is available, else with mock location
        SessionManager.deviceLocationHasBeenSet.observe(viewLifecycleOwner) {
            if (SessionManager.locationPermissionHasBeenGranted.value == false && SessionManager.deviceLocationHasBeenSet.value == false) {
                Log.d(
                    "[Home] DeviceLocation",
                    "Location permission has not been granted. Setting mock location"
                )
                SessionManager.setDeviceLocation(LatLng(51.925959, 3.9226572))
            } else if (SessionManager.deviceLocationHasBeenSet.value == false && SessionManager.locationPermissionHasBeenGranted.value == true) {
                Log.w(
                    "[Home] SM Deviceloc",
                    "Device location has not been set yet, so we wait for that"
                )
//                binding.progressIndicatorHomeFragment.visibility = View.VISIBLE
            } else {
                Log.i("[Home] SM Deviceloc", "Device location has been set, so we can start!")
//                model.getOffers()
            }
        }

    }

}
