package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.NewsItem
import com.example.finalproject.model.StoreComment
import com.example.finalproject.model.VotingState


class CommentRepository {

    fun getComments(resBody: MutableLiveData<MutableList<StoreComment>>) {
        // TODO: Get comments from firestore
        // Store the results in resBody.value
        // resBody.value = data
        resBody.value = mutableListOf(StoreComment("Good", VotingState.DOWN),
            StoreComment("Bad", VotingState.UP))
    }

    fun uploadComment(comment: StoreComment) {
        // TODO: Upload comment to firestore
    }

}
