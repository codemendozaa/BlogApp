package com.example.blogapp.data.remote

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    @ExperimentalCoroutinesApi
    suspend fun getLatesPosts(): Flow<Result<List<Post>>> = callbackFlow {
        val postList = mutableListOf<Post>()
        var postReference: Query? = null
        try {
            postReference = FirebaseFirestore.getInstance().collection("post")
                .orderBy("created_at", Query.Direction.DESCENDING)

        } catch (e: Throwable) {
            close(e)
        }

        val suscription = postReference?.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener

            try {
                postList.clear()
                for (post in value.documents) {
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
            }catch (e:Exception){
                close(e)
            }

            offer(Result.Success(postList))
        }
        awaitClose { suscription?.remove() }
    }
}