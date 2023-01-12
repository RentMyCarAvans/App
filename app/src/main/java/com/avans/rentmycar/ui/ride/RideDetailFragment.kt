package com.avans.rentmycar.ui.ride

import android.content.Intent.getIntent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON
import androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED
import androidx.core.view.isInvisible

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileBinding
import com.avans.rentmycar.databinding.FragmentRideBinding
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.SessionManager.clearData
import com.avans.rentmycar.ui.viewmodel.UserViewModel
import com.avans.rentmycar.utils.*
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

import java.util.*
import java.util.concurrent.Executor


// viewbinding in fragment : https://stackoverflow.com/questions/62952957/viewbinding-in-fragment
class RideDetailFragment : Fragment(R.layout.fragment_ride_detail)  {
    private var binding: FragmentRideBinding? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRideBinding.bind(view)
    }




    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}