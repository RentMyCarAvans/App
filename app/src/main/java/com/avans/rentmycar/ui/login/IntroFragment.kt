package com.avans.rentmycar.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentIntroBinding
import com.avans.rentmycar.databinding.FragmentLoginBinding
import com.avans.rentmycar.model.LoginViewModel
import com.avans.rentmycar.rest.response.BaseResponse

class IntroFragment : Fragment() {
    private var _binding: FragmentIntroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.btnRegister.setOnClickListener {
            doRegister()
        }

        return root
    }

    private fun doRegister() {
        findNavController().navigate(R.id.action_introFragment_to_registerFragment)
    }

    private fun doLogin() {
        findNavController().navigate(R.id.action_introFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}