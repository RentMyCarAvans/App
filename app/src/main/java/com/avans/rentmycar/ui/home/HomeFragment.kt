package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.OfferViewModel

class HomeFragment : Fragment() {

    // Declare viewbinding
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    // Declare viewmodel
    private val viewModel: OfferViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //show actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected offer
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id,
                offer.car.model,
                offer.pickupLocation,
                offer.startDateTime,
                offer.endDateTime,
            "http://placekitten.com/400/400"
            )
            findNavController().navigate(action)
        }

        binding.recyclerviewHomeFragmentOffers.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter

        viewModel.offerResult.observe(viewLifecycleOwner) {
            offerAdapter.setData(it)
        }

        // Get all offers and pass them to the adapter
        // TODO: Adjust the API call to get the offers not made by the current user, after that add the id to the call
        // Retrieve the id of the current user, so we can use it to get the offers not made by the current user
        val userId = SessionManager.getUserId(requireContext())
        // Log.d("[Home] Offer", "current userId: $userId")

        viewModel.getOffers()
        offerAdapter.setData(viewModel.offerResult.value ?: emptyList())

        // Set the title of the actionbar
        // TODO: Make this dynamic, change the title depending on the current language
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.offers_title)
    }

    override fun onStart() {
        super.onStart()
        Log.d("[Home] Offer", "onStart")
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->
            val action = HomeFragmentDirections.actionHomeFragment2ToHomeDetailFragment2(
                offer.id,
                offer.car.model,
                offer.pickupLocation,
                offer.startDateTime,
                offer.endDateTime,
                "http://placekitten.com/400/400"
            )
            findNavController().navigate(action)
        }
        viewModel.getOffers()
        offerAdapter.setData(viewModel.offerResult.value ?: emptyList())
    }

}
