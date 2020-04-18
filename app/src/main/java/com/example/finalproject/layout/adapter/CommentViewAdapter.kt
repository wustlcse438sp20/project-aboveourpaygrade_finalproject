package com.example.finalproject.layout.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.example.finalproject.R
import com.example.finalproject.model.StoreComment
import com.example.finalproject.model.VotingState
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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
    private val pic: ImageView =itemView.findViewById(R.id.pfPic)

    private val selectedColor = Color.rgb(0, 128, 255)
    private val defaultColor = Color.rgb(85, 85, 85)

    private var vote = VotingState.NEITHER

    fun bind(comment: StoreComment) {
        textView.text = comment.text

        val rand  = comment.uid.hashCode()%8
        var color = Color.RED

        when(rand){
            0->color=Color.RED
            1->color=Color.BLUE
            2->color=Color.GREEN
            3->color=Color.YELLOW
            4->color=Color.BLACK
            5->color=Color.CYAN
            6->color=Color.GRAY
            7->color=Color.MAGENTA
        }


        val drawable = TextDrawable.builder()
            .buildRect(comment.char.toString(), color)
        pic.setImageDrawable(drawable)

        // Allow buttons to change color independently
        thumbsUp.drawable.mutate()
        thumbsDown.drawable.mutate()

        setButtonState(comment.vote)

        thumbsUp.setOnClickListener {
            if(vote != VotingState.UP) {
                setButtonState(VotingState.UP)
                val database = Firebase.database
                val postListener = object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        (dataSnapshot.value as HashSet<String>).remove(comment.uid)

                    }
                }
                database.getReference(comment.uid.toString()).child(comment.hashCode().toString()).child("dislikes")

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