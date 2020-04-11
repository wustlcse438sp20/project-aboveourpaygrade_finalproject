package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreComment

class CommentViewModel {

    val data = MutableLiveData<List<StoreComment>>()
    private val repository = CommentRepository()

    fun addComment(comment: StoreComment) {
        repository.uploadComment(comment)
    }

    fun loadComments() {
        repository.getComments(data)
    }

}