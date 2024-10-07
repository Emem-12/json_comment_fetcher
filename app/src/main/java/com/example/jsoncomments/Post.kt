package com.example.jsoncomments

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("postId") val userid: String,
    @SerializedName("comments")val id: List<Comment>,
    val comment: Comment


)