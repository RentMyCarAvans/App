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
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentMycarsDetailBinding
import com.avans.rentmycar.model.CarList
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
        Log.d("[RMC]", "onCreateView()")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("[RMC]", "onViewCreated()")
        Log.d("[RMC]", "onViewCreated() => view: " + view.toString())
        val args: CarDetailFragmentArgs by navArgs()
        val carId = args.id
        val carModel = args.brand
        val carYear = args.year
        val carNumberOfSeats = args.numberofseats
        val carColor = args.color
        val carMileage = args.mileage
        val carLicensePlate = args.licenseplate
        val carImageUrl = args.carimageurl

        Log.d("[RMC]", "onViewCreated() => Checking navArgs")
        Log.d("[RMC]", "onViewCreated() => navargs/args.brand: " + args.brand)
        Log.d("[RMC]", "onViewCreated() => navargs/args.id: " + args.id)
        Log.d("[RMC]", "onViewCreated() => navargs/args.year: " + args.year)
        Log.d("[RMC]", "onViewCreated() => navargs/args.numberofseats: " + args.numberofseats)
        Log.d("[RMC]", "onViewCreated() => navargs/args.color: " + args.color)
        Log.d("[RMC]", "onViewCreated() => navargs/args.mileage: " + args.mileage)
        Log.d("[RMC]", "onViewCreated() => navargs/args.licenseplate: " + args.licenseplate)
        Log.d("[RMC]", "onViewCreated() => navargs/args.imageurl: " + args.carimageurl)

        Log.d("[RMC]", "onViewCreated() => Selected item with model " + args.brand + " and id " +args.id)

        // TODO: Replace with binding
        //val imageViewCar = view.findViewById<ImageView>(R.id.car_image)
        //Log.d("[RMC]", "onViewCreated() => imageViewCar: " + imageViewCar)
        //Glide.with(this).load(carImageUrl).centerCrop().placeholder(R.drawable.audi).into(imageViewCar);
        //Log.d("[RMC]", "onViewCreated() => after Glice imageViewCar: " + imageViewCar)
        view.findViewById<TextView>(R.id.tv_car_brand).text = carModel.toString()
        view.findViewById<TextView>(R.id.tv_car_year).text = carYear
        view.findViewById<TextView>(R.id.tv_car_licenseplate).text = carLicensePlate

        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = carModel

    }

}
