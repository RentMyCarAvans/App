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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.AddCarItemBinding
import com.avans.rentmycar.model.RdwResponse
import com.avans.rentmycar.model.RdwResponseItem
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.FieldValidation
import com.avans.rentmycar.viewmodel.CarViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

/**
 * [CarAddItemFragment] Add a new car by licenseplate.
 */
class CarAddItemFragment : Fragment() {
    private val TAG = "[RMC][CarAddItemFr]"

    // var for binding the add_car_item layout
    private var _binding: AddCarItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddCarItemBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView()")
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        val binding = AddCarItemBinding.bind(view)
        val carViewModel: CarViewModel by viewModels()

        binding.buttonGetLicenseplateRdw.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button GET clicked. Invoke RdwApiService")
            val kenteken: TextInputEditText = binding.txtInputLicensePlate
            val licensePlate: String = kenteken.text.toString()

            // Validate licenseplate before API call
            if (isValidLicensePlate()){
                // invoke RdwApiService for retrieval of cardetails of the given licenseplate
                Log.d(TAG, "onViewCreated() => invoke RdwApieService for licenseplate: " + licensePlate)
                carViewModel.getRdwCarDetails(kenteken.text.toString())
                Snackbar.make(view, "Car details retrieved at the RDW", Snackbar.LENGTH_LONG)
                .show()
            }
        }

        carViewModel.rdwResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated() => observer rdwResponse triggerd")
            bindUI(it)
        }
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
    fun bindUI(it: List<RdwResponseItem>) {
        Log.d(TAG, "bindUI() => voertuigsoort: " + it[0].voertuigsoort + " merk: " + it[0].merk)
        binding.rtvRdwInrichting.text = it[0].inrichting
        binding.tvRdwVoertuigsoort.text = it[0].voertuigsoort
        binding.tvRdwAantalDeuren.text = it[0].aantalDeuren
        binding.tvRdwAantalZitplaatsen.text = it[0].aantalZitplaatsen
        binding.rtvRdwKleur.text = it[0].eersteKleur
    }

    private fun isValidLicensePlate(): Boolean {
        if (binding.txtInputLicensePlate.text.toString().trim().isEmpty()) {
            binding.txtLayLicensePlateAdd.error = getString(R.string.required_field)
            binding.txtInputLicensePlate.requestFocus()
            return false
        } else if (!FieldValidation.isValidLicensePlate((binding.txtInputLicensePlate.text.toString()))) {
            binding.txtLayLicensePlateAdd.error = getString(R.string.invalid_license)
            binding.txtInputLicensePlate.requestFocus()
            return false
        } else {
            binding.txtLayLicensePlateAdd.isErrorEnabled = false
        }
        return true
    }
}
