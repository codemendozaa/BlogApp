package com.example.blogapp.domain.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.HomeScreenDataSource


class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {
    override suspend fun getLatesPost(): Result<List<Post>> = dataSource.getLatesPosts()
    override suspend fun registerLikeButtonState(postId: String, liked: Boolean)= dataSource.registerLikeButtonState(postId,liked)
}