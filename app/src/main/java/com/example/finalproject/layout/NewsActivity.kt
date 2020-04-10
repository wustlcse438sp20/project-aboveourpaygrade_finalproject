package com.example.finalproject.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.layout.adapter.RssFeedAdapter
import com.example.finalproject.model.NewsItem
import com.example.finalproject.network.RssViewModel
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private val news = ArrayList<NewsItem>()
    private val viewModel = RssViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val adapter = RssFeedAdapter(news)
        viewModel.data.observe(this, Observer {
            news.clear()
            news.addAll(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.fetchNews()
        newsView.layoutManager = LinearLayoutManager(this)
        newsView.adapter = adapter
    }

    fun back(view: View) {
        finish()
    }
}
