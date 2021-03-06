package com.example.finalproject.network

import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.NewsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RssRepository {

    fun getNews(resBody: MutableLiveData<List<NewsItem>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = RssFeed().getData()

            withContext(Dispatchers.Main) {
                resBody.value = data
            }
        }
    }
}