package com.example.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blogapp.R
import com.example.blogapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding:FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {

        binding.btnSignup.setOnClickListener {
            val username = binding.editTextUserName.text.toString().trim()
            val email = binding.editTexEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val comfirmPassword = binding.editTextComfirmPassword.text.toString().trim()

            if (password != comfirmPassword) {
                binding.editTextPassword.error = "Password does not match"
                binding.editTextComfirmPassword.error = "Password does not match"
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                binding.editTextUserName.error = "Username is empty"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.editTexEmail.error = "Email is empty"
                return@setOnClickListener

            }
            if (password.isEmpty()) {

                binding.editTextPassword.error = "Password is empty"
                return@setOnClickListener
            }

            if (comfirmPassword.isEmpty()) {
                binding.editTextComfirmPassword.error = "Comfirm Password is empty"
                return@setOnClickListener


            }
            Log.d("signUpData","data:$username,$email,$password,$comfirmPassword")
        }
    }
}