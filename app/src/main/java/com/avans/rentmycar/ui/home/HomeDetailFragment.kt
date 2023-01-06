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
import com.avans.rentmycar.api.MapsApiService
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class HomeDetailFragment : Fragment() {

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
        // Add MapsFragment to the map container
        childFragmentManager.beginTransaction().replace(R.id.frameLayout_home_detail_map, mapFragment).commit()
        return inflater.inflate(R.layout.fragment_home_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = SessionManager.getUserId(requireContext())

        val args: HomeDetailFragmentArgs by navArgs()
        val offerId = args.id
        val offerCarModel = args.carmodel
        val offerPickupLocation = args.pickuplocation
        val offerStartDateTime = args.startDateTime
        val offerEndDateTime = args.endDateTime
        val carImageUrl = args.carImageUrl

        if(args.lat != null && args.lng != null) {
            offerLat = args.lat.toDouble()
            offerLng = args.lng.toDouble()
        }

        viewModel.getGeocodeResponse(offerPickupLocation)
//        val geocodeResponse = viewModel.geocodeResult.value

        viewModel.geocodeResult?.observe(viewLifecycleOwner) {
            val geocodeResponse = viewModel.geocodeResult!!.value
            if(geocodeResponse != null) {
                offerLat = geocodeResponse.results?.get(0)?.geometry?.location?.lat!!
                offerLng = geocodeResponse.results?.get(0)?.geometry?.location?.lng!!

            }
            Log.d("[HDF]", "Geocode response: geocodeResponse = $geocodeResponse")

            if(mapFragment.isAdded) {
                Log.d("[HDF]", "Map is added")
                mapFragment.setMapLocation(offerLat, offerLng)
            }

        }



        Log.d("[HDF]", "OfferId: $offerId")

        // TODO: Replace with binding
        val imageViewCar = view.findViewById<ImageView>(R.id.imageview_home_detail_car_image)
        Glide.with(this).load(carImageUrl).centerCrop().placeholder(R.drawable.audi).into(imageViewCar)
        view.findViewById<TextView>(R.id.textview_home_detail_offerid).text = "OfferId: $offerId"
        view.findViewById<TextView>(R.id.textview_home_detail_car_name).text = offerCarModel
        view.findViewById<TextView>(R.id.textview_home_detail_offer_pickuplocation).text = offerPickupLocation
        view.findViewById<TextView>(R.id.textview_home_detail_offer_dates).text =
            "$offerStartDateTime - $offerEndDateTime"



        // Setup Book Button
        view.findViewById<TextView>(R.id.button_home_detail_book).setOnClickListener {

            Log.d("[HDF]", "Book Offer " + offerId + " for user with id " + userId.toString())

            val offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]
            offerViewModel.createBooking(offerId, userId!!)

            // TODO: Check the response and show a message to the user depending on the response
            // TODO: Navigate back to HomeFragment ONLY if successfull

            offerViewModel.bookingResult.observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    Log.d("[HDF]", "Response: $response")
                    Log.d("[HDF]", "Resp.status: " + response.status)
                    if (response.status == 201) {
                        // Show snackbar success message
                        Snackbar.make(view, "Booking created successfully", Snackbar.LENGTH_LONG)
                            .show()
                        Log.d("[HDF]", "Navigating back to HomeFragment")
                        val action =
                            HomeDetailFragmentDirections.actionHomeDetailFragmentToHomeFragment()
                        view.findNavController().navigate(action)
                    } else {
                        Snackbar.make(view, "Booking failed", Snackbar.LENGTH_LONG).show()
                        Log.e("[HDF]", "--- BOOKING FAILED ---")
                    }
                }
            }


            Log.d("[HDF]", "bookingResult.value: " + offerViewModel.bookingResult.value)





        }



        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = offerCarModel

    }


    override fun onStart() {
        super.onStart()
        // Set the location of the map





        if(mapFragment.isAdded) {
            Log.d("[HDF]", "Map is added")
            mapFragment.setMapLocation(offerLat, offerLng)
        }
    }



}