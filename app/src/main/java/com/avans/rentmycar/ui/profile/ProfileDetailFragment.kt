package com.avans.rentmycar.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentProfileDetailBinding
import com.avans.rentmycar.repository.UserRepository
import com.avans.rentmycar.rest.request.UploadStreamRequestBody
import com.avans.rentmycar.rest.response.BaseResponse
import com.avans.rentmycar.utils.Constant
import com.avans.rentmycar.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File


class ProfileDetailFragment : Fragment(R.layout.fragment_profile_detail) {
    private var binding: FragmentProfileDetailBinding? = null
    private val viewModel by viewModels<UserViewModel>()
    val userRepo = UserRepository()

    //    val contentResolver = requireActivity().contentResolver
    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileDetailBinding.bind(view)

        binding!!.profileImage

        binding!!.btnUploadphoto.setOnClickListener {
            openImageChooser()
        }
        var test = viewModel.getUser("test@test.com","bladiebla")

        viewModel.userResult.observe(viewLifecycleOwner) {
            Log.d("APP", "entered observe!!")

            when (it) {
                is BaseResponse.Loading -> {
//                    showLoading()
                    Log.d("APP", "was LOADING!!")

                }
                is BaseResponse.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                    Log.d("APP", "was error!!")

                }
                is BaseResponse.Success -> {
//                    stopLoading()
                    Log.d("APP", "was success!!")
                    Glide.with(this).load("${Constant.BASE_URL}/api/v1/users/profilephoto/${it.data?.data?.id}").into(binding!!.profileImage);
                    view.findViewById<TextInputEditText>(R.id.firstname_input).setText(it.data?.data?.firstName)
                    view.findViewById<TextInputEditText>(R.id.lastname_input).setText(it.data?.data?.lastName)
                    view.findViewById<TextInputEditText>(R.id.address_input).setText(it.data?.data?.address)
                    view.findViewById<TextInputEditText>(R.id.telephone_input).setText(it.data?.data?.telephone)
                    }
            }
        }
    }


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }


    private fun uploadImage(file: String) {
        lifecycleScope.launch {
            val file = File(file)
//            val reqFile = file.asRequestBody("*/*".toMediaTypeOrNull())
            val image = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

//           val stream = context?.getContentResolver()?.openInputStream(file);
//            val stream = contentResolver.openInputStream(file) ?: return@launch
//
//            val request = UploadStreamRequestBody("image/*", stream, onUploadProgress = {
//                Log.d("MyActivity", "On upload progress $it")
//                binding!!.progressBar.progress = it // Some ProgressBar
//            })
            val filePart = MultipartBody.Part.createFormData(
                "file",
                file.name,
                image
            )
            try {
                userRepo.uploadProfilePhoto(filePart)
            } catch (e: Exception) { // if something happens to the network
                Snackbar.make(binding!!.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                Log.d("APP", e.toString())
                return@launch
            }
            Log.d("MyActivity", "On finish upload file")
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    Log.d("APP", selectedImageUri.toString())
                    uploadImage(selectedImageUri.toString())
                    binding!!.profileImage.setImageURI(selectedImageUri)
                }
            }


//        if (requestCode == Activity.RESULT_OK) {
//            data?.data?.let { uri ->
//                binding!!.profileImage.setImageURI(uri)
//
//                uploadImage(uri.toString())
//
//            }
//        } else super.onActivityResult(requestCode, resultCode, data)
//        }

        }
    }
}