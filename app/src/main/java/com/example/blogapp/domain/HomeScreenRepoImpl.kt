package com.example.blogapp.domain

import com.example.blogapp.core.Resource
import com.example.blogapp.data.Post

class HomeScreenRepoImpl(private val dataSource:HomeScreenDataSource):HomeScreenRepo {
    override suspend fun getLatesPost(): Resource<List<Post>> {
        TODO("Not yet implemented")
    }
}