package com.example.blogapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blogapp.R
import com.example.blogapp.core.BaseViewHolder
import com.example.blogapp.core.TimeUtils
import com.example.blogapp.data.model.Post
import com.example.blogapp.databinding.PostItemViewBinding

class HomeScreenAdapter(private val postList: List<Post>,private val onPostClickListener:OnPostClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postClickListener:OnPostClickListener? = null
    init {
        postClickListener = onPostClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])

        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val contex: Context
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            setupProfileInfo(item)
            addPostTimeStamp(item)
            setupPostImage(item)
            setupPostDescription(item)
            tintHeardIcon(item)
            setupLikedCount(item)
            setLikeClickAction(item)
        }



        private fun setupProfileInfo(post: Post) {
            Glide.with(contex).load(post.poster?.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = post.poster?.username
        }

        private fun addPostTimeStamp(post: Post) {
            val createAt = (post.created_at?.time?.div(1000L))?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimestamp.text = createAt
        }

        private fun setupPostImage(post: Post) {
            Glide.with(contex).load(post.post_image).centerCrop().into(binding.postImage)

        }

        private fun setupPostDescription(post: Post) {
            if (post.post_description.isEmpty()) {
                binding.postDescription.visibility = View.GONE
            } else {
                binding.postDescription.text = post.post_description
            }
        }

        private fun tintHeardIcon(post: Post){
            if (!post.liked){
                binding.likeBtn.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.ic_baseline_favorite_border))
                binding.likeBtn.setColorFilter(ContextCompat.getColor(contex, R.color.black))
            }else{
                binding.likeBtn.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.ic_baseline_favorite))
                binding.likeBtn.setColorFilter(ContextCompat.getColor(contex, R.color.red_like))


            }
        }

        private fun setLikeClickAction(post: Post) {
        binding.likeBtn.setOnClickListener {
            if (post.liked) post.apply { liked = false}else post.apply{liked = true}
            tintHeardIcon(post)
            postClickListener?.onLikeButtonClick(post,post.liked)
        }
        }

        private fun setupLikedCount(post: Post) {
            if (post.likes > 0){
                binding.likeCount.visibility =View.VISIBLE
                binding.likeCount.text ="${post.likes} likes"
            }else{
                binding.likeCount.visibility =View.GONE
            }
        }
    }
}

interface OnPostClickListener{
    fun onLikeButtonClick(post: Post,liked:Boolean)
}