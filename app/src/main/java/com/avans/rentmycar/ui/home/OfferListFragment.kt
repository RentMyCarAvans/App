package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentOfferListBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar

class OfferListFragment : Fragment() {

    private lateinit var _binding: FragmentOfferListBinding
    private val binding get() = _binding

    private lateinit var offerViewModel: OfferViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferListBinding.inflate(inflater, container, false)
        offerViewModel = ViewModelProvider(requireActivity())[OfferViewModel::class.java]

        return binding.root
    } // end of onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        offerViewModel.setCheckboxFuelTypeIceFilter(SessionManager.getBoolean(context = requireContext(), "fuelTypeIceFilter") ?: true)
        offerViewModel.setCheckboxFuelTypeBevFilter(SessionManager.getBoolean(context = requireContext(), "fuelTypeBevFilter") ?: true)
        offerViewModel.setCheckboxFuelTypeFcevFilter(SessionManager.getBoolean(context = requireContext(), "fuelTypeFcevFilter") ?: true)
        offerViewModel.setCheckboxShowOwnCarsFilter(SessionManager.getBoolean(context = requireContext(), "showOwnCarsFilter") ?: true)
        offerViewModel.setNumberOfSeatsFilter(SessionManager.getFloat(context = requireContext(), "numberOfSeatsFilter")?.toInt() ?: 4)
        offerViewModel.setMaxDistanceInKmFilter(SessionManager.getFloat(context = requireContext(), "maxDistanceInKmFilter") ?: 75f)
    }

    override fun onStart() {
        super.onStart()

        offerViewModel.currentUserId = SessionManager.getUserId(context = requireContext())

        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id
            )
            findNavController().navigate(action)
        }

        binding.recyclerviewFragmentofferlistOffers.layoutManager =
            LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewFragmentofferlistOffers.adapter = offerAdapter

        // Making sure the offerData is up to date
        offerViewModel.offerCollection.observe(viewLifecycleOwner) {
            Log.d("[OLF] model.offerColl", it.toString())
            offerAdapter.setData(it)
            if (it.isEmpty()) {
                Log.d("[OLF]", "No offers found")
                // TODO: Show a text message that there are no offers available. For now, just show a snackbar
                view?.let { it1 ->
                    Snackbar.make(it1, "No offers found", Snackbar.LENGTH_LONG).show()
                }
            }
        }


        // Get the offers from the database. With location if the devicelocation is available, else with mock location
        SessionManager.deviceLocationHasBeenSet.observe(viewLifecycleOwner) {
            if (SessionManager.locationPermissionHasBeenGranted.value == false && SessionManager.deviceLocationHasBeenSet.value == false) {
                Log.d(
                    "[Home] DeviceLocation",
                    "Location permission has not been granted. Setting mock location"
                )
                SessionManager.setDeviceLocation(LatLng(51.925959, 3.9226572))
            } else if (SessionManager.deviceLocationHasBeenSet.value == false && SessionManager.locationPermissionHasBeenGranted.value == true) {
                Log.w(
                    "[Home] SM Deviceloc",
                    "Device location has not been set yet, so we wait for that"
                )
                binding.progressIndicatorHomeFragment.visibility = View.VISIBLE
            } else {
                Log.i("[Home] SM Deviceloc", "Device location has been set, so we can start!")
        offerViewModel.getOffers()

            }
        }

        // Filter options
        binding.buttonOfferlistFilter.setOnClickListener {
            HomeBottomSheetDialogFragment().show(childFragmentManager, "HomeBottomSheetDialogFragment")
        }

        binding.buttonOfferlistClearfilter.setOnClickListener{
            SessionManager.saveBoolean(context = requireContext(), "fuelTypeIceFilter", true)
            SessionManager.saveBoolean(context = requireContext(), "fuelTypeBevFilter", true)
            SessionManager.saveBoolean(context = requireContext(), "fuelTypeFcevFilter", true)
            SessionManager.saveBoolean(context = requireContext(), "showOwnCarsFilter", true)
            SessionManager.saveFloat(context = requireContext(), "numberOfSeatsFilter", 2f)
            SessionManager.saveFloat(context = requireContext(), "maxDistanceInKmFilter", 75f)
            offerViewModel.clearFilter()
        }



        offerViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressIndicatorHomeFragment.visibility = View.VISIBLE
            } else {
                binding.progressIndicatorHomeFragment.visibility = View.GONE
            }
        }

    } // end of onStart()

}