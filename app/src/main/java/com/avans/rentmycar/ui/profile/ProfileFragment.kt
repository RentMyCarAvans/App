package com.avans.rentmycar.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.SessionManager.clearData
import com.avans.rentmycar.viewmodel.UserViewModel

// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding!!.btnLogout.setOnClickListener {
            logout()
        }
        binding!!.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileDetailFragment)

        }
        var test = viewModel.getUser("test@test.com","bladiebla")
//        Log.v("APP", test.toString())
        viewModel.userResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }
                is BaseResponse.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                }
                is BaseResponse.Success -> {
                    stopLoading()
                binding!!.firstName.text = it.data?.data?.firstName
                binding!!.lastName.text = it.data?.data?.lastName
                binding!!.email.text = it.data?.data?.email
                binding!!.tvDob.text = it.data?.data?.dateOfBirth

                }
            }
        }

    }

    private fun stopLoading() {
        binding?.progressBar?.visibility = View.GONE
    }

    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun logout() {
        clearData(requireContext())
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }



}