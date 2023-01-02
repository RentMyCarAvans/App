package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.repository.OfferRepository
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

        // Retreive the id of the current user
        val userId = SessionManager.getUserId(requireContext())
//        Log.d("[Home] Offer", "current userId: $userId")

        val offerRepository = OfferRepository()
        val viewModel = OfferViewModel()

        viewModel.offerResult.observe(viewLifecycleOwner) {
//            Log.d("[Home] vm", it.toString())
            offerAdapter.setData(it)
        }

//        Log.d("[Home] vm-gO", viewModel.getOffers().toString())
        viewModel.getOffers()
        offerAdapter.setData(viewModel.offerResult.value?: emptyList())
//        Log.d("[Home-Offer]", viewModel.getOffers().toString())

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = "Offers"
    }

}
