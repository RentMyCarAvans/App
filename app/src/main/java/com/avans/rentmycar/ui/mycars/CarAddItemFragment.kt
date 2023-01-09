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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.AddCarItemBinding
import com.avans.rentmycar.model.RdwResponseItem
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.FieldValidation
import com.avans.rentmycar.utils.SessionManager
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

        binding.buttonCarGetRdwdetails.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button GET clicked. Invoke RdwApiService")
            val kenteken: TextInputEditText = binding.txtInputCarLicensePlate
            val licensePlate: String = kenteken.text.toString()

            // Validate licenseplate before API call
            if (isValidLicensePlate()){
                // invoke RdwApiService for retrieval of cardetails of the given licenseplate
                Log.d(TAG, "onViewCreated() => invoke RdwApiService for licenseplate: " + licensePlate)
                carViewModel.getRdwCarDetails(kenteken.text.toString())
                Snackbar.make(view, "Car details retrieved at the RDW", Snackbar.LENGTH_LONG)
                .show()
            }
        }

        binding.buttonCarSave.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button SAVE clicked. Invoke CarApiService")
            // TODO Add validation for all inputfield
            createCar()
            Log.d(TAG, "onViewCreated() => Car added. Return to home of my cars")
            findNavController().navigate(R.id.action_carAddItemFragment_to_mycars)
        }

        carViewModel.rdwResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated() => observer rdwResponse triggerd")
            if (carViewModel.rdwResponse.value!!.isEmpty()){
                Snackbar.make(view, "Error saving Car. Please try again later", Snackbar.LENGTH_LONG)
                    .show()
                }
            bindUI(it)
        }

        carViewModel.carCreateResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated() => observer carCreateResponse triggerd")
            when (it) {
                is BaseResponse.Loading -> {
                    Log.d(TAG, "onViewCreated() => observer BaseResponse.Loading")

                }
                is BaseResponse.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    Snackbar.make(view, "An error occurred while fetching your data. Please try again later", Snackbar.LENGTH_LONG)
                            .show()
                    Log.d(TAG, "onViewCreated() => observer BaseResponse.Error")

                }
                is BaseResponse.Success -> {
                    Log.d(TAG, "onViewCreated() => observer BaseResponse.Success. Return to MyCarsFragment ")
                    val action = CarDetailFragmentDirections.actionCarDetailFragmentToMycars()
                    findNavController().navigate(action)
                    // Snackbar.make(view, "An error occurred while fetching your data. Please try again later", Snackbar.LENGTH_LONG)
                    //     .show()
                }
            }
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
        binding.txtInputCarModel.setText( it[0].merk + "- " + it[0].handelsbenaming)
        binding.txtInputCarVehicle.setText(it[0].voertuigsoort)
        binding.txtInputCarNrOfDoors.setText(it[0].aantalDeuren)
        binding.txtInputCarNrOfSeats.setText(it[0].aantalZitplaatsen)
        binding.txtInputCarColor.setText(it[0].eersteKleur)
        binding.txtInputCarYear.setText(it[0].datumEersteToelating?.substring(0,4)) // substring year of date with format yyyymmdd
    }

    private fun isValidLicensePlate(): Boolean {
        if (binding.txtInputCarLicensePlate.text.toString().trim().isEmpty()) {
            binding.txtInputCarLicensePlate.error = getString(R.string.required_field)
            binding.txtInputCarLicensePlate.requestFocus()
            return false
        } else if (!FieldValidation.isValidLicensePlate((binding.txtInputCarLicensePlate.text.toString()))) {
            binding.txtInputCarLicensePlate.error = getString(R.string.invalid_license)
            binding.txtInputCarLicensePlate.requestFocus()
            return false
        } else {
            binding.txtLayCarlicensePlateAdd.isErrorEnabled = false
        }
        return true
    }

    private fun createCar() {
        Log.d(TAG, "createCar()")
        val carViewModel: CarViewModel by viewModels()

        // Cast inputfields
        val carColor: TextInputEditText = binding.txtInputCarColor
        val color: String = carColor.text.toString()

        val carLicensePlate: TextInputEditText = binding.txtInputCarLicensePlate
        val licensePlate: String = carLicensePlate.text.toString()

        val carModel: TextInputEditText = binding.txtInputCarModel
        val model: String = carModel.text.toString()

        val carNrOfSeats: TextInputEditText = binding.txtInputCarNrOfSeats
        val nrOfSeats: String = carNrOfSeats.text.toString()

        val carType: TextInputEditText = binding.txtInputCarVehicle
        val type: String = carType.text.toString()

        val carVehicleType: TextInputEditText = binding.txtInputCarVehicle
        val vehicleType: String = carVehicleType.text.toString()
        val userId = SessionManager.getUserId(requireContext())?.toInt()
        val carYear: TextInputEditText = binding.txtInputCarYear
        val year: String = carYear.text.toString()
        Log.d(TAG, "createCar() => colorType = " + color)
        Log.d(TAG, "createCar() => licensePlate = " + licensePlate)
        Log.d(TAG, "createCar() => model = " + model)
        Log.d(TAG, "createCar() => numberOfSeats = " + nrOfSeats.toInt(),)
        Log.d(TAG, "createCar() => type = " + type)
        Log.d(TAG, "createCar() => vehicleType = " + vehicleType)
        Log.d(TAG, "createCar() => yearOfManufacture = " + year.toInt())
        var mType: String
        when (vehicleType) {
            "Benzine" -> mType = "ICE"
            "Diesel" -> mType = "BEV"
            else -> {
                mType = "FCEV"
            }
        }
        carViewModel.createCar(
            colorType = color,
            image = "", // TODO
            licensePlate = licensePlate,
            mileage = 100, // TODO
            model = model,
            numberOfSeats = nrOfSeats.toInt(),
            type = mType,
            userId = userId!!,
            vehicleType = vehicleType,
            yearOfManufacture = year.toInt()
        )
        Log.d(TAG, "createCar() => After calling viewModel.createCar().")
    }
}
