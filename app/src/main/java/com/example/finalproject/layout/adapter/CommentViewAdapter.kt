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


class CommentViewAdapter(private val comments: ArrayList<StoreComment>, private val store: Place) :
    RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(inflater, parent, store)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

}

class CommentViewHolder(inflater: LayoutInflater, parent: ViewGroup, s: Place) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.store_comment, parent, false)) {

    private val textView: TextView = itemView.findViewById(R.id.commentText)
    private val thumbsUp: ImageButton = itemView.findViewById(R.id.thumbsUpButton)
    private val pic: ImageView = itemView.findViewById(R.id.pfPic)
    private val lblLikes: TextView = itemView.findViewById(R.id.likeCount)
    private val store = s


    private val selectedColor = Color.rgb(0, 128, 255)
    private val defaultColor = Color.rgb(85, 85, 85)

    private var vote = VotingState.NEITHER

    fun bind(comment: StoreComment) {
        textView.text = comment.text

        val rand = comment.uid.hashCode() % 8
        var color = Color.RED


        val youLikeListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //Log.v("zach","DATABASE ERROR")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Log.v("zach","Inside the listener & value= "+dataSnapshot.value)
                if (!dataSnapshot.exists()) {
                    //Log.v("zach","no snapshot")
                    return
                }
                if (((dataSnapshot.value!!).toString() == "true")) {
                    //Log.v("zach","found the like")
                    setButtonState(VotingState.UP)
                }

            }
        }

        Firebase.database.getReference(store.id.toString()).child(comment.hashCode().toString())
            .child("likes").child(FirebaseAuth.getInstance().uid!!.toString())
            .addListenerForSingleValueEvent(youLikeListener)


        val likeCountListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    lblLikes.text = "0"
                    return
                }
                var sum = 0
                for (s in dataSnapshot.children) {
                    if (dataSnapshot.child(s.key.toString()).value.toString() == "true") {
                        sum++
                    }
                }
                lblLikes.text = sum.toString()

            }
        }

        Firebase.database.getReference(store.id.toString()).child(comment.hashCode().toString())
            .child("likes").addValueEventListener(likeCountListener)


        when (rand) {
            0 -> color = Color.RED
            1 -> color = Color.BLUE
            2 -> color = Color.GREEN
            3 -> color = Color.YELLOW
            4 -> color = Color.BLACK
            5 -> color = Color.CYAN
            6 -> color = Color.GRAY
            7 -> color = Color.MAGENTA
        }


        val drawable = TextDrawable.builder()
            .buildRect(comment.char.toString(), color)
        pic.setImageDrawable(drawable)

        // Allow buttons to change color independently
        thumbsUp.drawable.mutate()

        // setButtonState()

        thumbsUp.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            if (vote != VotingState.UP) {
                setButtonState(VotingState.UP)
                val database = Firebase.database
                database.getReference(store.id.toString()).child(comment.hashCode().toString())
                    .child("likes").child(mAuth.currentUser!!.uid).setValue(true)

            } else {
                setButtonState(VotingState.NEITHER)
                val database = Firebase.database
                database.getReference(store.id.toString()).child(comment.hashCode().toString())
                    .child("likes").child(mAuth.currentUser!!.uid).setValue(false)
            }

        }

    }

    private fun setButtonState(vote: VotingState) {
        this.vote = vote
        when (vote) {
            VotingState.UP -> {
                thumbsUp.drawable.setTint(selectedColor)
                lblLikes.setTextColor(selectedColor)
            }
            else -> {
                thumbsUp.drawable.setTint(defaultColor)
                lblLikes.setTextColor(defaultColor)
            }
        }
    }
}