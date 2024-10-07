package com.example.jsoncomments

sealed class Response {
    data class Success(var data: List<Post>?) : Response()
    data class Failure(var message: String) : Response()
}
