package com.avans.rentmycar.ui.profile

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.model.response.BaseResponse
import com.avans.rentmycar.utils.SessionManager.clearData
import com.avans.rentmycar.viewmodel.UserViewModel
import com.avans.rentmycar.utils.*
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

import java.util.*


// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: UserViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding!!.btnLogout.setOnClickListener {
            logout()
        }
        binding!!.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileDetailFragment)
        }
        val userId = context?.let { SessionManager.getUserId(it) }

        if (userId != null) {
            viewModel.getUser(userId)
        }


        viewModel.userResult.observe(viewLifecycleOwner) {
            when (it) {

                is BaseResponse.Loading -> {
                    Log.d("RentMyCarApp", "loading")

                    showLoading()
                }
                is BaseResponse.Error -> {
                    Log.d("RentMyCarApp", "ERROR, API DOWN?")
                    showLoading()
                    val snackbar = Snackbar.make(view, "Error", Snackbar.LENGTH_LONG)
                    snackbar.view.setBackgroundColor(resources.getColor(R.color.warning))
                    snackbar.show()
                }
                is BaseResponse.Success -> {
                    Log.d("RentMyCarApp", "success")

                    stopLoading()
                    binding!!.textviewFirstName.text = it.data?.data?.firstName
                    binding!!.txtviewLastName.text = it.data?.data?.lastName
                    binding!!.textviewEmail.text = it.data?.data?.email
                    binding!!.textviewAddress.text = it.data?.data?.address
                    binding!!.textviewPhone.text = it.data?.data?.telephone
                    binding!!.textviewLocation.text = it.data?.data?.city
                    binding!!.textviewBirthdate.text = it.data?.data?.dateOfBirth
                    binding!!.textviewBonuspoint.text =
                        it.data?.data?.bonusPoints.toString() + " " + getString(R.string.bonuspoints)
                    Glide.with(this)
                        .load("${Constant.BASE_URL}/api/v1/users/profilephoto/${it.data?.data?.id}")
                        .centerCrop().placeholder(R.drawable.noprofilepic)
                        .into(binding!!.imgProfilePicture);

                    if (it.data?.data?.isVerifiedUser == true) {
                        binding!!.textviewVerifiedUser.text = getString(R.string.verified_user)
                    } else {
                        binding!!.textviewVerifiedUser.text = getString(R.string.not_verified_user)
                    }

                }
            }
        }


    }


    fun stopLoading() {
        binding?.progressBar?.visibility = View.GONE
    }

    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun logout() {
        clearData(requireContext())

        val navController: NavController =
            findNavController()
        navController.run {
            popBackStack()
            navigate(R.id.home)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


}