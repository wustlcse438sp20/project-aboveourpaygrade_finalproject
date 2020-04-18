package com.example.finalproject.network

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreComment
import com.example.finalproject.model.VotingState
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CommentRepository {

    fun getComments(resBody: MutableLiveData<List<StoreComment>>, p : Place) {
        // TODO: Get comments from firestore
        // Store the results in resBody.value
        // resBody.value = data
        val database = Firebase.database

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists())
                    return;
                val children = dataSnapshot.children
                var cmtList = ArrayList<StoreComment>()
                for(tmp in children) {
                    var value = tmp.value  as HashMap<String, String>
                    //Log.v("zach","vote stored = "+tmp["vote"])
                    val cmt = StoreComment(value["text"]!!, voteStateStr(value["vote"]!!), value["char"]!![0].toString(),value["uid"]!!)
                    cmtList.add(cmt)
                }
                resBody.value=cmtList
            }

            override fun onCancelled(databaseError: DatabaseError) {


            }
        }
       database.getReference(p.id!!).addValueEventListener(postListener)
    }

    fun voteStateStr(s:String):VotingState{
        if(s=="UP")
            return VotingState.UP;
        else
            if(s=="DOWN")
                return VotingState.DOWN
            else
                return VotingState.NEITHER
    }

    fun uploadComment(comment: StoreComment, store: Place) {

        val mAuth = FirebaseAuth.getInstance()
        val database = Firebase.database
        Log.v("zach","About to add to database: "+comment.uid+", "+comment.text)
        database.getReference(store.id!!).child(comment.hashCode().toString()).setValue(comment)



    }

}


