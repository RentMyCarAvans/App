package com.avans.rentmycar.ui.mycars

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
import com.avans.rentmycar.databinding.FragmentEditCarBinding
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.CarViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [EditCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditCarFragment : Fragment() {
    private val TAG = "[RMC][EditCarFragment]"

    // var for binding the fragment_edit_car layout
    private var _binding: FragmentEditCarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("[RMC][EditCarFragment]","onCreateView()")
        _binding = FragmentEditCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: EditCarFragmentArgs by navArgs()
        val carViewModel: CarViewModel by viewModels()

        bindUI(args)

        binding.buttonCarUpdate.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button UPDATE clicked. Invoke CarApiService")
            updateCar(args.licenseplate, args.id)
            Snackbar.make(view, "Car " + args.brand + " with licenseplate " + args.licenseplate + " updated", Snackbar.LENGTH_LONG)
                .show()
            Log.d(TAG, "onViewCreated() => Car " + args.brand + " with licenseplate " + args.licenseplate + " updated. Return to home")
            findNavController().navigate(R.id.action_editCarFragment_to_mycars)
        }
    }

    /**
     * Binds views with the passed in item data.
     */
    fun bindUI(args: EditCarFragmentArgs) {
        Log.d("[RMC][EditCarFragment]","bindUI()")
        binding.txtInputCarNrOfDoors.setText(args.numberofseats)
        binding.txtInputCarColor.setText(args.color)
        binding.txtInputCarModel.setText(args.brand)
        binding.txtInputCarVehicle.setText(args.vehicletype)
        binding.txtInputCarYear.setText(args.year)
        binding.txtInputCarNrOfSeats.setText(args.numberofseats)
        binding.txtInputCarMileage.setText(args.mileage)

    }

    private fun updateCar(licensePlate: String, Id: String) {
        Log.d(TAG, "updateCar()")
        val carViewModel: CarViewModel by viewModels()

        // Cast inputfields
        val carColor = binding.txtInputCarColor
        val color: String = carColor.text.toString()

        val carModel = binding.txtInputCarModel
        val model: String = carModel.text.toString()

        val carNrOfSeats = binding.txtInputCarNrOfSeats
        val nrOfSeats: String = carNrOfSeats.text.toString()

        val carType = binding.txtInputCarVehicle
        val type: String = carType.text.toString()

        val carVehicleType = binding.txtInputCarVehicle
        val vehicleType: String = carVehicleType.text.toString()

        val carYear = binding.txtInputCarYear
        val year: String = carYear.text.toString()

        val carMileage = binding.txtInputCarMileage
        val mileage: String = carMileage.text.toString()

        val userId = SessionManager.getUserId(requireContext())?.toInt()
        Log.d(TAG, "updateCar() => id = " + Id)
        Log.d(TAG, "updateCar() => colorType = " + color)
        Log.d(TAG, "updateCar() => licensePlate = " + licensePlate)
        Log.d(TAG, "updateCar() => mileage = " + mileage.toInt())
        Log.d(TAG, "updateCar() => model = " + model)
        Log.d(TAG, "updateCar() => numberOfSeats = " + nrOfSeats.toInt(),)
        Log.d(TAG, "updateCar() => type = " + type)
        Log.d(TAG, "updateCar() => vehicleType = " + vehicleType)
        Log.d(TAG, "updateCar() => yearOfManufacture = " + year.toInt())
        var mType: String
        when (vehicleType) {
            "Benzine" -> mType = "ICE"
            "Diesel" -> mType = "BEV"
            else -> {
                mType = "FCEV"
            }
        }
        if (mileage != null) {
            carViewModel.updateCar(
                id = Id.toInt(),
                colorType = color,
                image = "https://www.thecarwiz.com/images/listing_vehicle_placeholder.jpg", // placeholder
                licensePlate = licensePlate,
                mileage = mileage.toInt(),
                model = model,
                numberOfSeats = nrOfSeats.toInt(),
                type = mType,
                userId = userId!!,
                vehicleType = vehicleType,
                yearOfManufacture = year.toInt()
            )
        }
        Log.d(TAG, "updateCar() => After calling viewModel.updateCar().")
    }
}