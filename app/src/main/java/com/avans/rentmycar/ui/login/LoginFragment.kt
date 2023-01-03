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
import com.avans.rentmycar.databinding.FragmentLoginBinding
import com.avans.rentmycar.ui.viewmodel.LoginViewModel
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.rest.response.LoginResponse
import com.avans.rentmycar.utils.SessionManager
import okhttp3.internal.toLongOrDefault

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //show actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.btnRegister.setOnClickListener {
            doRegister()
        }

        binding.txtviewForgotPassword.setOnClickListener {
            doForgotPassword()
        }
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    it.msg?.let { it1 -> Log.v("APP", it1) }
                    processError(it.msg)
                    stopLoading()

                }
                else -> {
                    stopLoading()
                }
            }
        }

        viewModel.forgotPasswordResult.observe(viewLifecycleOwner) {
            when (it) {

                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    showToast("Password email has been sent.")
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                    stopLoading()
                }
                else -> {
                    stopLoading()
                }
            }
        }
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.login)
        return root
    }

    private fun doForgotPassword() {
        Log.d("RMC_APP", binding.txtInputEmail.text.toString())
        viewModel.forgotPassword(binding.txtInputEmail.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToHome() {
        Log.v("APP", "logging in")
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)

    }

    fun doLogin() {
        val email = binding.txtInputEmail.text.toString()
        val pwd = binding.txtPass.text.toString()
        viewModel.loginUser(email = email, pwd = pwd)

    }

    private fun doRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

    private fun processLogin(data: LoginResponse?) {
        showToast("Success:" + data?.message)
        if (data != null) {
            if (data.status === 200) {
                context?.let { SessionManager.saveAuthToken(it, data.data.token) }
                Log.d("APPRMC", data.toString())
                context?.let {SessionManager.saveUserId(it, data.data.userId.toLong())}
                Log.d("APP RMC", "status 200, now opening home")
                navigateToHome()
            }
        }
    }

    private fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}