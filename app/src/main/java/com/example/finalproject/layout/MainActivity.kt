package com.example.finalproject.layout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.*
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    lateinit var placesClient: PlacesClient

    val AUTOCOMPLETE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Places.initialize(applicationContext, getString(R.string.google_maps_key))

    }

    fun buttonPressed(view: View) {
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY,
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PRICE_LEVEL,
                Place.Field.ADDRESS,
                Place.Field.OPENING_HOURS,
                Place.Field.LAT_LNG,
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
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    data.putExtra("contents", "place_extra");
                    Log.i("GMAPS", "Place: " + place.toString() + ", " + place.id)
//                    getPhoto(place.id!!)
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("GMAPS", status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                    Log.i("GMAPS", "Cancelled")
                }
            }
        }
    }

    fun showNews(view: View) {}
    fun logout(view: View) {

        FirebaseAuth.getInstance().signOut()
        deleteFile("login")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}
