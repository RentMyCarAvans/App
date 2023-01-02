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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avans.rentmycar.databinding.FragmentMycarsDetailBinding
import com.avans.rentmycar.model.CarResponse

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

        val carDetailViewModel: CarDetailViewModel by viewModels()

        // Observer for carResponse
        Log.d(TAG, "onViewCreated() => set observer on rdw response ")
        carDetailViewModel.carResponse.observe(viewLifecycleOwner) {
            bindUI(it)
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
    fun bindUI(it: List<CarResponse>) {
        Log.d(TAG, "bindUI() => voertuigsoort: ")
        binding.tvCarBrand.text = it[0].status.toString()
        binding.tvCarBrand.text = it[0].data[0].model
        binding.tvCarColor.text = it[0].data[0].colorType
        binding.tvCarSeats.text = it[0].data[0].numberOfSeats.toString()
        binding.tvCarYear.text = it[0].data[0].yearOfManufacture.toString()
        binding.tvCarLicenseplate.text = it[0].data[0].licensePlate


    }
}
