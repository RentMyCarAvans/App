package com.avans.rentmycar.ui.home

import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentHomeDetailBinding
import com.avans.rentmycar.utils.BiometricAuthListener
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.utils.showBiometricPrompt
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class HomeDetailFragment : Fragment(), BiometricAuthListener {

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
        childFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_home_detail_map, mapFragment).commit()
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookingViewModel = ViewModelProvider(requireActivity())[BookingViewModel::class.java]
        val offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]

        val actionBar = (activity as AppCompatActivity).supportActionBar


        val args: HomeDetailFragmentArgs by navArgs()
        val offerId = args.id

        Log.d("ROB_APP", args.toString())


        // TODO: Get the Offer data from the ViewModel instead of from the navArgs
        offerViewModel.getOfferById(offerId)

        offerViewModel.singleOffer.observe(viewLifecycleOwner) { offer ->
            if (offer != null) {
                binding.textviewHomeDetailCarName.setText(offer.car.model)
                binding.textviewHomeDetailOfferPickuplocation.setText(offer.pickupLocation)
                binding.textviewHomeDetailOfferDates.setText(offer.startDateTime + " - " + offer.endDateTime)

                binding.imageviewHomeDetailCarImage.let {
                    Glide.with(this).load(offer.car.image).into(it)
                }

                actionBar?.title = offer.car.model

                // TODO: Refactor this after changes on API to include LatLng have been Approved
                viewModel.getGeocodeResponse(offer.pickupLocation)
            }
        }


        // TODO: Refactor this after changes on API to include LatLng  have been Approved
        // ===========================================================

//        viewModel.getGeocodeResponse(offerPickupLocation)

        viewModel.geocodeResult?.observe(viewLifecycleOwner) {
            val geocodeResponse = viewModel.geocodeResult!!.value
            if (geocodeResponse != null) {
                offerLat = geocodeResponse.data?.get(0)?.latitude!!
                offerLng = geocodeResponse.data.get(0)?.longitude!!
            }

            if (mapFragment.isAdded) {
                mapFragment.setMapLocation(offerLat, offerLng)
            }

        }

        // ===========================================================


        // Set the offer data
//        binding.textviewHomeDetailCarName.setText(offerCarModel)
//        binding.textviewHomeDetailOfferPickuplocation.setText(offerPickupLocation)
//        binding.textviewHomeDetailOfferDates.setText("$offerStartDateTime - $offerEndDateTime")
//
//
//        binding.imageviewHomeDetailCarImage.let {
//            Glide.with(this).load(carImageUrl).into(it)
//        }

        // start ride button
        binding.buttonHomeDetailStartRide.setOnClickListener {
            showBiometricPrompt(
                activity = requireActivity() as AppCompatActivity,
                listener = this,
                cryptoObject = null,
                allowDeviceCredential = true,
                title = getString(R.string.BiometricPrompt_title),
                description = getString(R.string.BiometricPrompt_description),
                subtitle = ""
            )
        }

        // Setup Book Button
        binding.buttonHomeDetailBook.setOnClickListener {
            bookingViewModel.createBooking(offerId, SessionManager.getUserId(requireContext())!!)

            bookingViewModel.createBookingResult.observe(viewLifecycleOwner) { response ->
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


        // Set the title of the actionbar

    }



    override fun onBiometricAuthenticateError(error: Int, errMsg: String) {
        when (error) {
            BiometricPrompt.BIOMETRIC_ERROR_USER_CANCELED -> {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT)
                    .show()
            }
            BiometricPrompt.BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL -> {
                Toast.makeText(requireContext(), "No device credential", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onBiometricAuthenticateSuccess(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
        Toast.makeText(requireContext(), "Succes", Toast.LENGTH_SHORT)
            .show()
        findNavController().navigate(R.id.action_homeDetailFragment2_to_rideFragment)


    }


}