package com.example.blogapp.domain.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {
    suspend fun getLatesPost(): Flow<Result<List<Post>>>
}