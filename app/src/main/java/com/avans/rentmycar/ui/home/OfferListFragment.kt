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
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.databinding.FragmentOfferListBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar

class OfferListFragment : Fragment() {

    private lateinit var _binding: FragmentOfferListBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(inflater, container, false)
        return binding.root
    } // end of onCreateView()

    override fun onStart() {
        super.onStart()

        val offerViewModel = ViewModelProvider(requireActivity())[OfferViewModel::class.java]
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
            binding.progressIndicatorHomeFragment.visibility = View.INVISIBLE
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
        binding.btnBottomSheetModal.setOnClickListener {
            HomeBottomSheetDialogFragment().show(childFragmentManager, "HomeBottomSheetDialogFragment")
        }

    } // end of onStart()

}