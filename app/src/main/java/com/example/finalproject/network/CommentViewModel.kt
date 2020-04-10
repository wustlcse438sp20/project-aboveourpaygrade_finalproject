package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreComment

class CommentViewModel {

    val data = MutableLiveData<MutableList<StoreComment>>()
    private val repository = CommentRepository()

    fun addComment(comment: StoreComment) {
        data.value?.add(comment)
        repository.uploadComment(comment)
    }

    fun loadComments() {
        repository.getComments(data)
    }

}