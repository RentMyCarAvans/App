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
            carViewModel.updateCar()
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
        binding.txtInputCarVehicle.setText("Personenauto")  // TODO Is not in navArgs and database
        binding.txtInputCarYear.setText(args.year)
        binding.txtInputCarNrOfSeats.setText(args.numberofseats)
        binding.txtInputCarMileage.setText(args.mileage)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditCarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditCarFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}