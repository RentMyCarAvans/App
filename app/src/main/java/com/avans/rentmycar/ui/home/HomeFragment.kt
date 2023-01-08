package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private val viewModel: OfferViewModel by viewModels()

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
        val binding = FragmentHomeBinding.bind(view)

        // Set the title of the actionbar
        // TODO: Can this be done in the activity? Or in the navigation graph?
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.offers_title)

//        // Set the TopButton colors to the default when the fragment is loaded
//        // TODO: Replace this with a MD3 component
//        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
//        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))
//
//        // Get the id of the logged in user so we can use it to get the correct offers and bookings
//        // TODO: Move this to the BookingsFragment for it is not needed here now because we do not filter offers on userId
//        val userId = SessionManager.getUserId(requireContext())
//
//        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected offer
//        val offerAdapter = OfferAdapter(GlideImageLoader(view.context as AppCompatActivity)) { offer ->
//
//            // TODO: Only pass the ID and let the DetailsFragment fetch the data from the Collection
//            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
//                offer.id,
//                offer.car.model,
//                offer.pickupLocation,
//                offer.startDateTime,
//                offer.endDateTime,
//                offer.car.image
//            )
//
//            //TODO: Andere Fragment maken voor de Booking Details
//            findNavController().navigate(action)
//        }
//
//        binding.recyclerviewHomeFragmentOffers.layoutManager =
//            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
//        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter
//
////        viewModel.offerResult.observe(viewLifecycleOwner) {
////            Log.d("[Home] offerResult", "offerResult changed to: $it")
////            offerAdapter.setData(it)
////        }
//
//
//
//        val model = ViewModelProvider(requireActivity())[OfferViewModel::class.java]
//        model.offerCollection.observe(viewLifecycleOwner, Observer {
//            offerAdapter.setData(it)
//            if(it.size == 0) {
//                // TODO: Show a message that there are no offers available
//                // For now, just show a snackbar
//                Snackbar.make(view, "No offers found", Snackbar.LENGTH_LONG).show()
//            }
//
//        })
//
//
//
//
//        // TODO: This should be in the other fragment when it is created
//        viewModel.bookingsResult.observe(viewLifecycleOwner) {
//            Log.d("[Home]", "Bookings: $it")
//            val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
//            Log.d("[Home]", "Offers from my bookings: $offersFromMyBookings")
//            offerAdapter.setData(offersFromMyBookings)
//        }
//
//
//        // === OnClickListeners ===
//
//        // Filter options
//        binding.btnBottomSheetModal.setOnClickListener {
//            HomeBottomSheetDialogFragment().show(childFragmentManager, "HomeBottomSheetDialogFragment")
//        }
//
//        // Topbar navigation button Available cars
//        binding.buttonHomeAvailablecars.setOnClickListener {
//            Log.d("[Home]", "BUTTON Available Cars clicked")
//
//            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
//            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))
//
//            model.getOffers()
//            Log.d("[Home] offrRes", viewModel.offerResult.value.toString())
//            offerAdapter.setData(viewModel.offerResult.value ?: emptyList())
//
//        }
//
//        // Topbar navigation button My bookings
//        binding.buttonHomeMybookings.setOnClickListener {
//
//            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_500))
//            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_200))
//
//            if (userId != null) {
//                viewModel.getBookings(userId)
//
//                // TODO: Maybe create a new layout for My Bookings?
//                val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
//                offerAdapter.setData(offersFromMyBookings)
//            }
//        }
//
//        // Get the offers from the database
//        // TODO: Display a loader while the data is being fetched
//        model.getOffers()
//
    }

    override fun onStart() {
        super.onStart()





        // Set the TopButton colors to the default when the fragment is loaded
        // TODO: Replace this with a MD3 component
        binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
        binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

        // Get the id of the logged in user so we can use it to get the correct offers and bookings
        // TODO: Move this to the BookingsFragment for it is not needed here now because we do not filter offers on userId
        val userId = SessionManager.getUserId(requireContext())

        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected offer
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->

            // TODO: Only pass the ID and let the DetailsFragment fetch the data from the Collection
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id,
                offer.car.model,
                offer.pickupLocation,
                offer.startDateTime,
                offer.endDateTime,
                offer.car.image
            )

            //TODO: Andere Fragment maken voor de Booking Details
            findNavController().navigate(action)
        }

        binding.recyclerviewHomeFragmentOffers.layoutManager =
            LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter

//        viewModel.offerResult.observe(viewLifecycleOwner) {
//            Log.d("[Home] offerResult", "offerResult changed to: $it")
//            offerAdapter.setData(it)
//        }



        val model = ViewModelProvider(requireActivity())[OfferViewModel::class.java]
        model.offerCollection.observe(viewLifecycleOwner, Observer {
//            binding.progressIndicatorHomeFragment.visibility = View.VISIBLE

            offerAdapter.setData(it)
            if(it.size == 0) {
                // TODO: Show a message that there are no offers available
                // For now, just show a snackbar
                view?.let { it1 -> Snackbar.make(it1, "No offers found", Snackbar.LENGTH_LONG).show() }
            }

            binding.progressIndicatorHomeFragment.visibility = View.GONE


        })




        // TODO: This should be in the other fragment when it is created
        viewModel.bookingsResult.observe(viewLifecycleOwner) {
            Log.d("[Home]", "Bookings: $it")
            val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
            Log.d("[Home]", "Offers from my bookings: $offersFromMyBookings")
            offerAdapter.setData(offersFromMyBookings)
        }


        // === OnClickListeners ===

        // Filter options
        binding.btnBottomSheetModal.setOnClickListener {
            HomeBottomSheetDialogFragment().show(childFragmentManager, "HomeBottomSheetDialogFragment")
        }

        // Topbar navigation button Available cars
        binding.buttonHomeAvailablecars.setOnClickListener {
            Log.d("[Home]", "BUTTON Available Cars clicked")

            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_200))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_500))

            model.getOffers()
            Log.d("[Home] offrRes", viewModel.offerResult.value.toString())
            offerAdapter.setData(viewModel.offerResult.value ?: emptyList())

        }

        // Topbar navigation button My bookings
        binding.buttonHomeMybookings.setOnClickListener {

            binding.buttonHomeAvailablecars.setBackgroundColor(resources.getColor(R.color.blue_500))
            binding.buttonHomeMybookings.setBackgroundColor(resources.getColor(R.color.blue_200))

            if (userId != null) {
                viewModel.getBookings(userId)

                // TODO: Maybe create a new layout for My Bookings?
                val offersFromMyBookings = viewModel.bookingsResult.value?.map { it.offer } ?: emptyList()
                offerAdapter.setData(offersFromMyBookings)
            }
        }

        // Get the offers from the database if the devicelocation is available
        SessionManager.deviceLocationHasBeenSet.observe(viewLifecycleOwner, Observer {
            if(SessionManager.deviceLocationHasBeenSet.value == false) {
                Log.w("[Home] SM Deviceloc", "Device location has not been set yet, so we will do that now")
                // TODO: Display a loader while the data is being fetched
                binding.progressIndicatorHomeFragment.visibility = View.VISIBLE

            } else {
                Log.i("[Home] SM Deviceloc", "Device location has been set, so we can start!")
                model.getOffers()
                // TODO: Disable Loader after offers have been fetched
            }
        })
//        model.getOffers()

    }


}
