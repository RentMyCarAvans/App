package com.avans.rentmycar.ui.mycars

import CarViewAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.FragmentMycarsBinding
import com.google.android.material.snackbar.Snackbar


class MyCarsFragment : Fragment() {
    private val TAG = "[RMC][MyCarsFragment]"

    private lateinit var _binding: FragmentMycarsBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(TAG, "onCreateView()")
        _binding = FragmentMycarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMycarsBinding.bind(view)

        // Clicklistener for floating action button
        binding.buttonNewCarFab.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Floating Action Button clicked")
            val bar = (activity as AppCompatActivity).supportActionBar
            bar?.title = "Add a new Car"
            val action = MyCarsFragmentDirections.actionMycarsToCarAddItemFragment()
            this.findNavController().navigate(action)
        }

        // click listener for the item click
        binding.root.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Floating Action Button clicked")
            val bar = (activity as AppCompatActivity).supportActionBar
            bar?.title = "Car details"
            val action = MyCarsFragmentDirections.actionMycarsToCarDetailFragment()
            this.findNavController().navigate(action)
        }


        binding.listCarRecyclerView.adapter = CarViewAdapter()
        binding.listCarRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = "My Cars"
    }

    private fun showDialog(view: View) {
        Log.d(TAG, "showDialog()")
        Snackbar.make(view, "TODO Call to car_detail_fragment", Snackbar.LENGTH_LONG)
            .setAction("DISMISS", View.OnClickListener {
                // executed when DISMISS is clicked
                System.out.println("Snackbar Set Action - OnClick.")
                })
            .show()
    }

    companion object {
        fun newInstance(): MyCarsFragment = MyCarsFragment()
    }
}
