package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.PostDto
import com.anglernation.app.ui.databinding.PostItemBinding

class PostAdapter(
    private val postList: List<PostDto>,
    private val onPostClick: (PostDto) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleText: android.widget.TextView = binding.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.binding.post = post
        holder.titleText.text = post.title
        holder.itemView.setOnClickListener {
            onPostClick(post)
        }
    }

    override fun getItemCount(): Int = postList.size

    fun updatePostList(newList: List<PostDto>) {
        postList.clear()
        postList.addAll(newList)
        notifyDataSetChanged()
    }
}