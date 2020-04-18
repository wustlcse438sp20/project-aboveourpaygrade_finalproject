package com.example.finalproject.model

import com.google.android.libraries.places.api.model.Place
import java.io.Serializable

data class StoreComment(val text: String, val char : String, val uid : String) : Serializable

enum class VotingState {
    UP, NEITHER
}