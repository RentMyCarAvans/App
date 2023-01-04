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

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentMycarsDetailBinding
import com.avans.rentmycar.model.CarList
import com.avans.rentmycar.viewmodel.CarViewModel
import com.bumptech.glide.Glide

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
        _binding = FragmentMycarsDetailBinding.inflate(inflater, container, false)

        val carViewModel: CarViewModel by viewModels()

        // Observer for carResponse
        Log.d(TAG, "onViewCreated() => set observer on rdw response ")
        carViewModel.carResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated() => observe set ")
           // bindUI(it)
        }
        // TODO Implement button to view details of my car
        //binding.getBtn.setOnClickListener {
        //    carDetailViewModel.getRdwCarDetails()
        //}
        return binding.root
    }

    /**
     * Binds views with the passed in item data.
     */

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
       // binding.tvCarLicenseplate.text = it[0].data[0].licensePlate


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CarDetailFragmentArgs by navArgs()
        val carId = args.id
        val carModel = args.brand
        val carYear = args.year
        val carNumberOfSeats = args.numberofseats
        val carColor = args.color
        val carMileage = args.mileage
        val carLicensePlate = args.licenseplate
        val carImageUrl = args.carimageurl

        Log.d("[RMC]", "OfferId: " + carId.toString())

        // TODO: Replace with binding
        val imageViewCar = view.findViewById<ImageView>(R.id.car_image)
        Glide.with(this).load(carImageUrl).centerCrop().placeholder(R.drawable.audi).into(imageViewCar);
        view.findViewById<TextView>(R.id.textview_car_title).text = "Title: " + carModel.toString()
        view.findViewById<TextView>(R.id.textview_car_description1).text = "Year: " + carYear
        view.findViewById<TextView>(R.id.textview_car_description2).text = "Licenseplate: " + carLicensePlate


        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = carModel

    }

}
