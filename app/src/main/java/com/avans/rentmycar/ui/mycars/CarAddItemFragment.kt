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
import CarDetailAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.AddCarItemBinding
import com.avans.rentmycar.model.RdwResponseItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.combineTransform

/**
 * [CarAddItemFragment] displays the details of the selected item.
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        val binding = AddCarItemBinding.bind(view)
        val carAddItemViewModel: CarAddItemViewModel by viewModels()

        binding.buttonGetLicenseplateRdw.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button GET clicked. Invoke RdwApiService")
            val kenteken : TextInputEditText = binding.txtInputLicensePlate
            val license : String = kenteken.text.toString()

            // invoke RdwApiService for retrieval of cardetails for the given licenseplate
            Log.d(TAG,"onViewCreated() => invoke RdwApieService for licenseplate: " + license)
            val responseRdw = carAddItemViewModel.getRdwCarDetails(kenteken.text.toString())
            Log.d(TAG, "onViewCreated() response rdw " + responseRdw)

            Snackbar.make(view, "Car details retrieved of RDW", Snackbar.LENGTH_LONG)
                .show()
        }
        Log.d(TAG, "onViewCreated() => before observer ")
        carAddItemViewModel.rdwResponse.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated() => it.ToString: " + it.toString())
            // binding.txtInputLicensePlate.text = carAddItemViewModel.rdwResponse.value.toString()
        }
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
}
