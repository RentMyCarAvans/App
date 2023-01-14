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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentBookingDetailBinding
import com.avans.rentmycar.utils.BiometricAuthListener
import com.avans.rentmycar.utils.showBiometricPrompt
import com.avans.rentmycar.viewmodel.BookingViewModel
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BookingDetailFragment : Fragment(), BiometricAuthListener {

    private lateinit var _binding: FragmentBookingDetailBinding
    private val binding get() = _binding

    private val args: BookingDetailFragmentArgs by navArgs()

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

        val bookingId = args.id

        bookingViewModel.getBookingById(bookingId)

        bookingViewModel.bookingSingle.observe(viewLifecycleOwner) { booking ->
            if (booking != null) {
                binding.textviewBookingDetailCarName.setText(booking.offer.car.model)
                binding.textviewBookingDetailOfferPickuplocation.setText(booking.offer.pickupLocation)
                binding.textviewBookingDetailOfferDates.setText(booking.offer.startDateTime + " - " + booking.offer.endDateTime)

                binding.imageviewBookingDetailCarImage.let {
                    Glide.with(this).load(booking.offer.car.image).into(it)
                }

                binding.bookingDetailTitle.text = booking.offer.car.model

//                actionBar?.title = booking.offer.car.model

            }
        }


        // Cancel booking
        binding.buttonBookingDetailCancelbooking.setOnClickListener {
            Log.d("[BDF]", "Cancel booking with id: $bookingId")
            // TODO: Show dialog to confirm cancel booking
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(getString(R.string.booking_cancel_dialog_title))
                    .setMessage(getString(R.string.booking_cancel_dialog_message))
                    .setNegativeButton(getString(R.string.booking_cancel_dialog_button_negative)) { dialog, which ->
                        // Respond to negative button press
                        Log.d("[BDF]", "Booking with id: $bookingId NOT cancelled")
                    }
                    .setPositiveButton(getString(R.string.booking_cancel_dialog_button_positive)) { dialog, which ->
                        // Respond to positive button press
                        // TODO: Implement cancel booking in BookingRepository
                        Log.d("[BDF]", "Booking with id: $bookingId will be cancelled")
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
        }
    }


    override fun onBiometricAuthenticateSuccess(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
        Toast.makeText(requireContext(), "Succes", Toast.LENGTH_SHORT)
            .show()
        findNavController().navigate(R.id.action_bookingDetailFragment_to_rideFragment)


    }

}