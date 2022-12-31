package com.avans.rentmycar.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentIntroBinding
import com.avans.rentmycar.databinding.FragmentRegisterBinding
import com.avans.rentmycar.model.LoginViewModel
import com.avans.rentmycar.rest.response.BaseResponse

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
        activity?.actionBar?.title = getString(R.string.register)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //show actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        binding.btnCreateAccount.setOnClickListener {
            val firstName = binding.txtInputFirstName.text.toString()
            val lastName = binding.txtInputLastName.text.toString()
            val email = binding.txtInputEmail.text.toString()
            val pwd = binding.txtInputPass.text.toString()
            val birthDate = binding.txtInputBirthDate.text.toString()
            createAccount(firstName = firstName, lastName = lastName, email= email, password = pwd,  dateOfBirth = birthDate)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.registerResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    Log.d("RentMyCarApp", "loading")

                    showLoading()
                }
                is BaseResponse.Success -> {
                    Log.d("RentMyCarApp", "success")
                    Toast.makeText(getActivity(),getString(R.string.success_creating_account),Toast.LENGTH_LONG).show();
                    stopLoading()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)


                }
                is BaseResponse.Error -> {
                    Log.d("RentMyCarApp", "error")
                    Toast.makeText(getActivity(),getString(R.string.error_creating_account),Toast.LENGTH_LONG).show();

                    stopLoading()

                }
            }
        }
    }

    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE

    }
    private fun stopLoading() {
        binding?.progressBar?.visibility = View.GONE

    }

    private fun createAccount(firstName: String, lastName: String, email: String, password: String, dateOfBirth: String) {
        viewModel.registerUser(firstName, lastName, dateOfBirth, email, password)
    }

}