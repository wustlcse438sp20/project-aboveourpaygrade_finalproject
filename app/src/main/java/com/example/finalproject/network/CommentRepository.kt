package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreComment
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CommentRepository {

    fun getComments(resBody: MutableLiveData<List<StoreComment>>, place: Place) {
        val database = Firebase.database

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists())
                    return
                val children = dataSnapshot.children
                val cmtList = ArrayList<StoreComment>()
                for (tmp in children) {
                    val value = tmp.value as HashMap<String, String>
                    val cmt =
                        StoreComment(value["text"]!!, value["char"]!![0].toString(), value["uid"]!!)
                    cmtList.add(cmt)
                }
                resBody.value = cmtList
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        database.getReference(place.id!!).addValueEventListener(postListener)
    }

    fun uploadComment(comment: StoreComment, store: Place) {
        val database = Firebase.database
        database.getReference(store.id!!).child(comment.hashCode().toString()).setValue(comment)
    }

}


