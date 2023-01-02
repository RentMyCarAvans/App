package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //show actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        view.findViewById<Button>(R.id.button).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeDetailFragment)
        }
        return view
    }

}