package com.example.blogapp.data.remote

import com.example.blogapp.core.Resource
import com.example.blogapp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatesPosts():Resource<List<Post>>{
        val postList  = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("post").get().await()
        for (post in querySnapshot.documents){
            post.toObject(Post::class.java )?.let { fbPost ->
                postList.add(fbPost)
            }
        }
        return Resource.Success(postList)
    }
}