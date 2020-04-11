package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.StoreListing

class StoreListViewModel {
    val data = MutableLiveData<List<StoreListing>>()
    private val repository = StoreListRepository()

    fun getStores() {
        repository.getStores(data)
    }

    fun uploadStore(store: StoreListing) {
        repository.uploadStore(store)
    }

}