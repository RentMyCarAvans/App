package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    // Setup adapter
    private val offerAdapter by lazy {
        OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        binding.recyclerviewHomeFragmentOffers.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerviewHomeFragmentOffers.adapter = offerAdapter

        // Retreive the id of the current user, so we can use it to get the offers not made by the current user
        // TODO: Adjust the API call to get the offers not made by the current user
        val userId = SessionManager.getUserId(requireContext())
//        Log.d("[Home] Offer", "current userId: $userId")

//        val viewModel = OfferViewModel()

        viewModel.offerResult.observe(viewLifecycleOwner) {
            offerAdapter.setData(it)
        }

        // Get all offers and pass them to the adapter
        viewModel.getOffers()
        offerAdapter.setData(viewModel.offerResult.value ?: emptyList())

        // Set the title of the actionbar
        // TODO: Make this dynamic, change the title depending on the current language
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = "Offers"
    }

}
