package com.avans.rentmycar.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.avans.rentmycar.databinding.FragmentRegisterBinding
import com.avans.rentmycar.viewmodel.LoginViewModel
import com.avans.rentmycar.model.response.BaseResponse
import com.avans.rentmycar.utils.FieldValidation.isValidBirthDate
import com.avans.rentmycar.utils.FieldValidation.isValidEmail

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
        //show actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        binding.btnCreateAccount.setOnClickListener {
            if (isValidated()) {
                val firstName = binding.txtInputFirstName.text.toString()
                val lastName = binding.txtInputLastName.text.toString()
                val email = binding.txtInputEmail.text.toString()
                val pwd = binding.txtInputPass.text.toString()
                val birthDate = binding.txtInputBirthDate.text.toString()
                createAccount(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = pwd,
                    dateOfBirth = birthDate
                )
            }
        }

        setupListeners()

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.register)

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
                    Toast.makeText(activity,getString(R.string.success_creating_account),Toast.LENGTH_LONG).show();
                    stopLoading()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is BaseResponse.Error -> {
                    Log.d("RentMyCarApp", "error")
                    Toast.makeText(activity,getString(R.string.error_creating_account),Toast.LENGTH_LONG).show();
                    stopLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE

    }
    private fun stopLoading() {
        binding.progressBar.visibility = View.GONE

    }

    private fun createAccount(firstName: String, lastName: String, email: String, password: String, dateOfBirth: String) {
        viewModel.registerUser(firstName, lastName, dateOfBirth, email, password)
    }


    private fun isValidated(): Boolean {
        return validateFirstName() && validateLastName() && validateBirthDate() && validateEmail() && validatePassword()
    }

    private fun setupListeners() {
        binding.txtInputFirstName.addTextChangedListener(TextFieldValidation(binding.txtInputFirstName))
        binding.txtInputLastName.addTextChangedListener(TextFieldValidation(binding.txtInputLastName))
        binding.txtInputEmail.addTextChangedListener(TextFieldValidation(binding.txtInputEmail))
        binding.txtInputPass.addTextChangedListener(TextFieldValidation(binding.txtInputPass))
        binding.txtInputBirthDate.addTextChangedListener(TextFieldValidation(binding.txtLayBirthDate))
    }

    private fun validateFirstName(): Boolean {
        if (binding.txtInputFirstName.text.toString().trim().isEmpty()) {
            binding.txtLayFirstNameAdd.error = getString(R.string.required_field)
            binding.txtInputFirstName.requestFocus()
            return false
        } else {
            binding.txtLayFirstNameAdd.isErrorEnabled = false
        }
        return true
    }

    private fun validateLastName(): Boolean {
        if (binding.txtInputLastName.text.toString().trim().isEmpty()) {
            binding.txtLayLastNameAdd.error = getString(R.string.required_field)
            binding.txtInputLastName.requestFocus()
            return false
        } else {
            binding.txtLayFirstNameAdd.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (binding.txtInputEmail.text.toString().trim().isEmpty()) {
            binding.txtLayEmailAdd.error = getString(R.string.required_field)
            binding.txtInputEmail.requestFocus()
            return false
        } else if (!isValidEmail(binding.txtInputEmail.text.toString())) {
            binding.txtLayEmailAdd.error = getString(R.string.invalid_email)
            binding.txtInputEmail.requestFocus()
            return false
        } else {
            binding.txtLayEmailAdd.isErrorEnabled = false
        }
        return true
    }

    private fun validateBirthDate(): Boolean {
        if (binding.txtInputBirthDate.text.toString().trim().isEmpty()) {
            binding.txtLayBirthDate.error = getString(R.string.required_field)
            binding.txtInputBirthDate.requestFocus()
            return false
        } else if (!isValidBirthDate(binding.txtInputBirthDate.text.toString())) {
            binding.txtLayBirthDate.error = getString(R.string.invalid_birthdate)
            binding.txtInputBirthDate.requestFocus()
            return false
        } else {
            binding.txtLayBirthDate.isErrorEnabled = false
        }
        return true
    }
    private fun validatePassword(): Boolean {
        if (binding.txtInputPass.text.toString().trim().isEmpty()) {
            binding.txtLayPassSignup.error = getString(R.string.required_field)
            binding.txtInputPass.requestFocus()
            return false
        } else if (binding.txtInputPass.text.toString().length < 6) {
            binding.txtLayPassSignup.error = "Password can't be less than 6"
            binding.txtInputPass.requestFocus()
            return false
        }
//         TODO enable this before going to production
//        else if (!isStringContainNumber(binding.txtInputPass.text.toString())) {
//            binding.txtLayPassSignup.error = "Required at least 1 digit"
//            binding.txtInputPass.requestFocus()
//            return false
//        } else if (!isStringLowerAndUpperCase(binding.txtInputPass.text.toString())) {
//            binding.txtLayPassSignup.error =
//                "Password must contain upper and lower case letters"
//            binding.txtInputPass.requestFocus()
//            return false
//        } else if (!isStringContainSpecialCharacter(binding.txtInputPass.text.toString())) {
//            binding.txtLayPassSignup.error = "1 special character required"
//            binding.txtInputPass.requestFocus()
//            return false
//        }
        else {
            binding.txtLayPassSignup.isErrorEnabled = false
        }
        return true
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.firstname_input -> {
                    validateFirstName()
                }
                R.id.lastname_input -> {
                    validateLastName()
                }
                R.id.txtInput_email -> {
                    validateEmail()
                }
                R.id.txtInput_pass -> {
                    validatePassword()
                }
                R.id.txtInput_birthDate -> {
                    validateBirthDate()
                }
            }
        }
    }
}