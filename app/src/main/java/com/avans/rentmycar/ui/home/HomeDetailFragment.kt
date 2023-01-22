package com.avans.rentmycar.ui.home

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.databinding.FragmentHomeDetailBinding
import com.avans.rentmycar.utils.DateTimeConverter
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.BookingViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class HomeDetailFragment : Fragment() {

    private lateinit var _binding: FragmentHomeDetailBinding
    private val binding get() = _binding

    val args: HomeDetailFragmentArgs by navArgs()

    val mapFragment = MapsFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        childFragmentManager.beginTransaction()
            .replace(com.avans.rentmycar.R.id.frameLayout_home_detail_map, mapFragment).commit()
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookingViewModel = ViewModelProvider(requireActivity())[BookingViewModel::class.java]
        val offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]

        val actionBar = (activity as AppCompatActivity).supportActionBar

        val offerId = args.id

        offerViewModel.getOfferById(offerId)

        offerViewModel.singleOffer.observe(viewLifecycleOwner) { offer ->
            if (offer != null) {

                val startDate = DateTimeConverter.convertDatabaseDateTimeToReadableDateTime(offer.startDateTime)
                val endDate = DateTimeConverter.convertDatabaseDateTimeToReadableDateTime(offer.endDateTime)

                binding.textviewHomeDetailCarName.setText(offer.car.model)
                binding.textviewHomeDetailOfferPickuplocation.setText(offer.pickupLocation)
                binding.textviewHomeDetailOfferDates.setText(startDate + " - " + endDate)


                val carImageUrl = offer.car.image
                if(carImageUrl.isNullOrEmpty()) {
                    binding.imageviewHomeDetailCarImage.let {
                        Glide.with(this).load("https://www.thecarwiz.com/images/listing_vehicle_placeholder.jpg").into(it)
                    }
                } else {
                    binding.imageviewHomeDetailCarImage.let {
                        Glide.with(this).load(carImageUrl).into(it)
                    }
                }


                // If the current user is the owner of the offer, change the button text to "Cancel this offer" and add a click listener to delete the offer
                if(offer.car.user.id == SessionManager.getUserId(requireContext())) {
                    binding.buttonHomeDetailBook.text = getString(com.avans.rentmycar.R.string.cancel_this_booking)
                    binding.buttonHomeDetailBook.setOnClickListener {
                        offerViewModel.cancelOffer(offer.id)
                        Snackbar.make(view, getString(com.avans.rentmycar.R.string.offer_cancelled), Snackbar.LENGTH_SHORT).show()
                        view.findNavController().navigate(com.avans.rentmycar.R.id.action_homeDetailFragment_to_homeFragment)
                    }
                }

                actionBar?.title = offer.car.model

                if (mapFragment.isAdded) {
                    mapFragment.setMapLocation(offer.pickupLocationLatitude, offer.pickupLocationLongitude, offer.car.model, offer.pickupLocation)
                }

            }
        }



        // Setup Book Button
        binding.buttonHomeDetailBook.setOnClickListener {
            bookingViewModel.createBooking(offerId, SessionManager.getUserId(requireContext())!!)

            bookingViewModel.createBookingResult.observe(viewLifecycleOwner) { response ->
                // TODO: Check if value of response can be !null
                if (response != null) {
                    Log.d("[HDF] Response", "Response: $response")
                    Log.d("[HDF] Resp.status", "Resp.status: " + response.status)
                    if (response.status == 201) {
                        Snackbar.make(view, "Booking created successfully", Snackbar.LENGTH_LONG)
                            .show()
                        val action =
                            HomeDetailFragmentDirections.actionHomeDetailFragmentToHomeFragment()
                        view.findNavController().navigate(action)
                    }

                } else {
                    Log.d("[HDF] Response", "Response: $response")
                    Snackbar.make(view, "Booking creation failed", Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }

}