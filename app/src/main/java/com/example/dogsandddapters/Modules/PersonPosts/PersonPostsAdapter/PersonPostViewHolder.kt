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
                           var generalposts: List<PersonPost>?): RecyclerView.ViewHolder(itemView) {

    var requestTextView: TextView? = null
    var offerTextView: TextView? = null
    var contactTextView: TextView? = null
    var idTextView: TextView? = null
    var imageImageView: ImageView? = null
    var personpost: PersonPost? = null

    init {
        requestTextView = itemView.findViewById(R.id.requestTextView)
        offerTextView = itemView.findViewById(R.id.offerTextView)
        contactTextView = itemView.findViewById(R.id.contactTextView)
        imageImageView = itemView.findViewById(R.id.postImageView)
        idTextView = itemView.findViewById(R.id.generalPostIdTextView)


        itemView.setOnClickListener {
            Log.i("TAG", "GeneralPostViewHolder: Position clicked $adapterPosition")

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