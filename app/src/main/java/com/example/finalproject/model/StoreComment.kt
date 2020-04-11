package com.example.finalproject.model

data class StoreComment(val text: String, val vote: VotingState)

enum class VotingState {
    UP, DOWN, NEITHER
}