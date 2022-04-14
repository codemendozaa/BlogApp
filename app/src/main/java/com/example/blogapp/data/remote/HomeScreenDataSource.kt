package com.example.blogapp.data.remote

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeScreenDataSource {

    suspend fun getLatesPosts(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        withContext(Dispatchers.IO) {
            val querySnapshot = FirebaseFirestore.getInstance().collection("post")
                .orderBy("created_at", Query.Direction.DESCENDING).get().await()

            for (post in querySnapshot.documents) {
                post.toObject(Post::class.java)?.let { fbPost ->
                    fbPost.apply {
                        created_at = post.getTimestamp(
                            "created_at",
                            DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                        )?.toDate()
                        //   val like = isPostLiked(uid,postId)
                    }
                    postList.add(fbPost)
                }
            }
        }
        return Result.Success(postList)
    }
}




