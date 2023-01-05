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

    // Declare viewbinding
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    // CurrentCall is the current call to the API, which influences the UI
    // TODO: Check if this is still needed
    private var currentCall = 0;

    // Declare viewmodel
    private val viewModel: OfferViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //show actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // TODO: Page should remember last state (Available Cars / My Bookings) for navigating back from other pages
        this.currentCall = 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        // Set the TopButton colors to the default when the fragment is loaded
        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

        // Get the id of the logged in user so we can use it to get the correct offers and bookings
        val userId = SessionManager.getUserId(requireContext())

        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected offer
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->

            var carImage = ""
            if(offer.car.image == null) {
                carImage = "http://placekitten.com/500/500"
            } else {
                carImage = offer.car.image
            }

            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id,
                offer.car.model,
                offer.pickupLocation,
                offer.startDateTime,
                offer.endDateTime,
                carImage,

            )
            //TODO: Andere Fragment maken voor de Booking Details
            findNavController().navigate(action)
        }


//        // TODO: This test with BookingAdapter should be removed if not needed
//        val bookingAdapter = BookingAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { booking ->
//            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
//                booking.id,
//                booking.offer.car.model,
//                booking.offer.pickupLocation,
//                booking.offer.startDateTime,
//                booking.offer.endDateTime,
//                "http://placekitten.com/400/400"
//            )
//            findNavController().navigate(action)
//        }

        binding.recyclerviewHomeFragmentOffers.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter

        viewModel.offerResult.observe(viewLifecycleOwner) {
            offerAdapter.setData(it)
        }



        //TODO: Zorgen dat de lijst herlaadt na klikken op de knop

        binding.buttonHomeAvailablecars.setOnClickListener {
            Log.d("[Home]", "BUTTON Available Cars clicked")
            this.currentCall = 0

            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

            viewModel.getOffers()
            Log.d("[Home] offrRes", viewModel.offerResult.value.toString())
            offerAdapter.setData(viewModel.offerResult.value ?: emptyList())

        }

        binding.buttonHomeMybookings.setOnClickListener {
            Log.d("[Home]", "BUTTON My Bookings clicked")
            this.currentCall = 1

            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_500))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_200))

            if (userId != null) {
                viewModel.getBookings(userId)
                // TODO: Find out why this doesn't work the first time and returns null, but works the second time
                Log.d("[Home] bkngSres", viewModel.bookingsResult.value.toString())

                // TODO: Maybe create a new layout for My Bookings?
                // Extracting the Offers from My Bookings to display them in the recyclerview
                val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()

                offerAdapter.setData(offersFromMyBookings ?: emptyList())
            }
        }

        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.offers_title)
    }

    override fun onStart() {
        super.onStart()
        Log.d("[Home] Offer", "onStart")
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id,
                offer.car.model,
                offer.pickupLocation,
                offer.startDateTime,
                offer.endDateTime,
                "http://placekitten.com/400/400"
            )
            findNavController().navigate(action)
        }
        viewModel.getOffers()
        offerAdapter.setData(viewModel.offerResult.value ?: emptyList())
    }

}
