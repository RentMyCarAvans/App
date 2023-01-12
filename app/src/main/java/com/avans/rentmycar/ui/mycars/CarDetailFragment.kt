/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.avans.rentmycar.ui.mycars

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentMycarsDetailBinding
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.viewmodel.CarViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.util.*

/**
 * [CarDetailFragment] displays the details of the selected item.
 */
class CarDetailFragment : Fragment() {
    private val TAG = "[RMC][CarDetailFrg]"
    private var _binding: FragmentMycarsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView()")
        return inflater.inflate(R.layout.fragment_mycars_detail,container, false)
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Binds views with the passed in item data.
     */
    fun bindUI(it: List<CarList>) {
        Log.d(TAG, "bindUI() => voertuigsoort: ")
        binding.tvCarBrand.text = it[0].model
        binding.tvCarColor.text = it[0].colorType
        binding.tvCarSeats.text = it[0].numberOfSeats.toString()
        binding.tvCarYear.text = it[0].yearOfManufacture.toString()
        binding.tvCarLicenseplate.text = it[0].licensePlate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")

        val args: CarDetailFragmentArgs by navArgs()

        Log.d(TAG, "onViewCreated() => Checking navArgs Car detail")
        Log.d(TAG, "onViewCreated() => navArgs: " + args.toString())
        Log.d(TAG, "onViewCreated() => Selected item with model " + args.brand + " and id " +args.id)

        // TODO: Replace with binding
        val imageViewCar = view.findViewById<ImageView>(R.id.iv_car_image)
        Glide.with(this).load(args.carimageurl).centerCrop().placeholder(R.drawable.audi).into(imageViewCar);
        view.findViewById<TextView>(R.id.tv_car_brand).text = args.brand.toString()
        view.findViewById<TextView>(R.id.tv_car_year).text = args.year
        view.findViewById<TextView>(R.id.tv_car_licenseplate).text = args.licenseplate
        view.findViewById<TextView>(R.id.tv_car_color).text = args.color
        view.findViewById<TextView>(R.id.tv_car_seats).text = args.numberofseats
        view.findViewById<TextView>(R.id.tv_car_mileage).text = args.mileage
        view.findViewById<TextView>(R.id.tv_car_vehicletype).text = args.vehicletype

        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = args.brand

        val binding = FragmentMycarsDetailBinding.bind(view)
        val carViewModel: CarViewModel by viewModels()

        binding.buttonDeleteCar.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button DELETE clicked. Invoke CarApiService")
            carViewModel.deleteCarById(args.id.toInt())
                Snackbar.make(view, "Car " + args.brand + " with licenseplate " + args.licenseplate + "deleted", Snackbar.LENGTH_LONG)
                    .show()
            Log.d(TAG, "onViewCreated() => Car "+ args.brand + " with licenseplate " + args.licenseplate + " deleted. Return to home")
            findNavController().navigate(R.id.action_carDetailFragment_to_mycars)
        }

        binding.buttonEditCarimageFab.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button EDIT clicked. Go to the Edit fragment")
            val action =
                CarDetailFragmentDirections.actionCarDetailFragmentToEditCarFragment(args.id, args.color, args.brand, args.mileage, args.licenseplate, args.numberofseats, args.year, args.carimageurl, args.vehicletype)
            view.findNavController().navigate(action)
        }

        binding.buttonOfferCar.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button OFFER clicked. Go to the Offer fragment")
            val current = LocalDateTime.now()

            val offerDirection =
                CarDetailFragmentDirections.actionCarDetailFragmentToOfferCarFragment(args.id, null, null, null, args.licenseplate)
            view.findNavController().navigate(offerDirection)
        }
    }
}
