package com.avans.rentmycar.ui.mycars

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.databinding.FragmentEditCarBinding

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
        // TODO Figure out why this is not working. The data is NOT being showed on the UI. Instead, you HAVE to use binding.root
        // return inflater.inflate(R.layout.fragment_edit_car, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: EditCarFragmentArgs by navArgs()
        //bindUI(args.color, args.brand, args.mileage?.toInt(), args.licenseplate, args.numberofseats?.toInt(), args.year?.toInt(), args.carimageurl)
        bindUI2(args)
    }

    /**
     * Binds views with the passed in item data.
     */
    fun bindUI(color:String?, brand:String?, mileage:Int?, licensePlate:String?, nrOfSeats:Int?, year:Int?, carImageUrl:String?) {
        Log.d(TAG, "bindUI() => color: "+color )
        binding.txtInputCarNrOfDoors.setText(color)
        binding.txtInputCarColor.setText(color).toString()
        binding.txtInputCarModel.setText("Tesla").toString()
        this.binding.txtInputCarVehicle.setText("Auto").toString()
        binding.txtInputCarVehicle.setText("Porsche 911 Turbo")
        binding.txtInputCarVehicle.setText("koppppppp")
    }
    fun bindUI2(args: EditCarFragmentArgs) {
        binding.txtInputCarNrOfDoors.setText(args.numberofseats)
        binding.txtInputCarColor.setText(args.color)
        binding.txtInputCarModel.setText(args.brand)
        binding.txtInputCarVehicle.setText(args.year)

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