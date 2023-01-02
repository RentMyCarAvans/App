package com.avans.rentmycar.ui.profile

import android.content.Intent.getIntent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.Constant
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.utils.SessionManager.clearData
import com.avans.rentmycar.ui.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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
        val userId = context?.let { SessionManager.getUserId(it) }

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
                    binding!!.textviewFirstName.text = it.data?.data?.firstName
                    binding!!.txtviewLastName.text = it.data?.data?.lastName
                    binding!!.textviewEmail.text = it.data?.data?.email
                    binding!!.textviewAddress.text = it.data?.data?.address
                    binding!!.textviewPhone.text = it.data?.data?.telephone
                    binding!!.textviewLocation.text = it.data?.data?.city
                    binding!!.textviewBirthdate.text = it.data?.data?.dateOfBirth
                    binding!!.textviewBonuspoint.text = it.data?.data?.bonusPoints.toString() + " " + getString(R.string.bonuspoints)
                    Glide.with(this).load("${Constant.BASE_URL}/api/v1/users/profilephoto/${it.data?.data?.id}").centerCrop().placeholder(R.drawable.noprofilepic).into(binding!!.imgProfilePicture);

                    if (it.data?.data?.isVerifiedUser == true) {
                        binding!!.textviewVerifiedUser.text = getString(R.string.verified_user)
                    } else {binding!!.textviewVerifiedUser.text = getString(R.string.not_verified_user)    }


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

     fun stopLoading() {
        binding?.progressBar?.visibility = View.GONE
    }

     fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    private fun logout() {
        clearData(requireContext())
        //restart app
        findNavController().navigate(R.id.action_profileFragment_to_introFragment2)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
   }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }



}