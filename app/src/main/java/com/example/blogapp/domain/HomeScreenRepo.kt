package com.example.blogapp.domain

import com.example.blogapp.core.Resource
import com.example.blogapp.data.Post

interface HomeScreenRepo {
    suspend fun getLatesPost():Resource<List<Post>>
}