package com.example.finalproject.layout.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.layout.StoreDetailActivity
import com.example.finalproject.model.StoreListing


class StoreListAdapter(private val entries: ArrayList<StoreListing>) :
    RecyclerView.Adapter<StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StoreViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

}

class StoreViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.news_item, parent, false)) {

    private val titleView: TextView = itemView.findViewById(R.id.newsItemTitle)

    fun bind(entry: StoreListing) {
        titleView.text = entry.name
        itemView.setOnClickListener {
            val intent = Intent(it.context, StoreDetailActivity::class.java)
            intent.putExtra("type", "place_id")
            intent.putExtra("place_id", entry.id)
            it.context.startActivity(intent)
        }
    }
}