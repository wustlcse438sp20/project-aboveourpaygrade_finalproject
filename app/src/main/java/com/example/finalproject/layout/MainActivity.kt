package com.example.finalproject.layout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.layout.adapter.StoreListAdapter
import com.example.finalproject.model.StoreListing
import com.example.finalproject.network.StoreListViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val STORE_DETAIL_REQUEST_CODE = 2

    private val stores = ArrayList<StoreListing>()
    private val storeViewModel = StoreListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Places.initialize(applicationContext, getString(R.string.google_maps_key))

        val adapter = StoreListAdapter(stores)
        storeList.layoutManager = LinearLayoutManager(this)
        storeList.adapter = adapter

        storeViewModel.data.observe(this, Observer {
            stores.clear()
            stores.addAll(it)
            adapter.notifyDataSetChanged()
        })
        storeViewModel.getStores()
    }

    fun buttonPressed(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PRICE_LEVEL,
                Place.Field.ADDRESS,
                Place.Field.OPENING_HOURS,
                Place.Field.UTC_OFFSET,
                Place.Field.PHOTO_METADATAS
            )
        )
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .build(this)

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = data!!.getParcelableExtra("places/selected_place") as Place
                    val intent = Intent(this, StoreDetailActivity::class.java)


                    intent.putExtra("contents", "place_extra")
                    intent.putExtra("places/selected_place", place)

                    Log.i("GMAPS", "Place: " + place.toString() + ", " + place.id)
                    startActivityForResult(intent, STORE_DETAIL_REQUEST_CODE)
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("GMAPS", status.statusMessage!!)
                    Toast.makeText(this, status.statusMessage, Toast.LENGTH_LONG).show()
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                    Log.i("GMAPS", "Cancelled")
                }
            }
        }
    }

    fun showNews(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }
    fun logout(@Suppress("UNUSED_PARAMETER") view: View) {

        FirebaseAuth.getInstance().signOut()
        deleteFile("login")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
