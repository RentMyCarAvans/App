package com.avans.rentmycar.ui.ride

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentRideBinding
import com.avans.rentmycar.databinding.FragmentRideDetailBinding
import com.avans.rentmycar.utils.*

import java.util.*


// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class RideDetailFragment : Fragment(R.layout.fragment_ride_detail)  {
    private var binding: FragmentRideDetailBinding? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRideDetailBinding.bind(view)
    }




    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}