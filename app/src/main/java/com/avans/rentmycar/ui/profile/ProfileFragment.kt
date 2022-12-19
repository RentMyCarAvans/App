package com.avans.rentmycar.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.viewmodel.UserViewModel

// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding!!.userName.text = "BLADIEBLAAAA"

        var test = viewModel.getUser("test@test.com","bladiebla")
        Log.v("APP", test.toString())
        viewModel.userResult.observe(viewLifecycleOwner) {
        Log.v("APP", it.toString())
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}