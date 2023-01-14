package com.avans.rentmycar.ui.home

import android.hardware.biometrics.BiometricPrompt
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.BaseApplication
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.AddCarItemBinding
import com.avans.rentmycar.databinding.FragmentBookingDetailBinding
import com.avans.rentmycar.ui.mycars.MyCarsFragmentDirections
import com.avans.rentmycar.utils.BiometricAuthListener
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.utils.showBiometricPrompt
import com.avans.rentmycar.viewmodel.BookingViewModel
import com.avans.rentmycar.viewmodel.RideViewModel
import com.avans.rentmycar.viewmodel.RideViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BookingDetailFragment : Fragment(), BiometricAuthListener {

    // var for binding the add_car_item layout
    private var _binding: FragmentBookingDetailBinding? = null
    private val binding get() = _binding!!

    private var bookingId : Long = 0L

    private val args: BookingDetailFragmentArgs by navArgs()
    private val rideViewModel: RideViewModel by activityViewModels{
        RideViewModelFactory(
            (activity?.application as BaseApplication).database.rideDao()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Booking details"

        val bookingViewModel = ViewModelProvider(requireActivity())[BookingViewModel::class.java]

         bookingId = args.id

        bookingViewModel.getBookingById(bookingId)

        bookingViewModel.bookingSingle.observe(viewLifecycleOwner) { booking ->
            if (booking != null) {
                binding.textviewBookingDetailCarName.text = booking.offer.car.model
                binding.textviewBookingDetailOfferPickuplocation.text = booking.offer.pickupLocation
                binding.textviewBookingDetailOfferDates.text = booking.offer.startDateTime + " - " + booking.offer.endDateTime

                binding.imageviewBookingDetailCarImage.let {
                    Glide.with(this).load(booking.offer.car.image).into(it)
                }
                binding.bookingDetailTitle.text = booking.offer.car.model

            }
        }



        // Cancel booking
        binding.buttonBookingDetailCancelbooking.setOnClickListener {
            Log.d("[BDF]", "Cancel booking with id: $bookingId")
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(getString(R.string.booking_cancel_dialog_title))
                    .setMessage(getString(R.string.booking_cancel_dialog_message))
                    .setNegativeButton(getString(R.string.booking_cancel_dialog_button_negative)) { dialog, which ->
                        Log.d("[BDF]", "Booking with id: $bookingId NOT cancelled")
                    }
                    .setPositiveButton(getString(R.string.booking_cancel_dialog_button_positive)) { dialog, which ->
                        Log.d("[BDF]", "Booking with id: $bookingId will be cancelled")
                        bookingViewModel.cancelBooking(bookingId)
                        bookingViewModel.cancelBookingResult.observe(viewLifecycleOwner) { result ->
                            Log.d("[BDF]", "Booking with id: $bookingId cancelled: $result")
                            if (result) {
                                Toast.makeText(context, "Booking cancelled", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_bookingDetailFragment_to_homeFragment2)
                            }
                        }
                    }
                    .show()
            }

        }


        // start ride button
        binding.buttonBookingDetailStartride.setOnClickListener {
            Log.d("[BDF]", "Start ride button clicked")
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
            else ->       {
                Toast.makeText(requireContext(), "No Biometric Authentication installed. Please add PIN to use this app.", Toast.LENGTH_SHORT)
                    .show()
                doStartRiding()

            }

        }
    }


    override fun onBiometricAuthenticateSuccess(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
        Toast.makeText(requireContext(), "Starting the ride..", Toast.LENGTH_SHORT)
            .show()
        findNavController().navigate(R.id.action_bookingDetailFragment_to_rideFragment)

        doStartRiding()
    }

    private fun saveRideToDB() {
        val args: HomeDetailFragmentArgs by navArgs()
        var location = SessionManager.getDeviceLocation()
        var timeNow = Instant.now().toString()
        rideViewModel.startRide(
            rideId = args.id, startLongitude = location.longitude, startLatitude = location.latitude, startTimeStamp = timeNow
        )
    }

    fun doStartRiding() {
        // build action
        val action = BookingDetailFragmentDirections.actionBookingDetailFragmentToRideFragment(bookingId)

        findNavController().navigate(action)

        saveRideToDB()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}