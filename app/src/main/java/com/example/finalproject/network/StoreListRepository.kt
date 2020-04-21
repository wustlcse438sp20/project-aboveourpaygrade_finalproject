package com.example.finalproject.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreListing
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StoreListRepository {

    fun getStores(stores: MutableLiveData<List<StoreListing>>) {
        // TODO: get stores from firebase & store in live data
        // stores.value = data
        var cmtStoreList = HashSet<String>()
        val database = Firebase.database
        val mAuth = FirebaseAuth.getInstance()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists())
                    return
                val children = dataSnapshot.children
                for (tmp in children) {
                    val children2 = tmp.children
                    for (tmp2 in children2) {
                        if (tmp2.child("uid").value.toString() == mAuth.currentUser!!.uid.toString()) {
                            cmtStoreList.add(tmp.key.toString())
                           // Log.v("zach","user commented on store: "+tmp.key.toString())
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        database.getReference("/").addValueEventListener(postListener)



    }

    fun uploadStore(store: StoreListing) {
        // TODO: Add new store to firebase
    }
}