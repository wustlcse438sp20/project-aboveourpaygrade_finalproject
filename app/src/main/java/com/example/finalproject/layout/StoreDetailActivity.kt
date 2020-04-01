package com.example.finalproject.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.finalproject.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import kotlinx.android.synthetic.main.activity_store_detail.*

class StoreDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)
    }

//    fun getPhoto(id: String) {
//        // Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
//        val fields: List<Place.Field> =
//            listOf(Place.Field.PHOTO_METADATAS)
//
//        // Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
//        val placeRequest = FetchPlaceRequest.newInstance(id, fields)
//
//        placesClient.fetchPlace(placeRequest).addOnSuccessListener { response: FetchPlaceResponse ->
//            val place = response.place
//            // Get the photo metadata.
//            val photoMetadata = place.photoMetadatas!![0]
//            // Get the attribution text.
//            val attributions = photoMetadata.attributions
//            // Create a FetchPhotoRequest.
//            val photoRequest = FetchPhotoRequest.builder(photoMetadata)
//                .setMaxWidth(500) // Optional.
//                .setMaxHeight(300) // Optional.
//                .build()
//            placesClient.fetchPhoto(photoRequest)
//                .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
//                    val bitmap = fetchPhotoResponse.bitmap
//                    storeThumbnail.setImageBitmap(bitmap)
//                }.addOnFailureListener { exception: Exception ->
//                    if (exception is ApiException) {
//                        val statusCode = exception.statusCode
//                        // Handle error with given status code.
//                        Log.e(
//                            "GMAPS",
//                            "Place not found: " + exception.message
//                        )
//                    }
//                }
//        }
//    }

    fun back(view: View) {
        finish()
    }
    fun leaveComment(view: View) {
        val comment = commentField.text.toString()
        // TODO: Firebase stuff
    }
}
