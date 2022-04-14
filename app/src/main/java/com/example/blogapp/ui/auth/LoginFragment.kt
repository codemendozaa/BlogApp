package com.example.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Result
import com.example.blogapp.core.hideKeyboard
import com.example.blogapp.data.remote.auth.AuthDataSource
import com.example.blogapp.databinding.FragmentLoginBinding
import com.example.blogapp.domain.auth.AuthRepoImpl
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(
        AuthDataSource()
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSigUpPage()
    }

    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let { user->
            if (user.displayName.isNullOrEmpty()){
                findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
            }else{
                findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
            }

        }
    }

    private fun doLogin(){
        binding.btnSignin.setOnClickListener {
            it.hideKeyboard()
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
                is  Result.Loading-> {
                    binding.progressBar.visibility =View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility =View.GONE
                    Toast.makeText(requireContext(), "Welcome:${result.data?.email}", Toast.LENGTH_SHORT).show()
                    if (result.data?.displayName.isNullOrEmpty()){
                        findNavController().navigate(R.id.action_loginFragment_to_setupProfileFragment)
                    }else{
                        findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    }

                }
                is Result.Failure -> {
                    binding.progressBar.visibility =View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()

                }
            }
        })

    }

}