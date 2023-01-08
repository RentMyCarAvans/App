package com.avans.rentmycar.ui.offer

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
import com.avans.rentmycar.databinding.FragmentOfferBinding
import com.avans.rentmycar.viewmodel.CarViewModel

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

        binding.buttonCarSave.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button SAVE clicked. Invoke CarApiService")
            // TODO Add validation for all inputfield
            updateCar()
            Log.d(TAG, "onViewCreated() => Offer made for car " + args.licenseplate + ". Return to home")
            findNavController().navigate(R.id.action_offerCarFragment_to_mycars)
        }
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

    private fun updateCar() {
        Log.d(TAG, "updateCar()")
        val carViewModel: CarViewModel by viewModels()

        // Cast inputfields
        /*
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
            "Beinze" -> mType = "ICE"
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

         */
    }
}