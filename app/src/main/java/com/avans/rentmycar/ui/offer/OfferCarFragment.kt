package com.avans.rentmycar.ui.offer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentOfferBinding
import com.avans.rentmycar.viewmodel.CarViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [OfferCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OfferCarFragment : Fragment() {
    private val TAG = "[RMC][OfferCarFrg]"

    // var for binding the fragment_edit_car layout
    private var _binding: FragmentOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView()")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: OfferCarFragmentArgs by navArgs()
        Log.d("[RMC]", "onViewCreated() => Checking navArgs Car offer")
        Log.d("[RMC]", "onViewCreated() => navArgs: " + args.toString())

        bindUI(args)

        binding.buttonCarSave.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button SAVE clicked. Invoke CarApiService")
            // TODO Add validation for all inputfield
            createOffer()
            Snackbar.make(view, "Offer on car with licenseplate " + args.licenseplate + " created", Snackbar.LENGTH_LONG)
                .show()
            Log.d(TAG, "onViewCreated() => Offer made for car " + args.licenseplate + ". Return to home")
            findNavController().navigate(R.id.action_offerCarFragment_to_mycars)
        }
    }

    /**
     * Binds views with the passed in item data.
     */
    fun bindUI(args: OfferCarFragmentArgs) {
        Log.d(TAG, "bindUI()")
        binding.txtInputOfferCarStartDate.setText(args.startdate)
        binding.txtInputOfferCarEndDate.setText(args.enddate)
        binding.txtInputOfferCarLocation.setText(args.location)
        binding.textviewOfferCarLicensePlate.text = (args.licenseplate)
    }

    private fun createOffer() {
        Log.d(TAG, "createOffer()")
        val carViewModel: CarViewModel by viewModels()

    }
}