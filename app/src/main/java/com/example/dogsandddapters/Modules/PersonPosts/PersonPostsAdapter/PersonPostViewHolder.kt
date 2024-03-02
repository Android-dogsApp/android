package com.example.dogsandddapters.Modules.GeneralPosts.PersonPostAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Modules.PersonPosts.PersonPostsRcyclerViewActivity
import com.example.dogsandddapters.R

class PersonPostViewHolder(val itemView: View,
                           val listener: PersonPostsRcyclerViewActivity.OnItemClickListener?,
                           var personposts: List<PersonPost>?): RecyclerView.ViewHolder(itemView) {

    var requestTextView: TextView? = null
    var offerTextView: TextView? = null
    var contactTextView: TextView? = null
    var idTextView: TextView? = null
    var imageImageView: ImageView? = null
    var personpost: PersonPost? = null

    init {
        requestTextView = itemView.findViewById(R.id.requestTextViewperson)
        offerTextView = itemView.findViewById(R.id.offerTextViewperson)
        contactTextView = itemView.findViewById(R.id.contactTextViewperson)
        imageImageView = itemView.findViewById(R.id.postImageViewperson)
        idTextView = itemView.findViewById(R.id.personPostIdTextViewperson)


        itemView.setOnClickListener {
            Log.i("TAG", "PersonPostViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onPersonPostClicked(personpost)
        }
    }

    fun bind(personpost: PersonPost?) {
        this.personpost = personpost
        requestTextView?.text = personpost?.request
        offerTextView?.text = personpost?.offer
        contactTextView?.text = personpost?.contact
        idTextView?.text = personpost?.postid
        //TODO: ADD PUBLISHER - THROUGH THE FIREBASE ♥
       //imageImageView?.text= generalpost?.image
    }
}