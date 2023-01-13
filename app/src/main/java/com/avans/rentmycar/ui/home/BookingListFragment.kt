package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.adapter.BookingAdapter
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentBookingListBinding
import com.avans.rentmycar.databinding.FragmentOfferListBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.BookingViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.snackbar.Snackbar


class BookingListFragment : Fragment() {

    private lateinit var _binding: FragmentBookingListBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    } // end of onCreateView()


    override fun onStart() {
        super.onStart()

        val userId = SessionManager.getUserId(requireContext())
        val bookingViewModel = ViewModelProvider(requireActivity())[BookingViewModel::class.java]

        val bookingAdapter = BookingAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { booking ->
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                booking.id
            )
            findNavController().navigate(action)
        }

        binding.recyclerviewBookinglistBookings.layoutManager =
            LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewBookinglistBookings.adapter = bookingAdapter

        // Making sure the Booking data is up to date
        bookingViewModel.bookingCollection.observe(viewLifecycleOwner) {
            Log.d("[BLF] model.bookingColl", it.toString())
            bookingAdapter.setData(it)
            if (it.isEmpty()) {
                Log.d("[BLF]", "No bookings found")
                // TODO: Show a text message that there are no bookings available. For now, just show a snackbar
                view?.let { it1 ->
                    Snackbar.make(it1, "No bookings found", Snackbar.LENGTH_LONG).show()
                }
            }
            binding.progressIndicatorBookinglistFragment.visibility = View.INVISIBLE
        }

        bookingViewModel.getBookings(userId)


    } // end of onStart()

}