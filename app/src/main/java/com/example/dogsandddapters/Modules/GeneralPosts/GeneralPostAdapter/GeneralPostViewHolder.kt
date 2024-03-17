package com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostsRcyclerViewActivity
import com.example.dogsandddapters.R
import com.squareup.picasso.Picasso

class GeneralPostViewHolder(val itemView: View,
                            val listener: GeneralPostsRcyclerViewActivity.OnItemClickListener?,
                            var generalposts: List<PersonPost>?): RecyclerView.ViewHolder(itemView) {

    var requestTextView: TextView? = null
    var offerTextView: TextView? = null
    var contactTextView: TextView? = null
    var idTextView: TextView? = null
    var imageImageView: ImageView? = null
    var generalpost: PersonPost? = null

    init {
        requestTextView = itemView.findViewById(R.id.requestTextView)
        offerTextView = itemView.findViewById(R.id.offerTextView)
        contactTextView = itemView.findViewById(R.id.contactTextView)
        imageImageView = itemView.findViewById(R.id.postImageView)
        idTextView = itemView.findViewById(R.id.generalPostIdTextView)


        itemView.setOnClickListener {
            Log.i("TAG", "GeneralPostViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onGeneralPostClicked(generalpost)
        }
    }

    fun bind(generalpost: PersonPost?) {
        this.generalpost = generalpost
        requestTextView?.text = generalpost?.request
        offerTextView?.text = generalpost?.offer
        contactTextView?.text = generalpost?.contact
        idTextView?.text = generalpost?.postid
        //TODO: ADD PUBLISHER - THROUGH THE FIREBASE ♥
        //imageImageView?.text= generalpost?.image
        val imageUrl = generalpost?.image
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl)
                .resize(400, 400)
                .centerCrop()
                .into(imageImageView)
           }
       }
}