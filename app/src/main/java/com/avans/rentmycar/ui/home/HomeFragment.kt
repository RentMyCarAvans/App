package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private val viewModel: OfferViewModel by viewModels()
    private val bookingViewModel: BookingViewModel by viewModels()

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
        // TODO: Replace this with a MD3 component
        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

        // Set the title of the actionbar
        // TODO: Can this be done in the activity? Or in the navigation graph?
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.offers_title)
    }

    override fun onStart() {
        super.onStart()

        // Get the id of the logged in user so we can use it to get the correct offers and bookings
        // TODO: Move this to the BookingsFragment for it is not needed here now because we do not filter offers on userId
        val userId = SessionManager.getUserId(requireContext())

        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected offer
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->

            // TODO: Only pass the ID and let the DetailsFragment fetch the data from the Collection
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id
            )

            //TODO: Andere Fragment maken voor de Booking Details
            findNavController().navigate(action)
        }

        binding.recyclerviewHomeFragmentOffers.layoutManager =
            LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter

        val model = ViewModelProvider(requireActivity())[OfferViewModel::class.java]
        model.offerCollection.observe(viewLifecycleOwner) {
        Log.d("[Home] model.offerColl", it.toString())
            offerAdapter.setData(it)
            if (it.isEmpty()) {
                Log.d("[Home]", "No offers found")
                // TODO: Show a text message that there are no offers available. For now, just show a snackbar
                view?.let { it1 ->
                    Snackbar.make(it1, "No offers found", Snackbar.LENGTH_LONG).show()
                }
            }
            binding.progressIndicatorHomeFragment.visibility = View.GONE
        }


        // TODO: This should be in the Bookings fragment when it has been coded
        bookingViewModel.bookingCollection.observe(viewLifecycleOwner) {
            Log.d("[Home]", "Bookings: $it")
            val offersFromMyBookings = bookingViewModel.bookingCollection.value?.map { it.offer } ?: emptyList()
            Log.d("[Home]", "Offers from    my bookings: $offersFromMyBookings")
            // TODO: Convert to BookingAdapter
            offerAdapter.setData(offersFromMyBookings)
        }


        // Filter options
        binding.btnBottomSheetModal.setOnClickListener {
            HomeBottomSheetDialogFragment().show(childFragmentManager, "HomeBottomSheetDialogFragment")
        }

        // Topbar navigation button Available cars
        binding.buttonHomeAvailablecars.setOnClickListener {
            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))
            model.getOffers()
        }

        // Topbar navigation button My bookings
        binding.buttonHomeMybookings.setOnClickListener {
            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_500))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_200))
            if (userId != null) {
                bookingViewModel.getBookings(userId)
                // TODO: Maybe create a new layout for My Bookings?
                val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
                offerAdapter.setData(offersFromMyBookings)
            } else {
                // TODO: Show a text message that there are no bookings available. For now, just show a snackbar
                view?.let { it1 ->
                    Snackbar.make(it1, "No bookings found", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        // Get the offers from the database. With location if the devicelocation is available, else with mock location
        SessionManager.deviceLocationHasBeenSet.observe(viewLifecycleOwner) {
            if (SessionManager.locationPermissionHasBeenGranted.value == false && SessionManager.deviceLocationHasBeenSet.value == false) {
                Log.d("[Home] DeviceLocation", "Location permission has not been granted. Setting mock location")
                SessionManager.setDeviceLocation(LatLng(51.925959, 3.9226572))
            } else if (SessionManager.deviceLocationHasBeenSet.value == false && SessionManager.locationPermissionHasBeenGranted.value == true) {
                Log.w(
                    "[Home] SM Deviceloc",
                    "Device location has not been set yet, so we wait for that"
                )
                binding.progressIndicatorHomeFragment.visibility = View.VISIBLE
            } else {
                Log.i("[Home] SM Deviceloc", "Device location has been set, so we can start!")
                model.getOffers()
            }
        }

    }

}
