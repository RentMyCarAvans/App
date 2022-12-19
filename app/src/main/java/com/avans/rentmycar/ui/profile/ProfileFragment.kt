package com.avans.rentmycar.ui.profile

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment

import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding

// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding!!.userName.text = "BLADIEBLAAAA"
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}