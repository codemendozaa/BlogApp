package com.example.blogapp.domain.home

import com.example.blogapp.core.Resource
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.HomeScreenDataSource
import com.example.blogapp.domain.home.HomeScreenRepo

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatesPost(): Resource<List<Post>> = dataSource.getLatesPosts()
}