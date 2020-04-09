package com.example.finalproject.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.layout.adapter.RssFeedAdapter
import com.example.finalproject.network.RssFeed
import com.example.finalproject.network.RssRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private val news = ArrayList<RssFeed.Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val repository = RssRepository()
        val data = MutableLiveData<List<RssFeed.Entry>>()
        val adapter = RssFeedAdapter(news)
        data.observe(this, Observer {
            news.clear()
            news.addAll(it)
            adapter.notifyDataSetChanged()
        })
        repository.getNews(data)
        newsView.layoutManager = LinearLayoutManager(this)
        newsView.adapter = adapter
    }

    fun back(view: View) {
        finish()
    }
}
