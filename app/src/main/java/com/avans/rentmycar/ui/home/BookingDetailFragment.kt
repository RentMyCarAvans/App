package com.avans.rentmycar.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentBookingDetailBinding
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.snackbar.Snackbar

class BookingDetailFragment : Fragment() {

    // TODO: This was autogenerated. Find out if it is usefull or needed
//    companion object {
//        fun newInstance() = BookingDetailFragment()
//    }

    private lateinit var viewModel: BookingViewModel

    private lateinit var _binding: FragmentBookingDetailBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        val args: BookingDetailFragmentArgs by navArgs()
        val bookingId = args.id

        if(bookingId != null) {
            viewModel.getBookingById(bookingId)
        } else {
            Snackbar.make(binding.root, "No booking found", Snackbar.LENGTH_LONG).show()
            return
        }

        val model = ViewModelProvider(requireActivity())[BookingViewModel::class.java]
        val booking = model.getBookingById(bookingId)

        Log.d("BookingDetailFragment", "Booking: $booking")

    }


    // TODO: This was autogenerated. Find out if it is usefull or needed
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}