package com.example.finalproject.layout.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.NewsItem


class RssFeedAdapter(private val entries: ArrayList<NewsItem>) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

}

class NewsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.news_item, parent, false)) {

    private val titleView: TextView = itemView.findViewById(R.id.newsItemTitle)
    private val descriptionView: TextView = itemView.findViewById(R.id.newsItemDescription)

    fun bind(entry: NewsItem) {
        titleView.text = entry.title
        descriptionView.text = entry.description
        itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(entry.link))
            it.context.startActivity(browserIntent)
        }
    }
}