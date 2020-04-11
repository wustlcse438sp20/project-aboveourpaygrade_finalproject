package com.example.finalproject.layout.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.StoreComment
import com.example.finalproject.model.VotingState


class CommentViewAdapter(private val comments: ArrayList<StoreComment>) :
    RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

}

class CommentViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.store_comment, parent, false)) {

    private val textView: TextView = itemView.findViewById(R.id.commentText)
    private val thumbsUp: ImageButton = itemView.findViewById(R.id.thumbsUpButton)
    private val thumbsDown: ImageButton = itemView.findViewById(R.id.thumbsDownButton)

    private val selectedColor = Color.rgb(0, 128, 255)
    private val defaultColor = Color.rgb(85, 85, 85)

    private var vote = VotingState.NEITHER

    fun bind(comment: StoreComment) {
        textView.text = comment.text

        // Allow buttons to change color independently
        thumbsUp.drawable.mutate()
        thumbsDown.drawable.mutate()

        setButtonState(comment.vote)

        thumbsUp.setOnClickListener {
            if(vote != VotingState.UP) {
                setButtonState(VotingState.UP)
                // TODO: send thumbs up message to firebase
            }

        }

        thumbsDown.setOnClickListener {
            if(vote != VotingState.DOWN) {
                setButtonState(VotingState.DOWN)
                // TODO: send thumbs down message to firebase
            }
        }
    }

    private fun setButtonState(vote: VotingState) {
        this.vote = vote
        when (vote) {
            VotingState.UP -> {
                thumbsDown.drawable.setTint(defaultColor)
                thumbsUp.drawable.setTint(selectedColor)
            }
            VotingState.DOWN -> {
                thumbsDown.drawable.setTint(selectedColor)
                thumbsUp.drawable.setTint(defaultColor)
            }
            else -> {
                thumbsDown.drawable.setTint(defaultColor)
                thumbsUp.drawable.setTint(defaultColor)
            }
        }
    }
}