package com.example.jsoncomments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jsoncomments.databinding.CommentViewBinding

    class CommentAdapter(private val comment: MutableList<Comment>, private val context: Context) :
        RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

        inner class CommentViewHolder(private val binding: CommentViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(comment: Comment) {
                binding.apply {
                    postId.text = comment.postId
                    id.text = comment.id
                    name.text = comment.name
                    email.text = comment.email
                    body.text = comment.body
                    id.setOnClickListener {
                        Toast.makeText(context, comment.body, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CommentViewBinding.inflate(layoutInflater, parent, false)
            val commentViewHolder = CommentViewHolder(binding)

            return commentViewHolder
        }


        override fun onBindViewHolder(
            holder: CommentViewHolder,
            position: Int
        ) {
            holder.bind(comment[position])
        }

        override fun getItemCount(): Int {
            return comment.size
        }


    }