package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreListing

class StoreListRepository {

    fun getStores(stores: MutableLiveData<List<StoreListing>>) {
        // TODO: get stores from firebase & store in live data
        // stores.value = data
    }

    fun uploadStore(store: StoreListing) {
        // TODO: Add new store to firebase
    }
}