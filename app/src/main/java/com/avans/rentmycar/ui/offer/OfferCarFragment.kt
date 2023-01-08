package com.avans.rentmycar.ui.offer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.databinding.FragmentOfferBinding

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
}