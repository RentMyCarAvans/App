package com.avans.rentmycar.ui.profile

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.Constant
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.utils.SessionManager.clearData
import com.avans.rentmycar.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: UserViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding!!.btnLogout.setOnClickListener {
            logout()
        }
        binding!!.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileDetailFragment)

        }
        val userId = SessionManager.getUserId(requireContext())

        if (userId != null) {
            viewModel.getUser(userId)
        }


        viewModel.userResult.observe(viewLifecycleOwner) {
            Log.d("RentMyCarApp", it.toString())
            when (it) {

                is BaseResponse.Loading -> {
                    Log.d("RentMyCarApp", "loading")

                    showLoading()
                }
                is BaseResponse.Error -> {
                    Log.d("RentMyCarApp", "ERROR, API DOWN?")
                    showLoading()
                    Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).show()
                }
                is BaseResponse.Success -> {
                    Log.d("RentMyCarApp", "success")

                    stopLoading()
                    binding!!.firstName.text = it.data?.data?.firstName
                    binding!!.lastName.text = it.data?.data?.lastName
                    binding!!.email.text = it.data?.data?.email
                    binding!!.tvAddress.text = it.data?.data?.address
                    binding!!.phone.text = it.data?.data?.telephone
                    binding!!.location.text = it.data?.data?.city
//                    binding!!.txtBirthdate.text = it.data?.data?.dateOfBirth
                    binding!!.txtBonuspoint.text = it.data?.data?.bonusPoints.toString() + " " + getString(R.string.bonuspoints)
                    Glide.with(this).load("${Constant.BASE_URL}/api/v1/users/profilephoto/${it.data?.data?.id}").centerCrop().placeholder(R.drawable.noprofilepic).into(binding!!.profileImage);

                    if (it.data?.data?.isVerifiedUser == true) {
                        binding!!.verifiedUser.text = getString(R.string.verified_user)
                    } else {binding!!.verifiedUser.text = getString(R.string.not_verified_user)    }


//                    val dateofBirth = it.data?.data?.dateOfBirth
//                    val dateFormatted = LocalDateTime
//                        .parse(dateofBirth)
//                        .toLocalDate()
//                        .format(
//                            DateTimeFormatter
//                                .ofLocalizedDate(FormatStyle.LONG)
//                                .withLocale(Locale.ENGLISH)
//                        )
//
//                    binding!!.txtBirthdate.text = dateFormatted
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