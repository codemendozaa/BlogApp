package com.example.blogapp.domain

import com.example.blogapp.core.Resource
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource):HomeScreenRepo {
    override suspend fun getLatesPost(): Resource<List<Post>> = dataSource.getLatesPosts()
}