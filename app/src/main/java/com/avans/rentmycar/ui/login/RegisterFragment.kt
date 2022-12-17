package com.avans.rentmycar.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avans.rentmycar.databinding.FragmentIntroBinding
import com.avans.rentmycar.databinding.FragmentRegisterBinding
import com.avans.rentmycar.model.LoginViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnCreateAccount.setOnClickListener {
            createAccount()
        }

        return root
    }

    private fun createAccount() {
        TODO("Not yet implemented")
    }

}