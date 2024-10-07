package com.example.jsoncomments.repo

import android.util.Log
import com.example.jsoncomments.Comment
import com.example.jsoncomments.Post
import com.example.jsoncomments.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class CommentRepository() {
    private val url = URL("https://jsonplaceholder.typicode.com/comments")

    suspend fun getComment(): Response {
        Log.d("httpGetRequest", "Requesting...")
        // Open a connection
        val connection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        try {
            connection.requestMethod = "GET"  // HTTP GET method

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }

                val gson = Gson()
                val type = object : TypeToken<List<Comment>>() {}.type
                val listOfComment = gson.fromJson<List<Comment>>(response, type)
                Log.d("httpGetRequest", "Post count is: ${listOfComment.size}")

                return Response.Success(data = listOfComment as List<Post>?)
            } else {
                Log.i("httpGetRequest", "Error: ${connection.responseCode}")

                return Response.Failure(message = "Error: ${connection.responseCode}")
            }
        } catch (e: Exception) {
            Log.e("httpGetRequest", "Failed because: ${e.message}")
            return Response.Failure(message = "Error: ${e.message}")
        } finally {
            connection.disconnect()  // Always close the connection
        }
    }
}