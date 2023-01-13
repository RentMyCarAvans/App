package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.avans.rentmycar.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the TopButton colors to the default when the fragment is loaded
        // TODO: Replace this with a MD3 component (TabNavigation?)
        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_200))
        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_500))

        // Set the title of the actionbar
        // TODO: Can this be done in the activity? Or in the navigation graph?
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(com.avans.rentmycar.R.string.offers_title)
    }

    // Using onStart so the onViewCreated of the MainActivity is called first
    override fun onStart() {
        super.onStart()

        val offerView = OfferListFragment()
        val bookingView = BookingListFragment()

        //Create and add the default fragment, OfferListFragment
        childFragmentManager.beginTransaction().apply {
            // TODO: I can not get this to work with viewbinding :(
            add(com.avans.rentmycar.R.id.framelayout_home, offerView)
            commit()
        }

        // Topbar navigation button Available cars
        binding.buttonHomeAvailablecars.setOnClickListener {
            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_200))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_500))

            childFragmentManager.beginTransaction().apply {
                replace(com.avans.rentmycar.R.id.framelayout_home, offerView)
                commit()
            }
        }

        // Topbar navigation button My bookings
        binding.buttonHomeMybookings.setOnClickListener {
            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_500))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(com.avans.rentmycar.R.color.blue_200))

            childFragmentManager.beginTransaction().apply {
                replace(com.avans.rentmycar.R.id.framelayout_home, bookingView)
                commit()
            }

        }

    }

}
