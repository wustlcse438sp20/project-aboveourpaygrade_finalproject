package com.example.finalproject.network

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreComment
import com.google.android.libraries.places.api.model.Place

class CommentViewModel(s : Place, uid :String) {

    val data = MutableLiveData<List<StoreComment>>()
    private val repository = CommentRepository()
    private val store = s



    fun addComment(comment: StoreComment) {
        repository.uploadComment(comment,store)
    }

    fun loadComments() {
        repository.getComments(data, store)
    }

}