package com.avans.rentmycar.ui.mycars

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentEditCarBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

        return inflater.inflate(R.layout.fragment_edit_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: EditCarFragmentArgs by navArgs()
        val carId = args.id
        val carColor = args.color
        bindUI(carColor)

    }

    /**
     * Binds views with the passed in item data.
     */
    fun bindUI(color:String) {
        Log.d(TAG, "bindUI() => color: "+color )
        binding.txtInputCarColor.setText(color)
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
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}