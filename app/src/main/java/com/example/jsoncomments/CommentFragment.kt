package com.example.jsoncomments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsoncomments.databinding.FragmentPostBinding
import com.example.jsoncomments.repo.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var comment: MutableList<Comment>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comment = mutableListOf()
        commentAdapter = CommentAdapter(comment , requireContext())

        getUserComment()

        binding.recyclerView.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun getUserComment() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = CommentRepository().getComment()

            when (response) {
                is Response.Success -> {
                    withContext(Dispatchers.Main) {
                        val listOfComment = response.data as List<Comment>
                        comment.addAll(listOfComment)
                        commentAdapter.notifyItemRangeInserted(0, listOfComment.size)
                    }
                }

                is Response.Failure -> {
                    //        Snackbar.make(binding.root, response.message, Snackbar.LENGTH_INDEFINITE).show()
                    withContext(Dispatchers.Main) {
                        showErrorDialog(message = response.message)
                    }
                }
            }

            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showErrorDialog(message: String) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setMessage(message)

        //Test features.
        alertDialog.setTitle("hello user")
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("Retry") { _, _ ->
            //
        }
        alertDialog.setNegativeButton(
            "Okay"
        ) { _, _ ->
            //
        }
        alertDialog.setNeutralButton(
            "Hmm!"
        ) { _, _ ->
            //
        }
        alertDialog.show()
    }
}

