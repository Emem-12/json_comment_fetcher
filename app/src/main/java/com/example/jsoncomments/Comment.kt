package com.example.jsoncomments

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("postId") val postId: String,
    @SerializedName("id") val id: String,
    @SerializedName("name")val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body")val body: String,

)