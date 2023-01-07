package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.databinding.FragmentHomeDetailBinding
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class HomeDetailFragment : Fragment() {

    // Declare viewbinding
    private lateinit var _binding: FragmentHomeDetailBinding
    private val binding get() = _binding

    // TODO: Refactor this page
    var offerLat = 51.5837013
    var offerLng = 4.797106

    val mapFragment = MapsFragment()

    private val viewModel: OfferViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        childFragmentManager.beginTransaction().replace(R.id.frameLayout_home_detail_map, mapFragment).commit()
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val args: HomeDetailFragmentArgs by navArgs()
        val offerId = args.id
        val offerCarModel = args.carmodel
        val offerPickupLocation = args.pickuplocation
        val offerStartDateTime = args.startDateTime
        val offerEndDateTime = args.endDateTime
        val carImageUrl = args.carImageUrl


        viewModel.getGeocodeResponse(offerPickupLocation)

        viewModel.geocodeResult?.observe(viewLifecycleOwner) {
            val geocodeResponse = viewModel.geocodeResult!!.value
            if(geocodeResponse != null) {
                offerLat = geocodeResponse.results?.get(0)?.geometry?.location?.lat!!
                offerLng = geocodeResponse.results.get(0)?.geometry?.location?.lng!!
            }

            if(mapFragment.isAdded) {
                mapFragment.setMapLocation(offerLat, offerLng)
            }

        }

        // Set the offer data
        binding.textviewHomeDetailOfferid.setText("Current OfferId: $offerId")
        binding.textviewHomeDetailCarName.setText(offerCarModel)
        binding.textviewHomeDetailOfferPickuplocation.setText(offerPickupLocation)
        binding.textviewHomeDetailOfferDates.setText("$offerStartDateTime - $offerEndDateTime")


        binding.imageviewHomeDetailCarImage.let {
            Glide.with(this).load(carImageUrl).into(it)
        }

        // Setup Book Button
        binding.buttonHomeDetailBook.setOnClickListener{
            val offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]
            offerViewModel.createBooking(offerId, SessionManager.getUserId(requireContext())!!)

            offerViewModel.createBookingResult.observe(viewLifecycleOwner) { response ->
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

                }
                if(response == null){
                    Snackbar.make(view, "Booking failed", Snackbar.LENGTH_LONG).show()
                    Log.e("[HDF]", "--- BOOKING FAILED ---")
                    } else {
                        Snackbar.make(view, "Booking failed", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = offerCarModel
    }


    override fun onStart() {
        super.onStart()
//        if(mapFragment.isAdded) {
//            mapFragment.setMapLocation(offerLat, offerLng)
//        }
    }

}