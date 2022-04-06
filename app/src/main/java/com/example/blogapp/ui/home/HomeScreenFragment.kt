package com.example.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.blogapp.R
import com.example.blogapp.data.model.Post
import com.example.blogapp.databinding.FragmentHomeScreenBinding
import com.example.blogapp.ui.adapter.HomeScreenAdapter
import com.google.firebase.Timestamp


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding:FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        val postList = listOf(
            Post("https://firebasestorage.googleapis.com/v0/b/blogapp-bea7f.appspot.com/o/imagenes%2Fprofile.jpeg?alt=media&token=19ab0412-b84b-4b8c-831d-188870b6759f","Erix", Timestamp.now(),"https://firebasestorage.googleapis.com/v0/b/blogapp-bea7f.appspot.com/o/imagenes%2FCaptura%20de%20Pantalla%202022-04-04%20a%20la(s)%2012.14.29%20p.%C2%A0m..png?alt=media&token=67e43f67-e863-464d-9456-e9f3676be34d"),
            Post("https://firebasestorage.googleapis.com/v0/b/blogapp-bea7f.appspot.com/o/imagenes%2Fprofile.jpeg?alt=media&token=19ab0412-b84b-4b8c-831d-188870b6759f","Erix", Timestamp.now(),"https://firebasestorage.googleapis.com/v0/b/blogapp-bea7f.appspot.com/o/imagenes%2FCaptura%20de%20Pantalla%202022-04-04%20a%20la(s)%2012.16.59%20p.%C2%A0m..png?alt=media&token=5f7adfc0-c320-482a-8bd8-34bb2a499224"),
            Post("https://firebasestorage.googleapis.com/v0/b/blogapp-bea7f.appspot.com/o/imagenes%2Fprofile.jpeg?alt=media&token=19ab0412-b84b-4b8c-831d-188870b6759f","Erix", Timestamp.now(),"https://firebasestorage.googleapis.com/v0/b/blogapp-bea7f.appspot.com/o/imagenes%2FCaptura%20de%20Pantalla%202022-04-04%20a%20la(s)%2012.14.29%20p.%C2%A0m..png?alt=media&token=67e43f67-e863-464d-9456-e9f3676be34d")
        )

        binding.rvHome.adapter = HomeScreenAdapter(postList)
    }
}