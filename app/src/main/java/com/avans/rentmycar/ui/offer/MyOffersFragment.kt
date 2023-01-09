package com.avans.rentmycar.ui.offer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.FragmentMycarsBinding
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.adapter.CarAdapter
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.databinding.FragmentMyoffersBinding

import com.avans.rentmycar.viewmodel.CarViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel

/**
 * Main fragment displaying details of all cars of the logged in user.
 */
class MyOffersFragment : Fragment() {
    private val TAG = "[RMC][MyOffersFragment]"

    private lateinit var _binding: FragmentMyoffersBinding
    private val binding get() = _binding

    // Declare viewmodel
    private val viewModel: OfferViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(TAG, "onCreateView()")
        _binding = FragmentMyoffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated() ******************************************")

        // Make all the items in the recyclerview clickable, so the user can click on an item and go to the detail page of the selected car
        val offerAdapter = OfferAdapter(GlideImageLoader(view?.context as AppCompatActivity)) { offer ->
            val action = MyOffersFragmentDirections.actionMyOffersFragmentToEditOfferFragment(
                offer.id.toString(),
                offer.startDateTime,
                offer.endDateTime,
                offer.pickupLocation,
                offer.car.model
            )
            Log.d(TAG,"onViewCreated() => Clicked on item with model " + offer.car.model + ". Navigate to detailscreen")
            findNavController().navigate(action)
        }

        binding.listOfferRecyclerView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.listOfferRecyclerView.adapter = offerAdapter

        // Attach an observer on the carReponse list to update the UI automatically when the data
        // changes.
        /*
        viewModel.carResponse.observe(this.viewLifecycleOwner){ cars ->
            cars.let {
                carAdapter.setData(it)
                Log.d(TAG, "onViewCreated => observed carResponse has been triggerd")
            }
        }
         */

        // Get all offers of the logged in user and pass them to the adapter
        val userId = SessionManager.getUserId(requireContext())?.toInt()
        viewModel.getMyOffers((userId!!).toLong())
       // offerAdapter.setData(viewModel.offerResponse.value?: emptyList())

        // Clicklistener for floating action button
        /*
        binding.buttonNewCarFab.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Floating Action Button clicked")
            val bar = (activity as AppCompatActivity).supportActionBar
            bar?.title = "Add a new Car"
            val action = MyCarsFragmentDirections.actionMycarsToCarAddItemFragment()
            this.findNavController().navigate(action)
        }

         */
    }

    companion object {
        fun newInstance(): MyOffersFragment = MyOffersFragment()
    }
}
