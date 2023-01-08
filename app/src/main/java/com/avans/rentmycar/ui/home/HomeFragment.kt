package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private val viewModel: OfferViewModel by viewModels()

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
        val binding = FragmentHomeBinding.bind(view)



        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.offers_title)

        // Set the TopButton colors to the default when the fragment is loaded
        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

        // Get the id of the logged in user so we can use it to get the correct offers and bookings
        val userId = SessionManager.getUserId(requireContext())

        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected offer
        val offerAdapter = OfferAdapter(GlideImageLoader(view.context as AppCompatActivity)) { offer ->

            // TODO: Only pass the ID and let the DetailsFragment fetch the data from the Collection
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id,
                offer.car.model,
                offer.pickupLocation,
                offer.startDateTime,
                offer.endDateTime,
                offer.car.image
            )

            //TODO: Andere Fragment maken voor de Booking Details
            findNavController().navigate(action)
        }

        binding.recyclerviewHomeFragmentOffers.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter

        viewModel.offerResult.observe(viewLifecycleOwner) {
            Log.d("[Home] offerResult", "offerResult changed to: $it")
            offerAdapter.setData(it)
        }

        // Get the offers from the viewModel and pass it to the adapter
        viewModel.offerCollection.observe(viewLifecycleOwner) {
            // TODO: This is the fastest one! But it needs sorting
            Log.d("[Home] offerCollection", "offerCollection changed to: $it")
            offerAdapter.setData(it)
        }

        // Show the filter options
        binding.btnBottomSheetModal.setOnClickListener {
            HomeBottomSheetDialogFragment().show(childFragmentManager, "HomeBottomSheetDialogFragment")
        }


        viewModel.bookingsResult.observe(viewLifecycleOwner) {
            Log.d("[Home]", "Bookings: $it")
            val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
            Log.d("[Home]", "Offers from my bookings: $offersFromMyBookings")
            offerAdapter.setData(offersFromMyBookings)
        }


        binding.buttonHomeAvailablecars.setOnClickListener {
            Log.d("[Home]", "BUTTON Available Cars clicked")

            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

            viewModel.getOffers()
            Log.d("[Home] offrRes", viewModel.offerResult.value.toString())
            offerAdapter.setData(viewModel.offerResult.value ?: emptyList())

        }

        binding.buttonHomeMybookings.setOnClickListener {
//            Log.d("[Home]", "BUTTON My Bookings clicked")

            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_500))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_200))

            if (userId != null) {
                viewModel.getBookings(userId)
                // TODO: Find out why this doesn't work the first time and returns null, but works the second time
                Log.d("[Home] bkngSres", viewModel.bookingsResult.value.toString())

                // TODO: Maybe create a new layout for My Bookings?
                // Extracting the Offers from My Bookings to display them in the recyclerview
                val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()

                offerAdapter.setData(offersFromMyBookings)
            }
        }


    }

//    private fun calculateDistance(location: LatLng): Float {
//        val userLocation = SessionManager.getUserLocation()
//        val userLat = userLocation?.latitude
//        val userLng = userLocation?.longitude
//
//        val distance = FloatArray(2)
//        Location.distanceBetween(
//            userLat!!,
//            userLng!!,
//            location.latitude,
//            location.longitude,
//            distance
//        )
//        return distance[0]
//    }

    override fun onStart() {
        super.onStart()
        // TODO: Page should remember last state (Available Cars / My Bookings) for navigating back from other pages
        Log.d("[Home] onStart", "onStart")

        // TODO: Check if this is needed

//        // TODO: Kan onderstaande op een andere manier? Dit voelt raar
//        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->
//            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
//                offer.id,
//                offer.car.model,
//                offer.pickupLocation,
//                offer.startDateTime,
//                offer.endDateTime,
//                "http://placekitten.com/400/400",
//            )
//            findNavController().navigate(action)
//        }
//        viewModel.getOffers()
//
//        viewModel.offerResult.observe(viewLifecycleOwner) {
//            offerAdapter.setData(it)
//        }

//        offerAdapter.setData(viewModel.offerResult.value ?: emptyList())
    }

}
