package com.example.finalproject.layout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.finalproject.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.google.android.libraries.places.widget.Autocomplete
import kotlinx.android.synthetic.main.activity_store_detail.*

class StoreDetailActivity : AppCompatActivity() {

    lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        placesClient = Places.createClient(this)
        val type = intent.getStringExtra("contents")
        if(type == "place_extra") {
            val place = Autocomplete.getPlaceFromIntent(intent)
            updateInterface(place)
        } else {
            val id = intent.getStringExtra("place_id");
            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PRICE_LEVEL,
                Place.Field.ADDRESS,
                Place.Field.OPENING_HOURS,
                Place.Field.UTC_OFFSET,
                Place.Field.PHOTO_METADATAS
            )
            val placeRequest = FetchPlaceRequest.newInstance(id!!, fields)
            placesClient.fetchPlace(placeRequest).addOnSuccessListener {
                updateInterface(it.place)
            }
        }
    }

    private fun updateInterface(place: Place) {
        getPhoto(place.photoMetadatas!![0])
        storeNameLabel.text = place.name
        storeAddressLabel.text = place.address
        if(place.isOpen!!) {
            storeHoursLabel.text = "Currently Open"
            storeHoursLabel.setTextColor(Color.DKGRAY)
        } else {
            storeHoursLabel.text = "Currently Closed"
            storeHoursLabel.setTextColor(Color.rgb(128, 0, 0))
        }
    }

    private fun getPhoto(photoMetadata: PhotoMetadata) {
        // Get the attribution text.
        val attributions = photoMetadata.attributions
        // Create a FetchPhotoRequest.
        val photoRequest = FetchPhotoRequest.builder(photoMetadata)
            .setMaxWidth(500) // Optional.
            .setMaxHeight(300) // Optional.
            .build()
        placesClient.fetchPhoto(photoRequest)
            .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                val bitmap = fetchPhotoResponse.bitmap
                storeThumbnailView.setImageBitmap(bitmap)
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    val statusCode = exception.statusCode
                    // Handle error with given status code.
                    Log.e(
                        "GMAPS",
                        "Place not found: " + exception.message
                    )
                }
            }
    }

    fun back(view: View) {
        finish()
    }
    fun leaveComment(view: View) {
        val comment = commentField.text.toString()
        // TODO: Firebase stuff
    }
}
