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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.BaseApplication
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentHomeDetailBinding
import com.avans.rentmycar.utils.BiometricAuthListener
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.utils.showBiometricPrompt
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.avans.rentmycar.viewmodel.RideViewModel
import com.avans.rentmycar.viewmodel.RideViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.time.Instant

class HomeDetailFragment : Fragment(), BiometricAuthListener {

    // Declare viewbinding
    private lateinit var _binding: FragmentHomeDetailBinding
    private val binding get() = _binding

    // TODO: Refactor this page
    var offerLat = 51.5837013
    var offerLng = 4.797106

    val mapFragment = MapsFragment()

    private val viewModel: OfferViewModel by viewModels()

    private val rideViewModel: RideViewModel by activityViewModels{
        RideViewModelFactory(
            (activity?.application as BaseApplication).database.rideDao()
        )
    }
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


        val args: HomeDetailFragmentArgs by navArgs()
        val offerId = args.id
        val offerCarModel = args.carmodel
        val offerPickupLocation = args.pickuplocation
        val offerStartDateTime = args.startDateTime
        val offerEndDateTime = args.endDateTime
        val carImageUrl = args.carImageUrl
        val offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]

        viewModel.getGeocodeResponse(offerPickupLocation)

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

        // Set the offer data
        binding.textviewHomeDetailOfferid.setText("Current OfferId: $offerId")
        binding.textviewHomeDetailCarName.setText(offerCarModel)
        binding.textviewHomeDetailOfferPickuplocation.setText(offerPickupLocation)
        binding.textviewHomeDetailOfferDates.setText("$offerStartDateTime - $offerEndDateTime")


        binding.imageviewHomeDetailCarImage.let {
            Glide.with(this).load(carImageUrl).into(it)
        }

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
                if (response == null) {
                    Snackbar.make(view, "Booking failed", Snackbar.LENGTH_LONG).show()
                    Log.e("[HDF]", "--- BOOKING FAILED ---")
                } else {
                    Snackbar.make(view, "Booking failed", Snackbar.LENGTH_LONG).show()
                }
            }
        }


        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = offerCarModel
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
        Toast.makeText(requireContext(), "Authentication successful. Starting the ride", Toast.LENGTH_SHORT)
            .show()
        findNavController().navigate(R.id.action_homeDetailFragment2_to_rideFragment)

       saveRideToDB()

    }

    private fun saveRideToDB() {
        val args: HomeDetailFragmentArgs by navArgs()
        var location = SessionManager.getDeviceLocation()
        var timeNow = Instant.now().toString()
        rideViewModel.startRide(
        rideId = args.id, startLongitude = location.longitude, startLatitude = location.latitude, startTimeStamp = timeNow
    )
    }

//    override fun onStart() {
//        super.onStart()
////        if(mapFragment.isAdded) {
////            mapFragment.setMapLocation(offerLat, offerLng)
////        }
//    }

}