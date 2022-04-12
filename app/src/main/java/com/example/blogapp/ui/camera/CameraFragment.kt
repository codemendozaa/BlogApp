package com.example.blogapp.ui.camera

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.data.remote.HomeScreenDataSource
import com.example.blogapp.data.remote.camera.CameraDataSource
import com.example.blogapp.databinding.FragmentCameraBinding
import com.example.blogapp.domain.camera.CameraRepoImpl
import com.example.blogapp.domain.home.HomeScreenRepoImpl
import com.example.blogapp.presentation.HomeScreenViewModel
import com.example.blogapp.presentation.HomeScreenViewModelFactory
import com.example.blogapp.presentation.camera.CameraViewModel
import com.example.blogapp.presentation.camera.CameraViewModelFactory
import java.lang.Exception

class CameraFragment : Fragment(R.layout.fragment_camera) {
    private var REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null
    private lateinit var binding:FragmentCameraBinding
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(
            CameraRepoImpl(
                CameraDataSource()
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view )
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)


        }catch (e:ActivityNotFoundException){
            Toast.makeText(requireContext(), "No se encontro ninguna app para abrir la camara", Toast.LENGTH_SHORT).show()
        }
        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let {
                viewModel.uploadPhoto(it, binding.editTextPhotoDescription.text.toString().trim()).observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(requireContext(), "Uploading photo...", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.postImg.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}