package com.example.finalproject.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.finalproject.R

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }

    fun back(view: View) {}
}
