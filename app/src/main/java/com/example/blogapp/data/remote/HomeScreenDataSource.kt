package com.example.blogapp.data.remote

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

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
                        id = post.id

                    }
                    postList.add(fbPost)
                }
            }
        }
        return Result.Success(postList)
    }

    fun registerLikeButtonState(postId: String, liked: Boolean) {
        val increment = FieldValue.increment(1)
        val decrement = FieldValue.increment(-1)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val postRef = FirebaseFirestore.getInstance().collection("post").document(postId)
        val postLikesRef = FirebaseFirestore.getInstance().collection("postLikes").document(postId)
        val database = FirebaseFirestore.getInstance()

        database.runTransaction { transaction ->
            val snapshot = transaction.get(postRef)
            val likeCount = snapshot.getLong("likes")
            if (likeCount != null) {
                if (liked) {
                    if (transaction.get(postLikesRef).exists()) {
                        transaction.update(postLikesRef, "likes", FieldValue.arrayUnion(uid))
                    } else {
                        transaction.set(
                            postLikesRef,
                            hashMapOf("likes" to arrayListOf(uid)),
                            SetOptions.merge()
                        )
                    }

                    transaction.update(postRef, "likes", increment)
                } else {
                    transaction.update(postRef, "likes", decrement)
                    transaction.update(postLikesRef, "likes", FieldValue.arrayRemove(uid))
                }
            }

        }.addOnFailureListener {
            throw Exception(it.message)
        }
    }
}




