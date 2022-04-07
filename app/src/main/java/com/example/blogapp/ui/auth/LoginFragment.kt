package com.example.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Resource
import com.example.blogapp.data.remote.auth.LoginDataSource
import com.example.blogapp.databinding.FragmentLoginBinding
import com.example.blogapp.domain.auth.LoginRepoImpl
import com.example.blogapp.presentation.auth.LoginScreenViewModel
import com.example.blogapp.presentation.auth.LoginScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<LoginScreenViewModel> { LoginScreenViewModelFactory(LoginRepoImpl(
        LoginDataSource()
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSigUpPage()
    }

    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogin(){
        binding.btnSignin.setOnClickListener {
            val email = binding.editTexEmail.text.toString().trim()
            val password = binding.editTexPassword.text.toString().trim()
            validateCredencial(email,password)
            signIn(email,password)
        }
    }

    private fun goToSigUpPage(){
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun validateCredencial(email:String,password:String){
        if (email.isEmpty()){
            binding.editTexEmail.error = "E-mail is empty"
            return
        }
        if (password.isEmpty()){
            binding.editTexPassword.error = "Password is empty"
            return
        }
    }

    private fun signIn(email: String,password: String){

        viewModel.singIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility =View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBar.visibility =View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    Toast.makeText(requireContext(), "Welcome:${result.data?.email}", Toast.LENGTH_SHORT).show()

                }
                is Resource.Failure -> {
                    binding.progressBar.visibility =View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()

                }
            }
        })

    }

}