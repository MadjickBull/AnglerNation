package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.CommentDto
import com.anglernation.app.ui.databinding.CommentItemBinding

class CommentAdapter(
    private val commentList: List<CommentDto>,
    private val onCommentClick: (CommentDto) -> Unit
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentText: android.widget.TextView = binding.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.binding.comment = comment
        holder.contentText.text = comment.content
        holder.itemView.setOnClickListener {
            onCommentClick(comment)
        }
    }

    override fun getItemCount(): Int = commentList.size

    fun updateCommentList(newList: List<CommentDto>) {
        commentList.clear()
        commentList.addAll(newList)
        notifyDataSetChanged()
    }
}