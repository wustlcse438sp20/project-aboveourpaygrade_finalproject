package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.NewsItem

class RssViewModel {

    private val repository = RssRepository()
    val data = MutableLiveData<List<NewsItem>>()

    fun fetchNews() {
        repository.getNews(data)
    }
}