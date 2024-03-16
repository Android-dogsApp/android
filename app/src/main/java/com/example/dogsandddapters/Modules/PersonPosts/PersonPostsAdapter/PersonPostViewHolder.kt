package com.example.dogsandddapters.Modules.GeneralPosts.PersonPostAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Modules.PersonPosts.PersonPostsRcyclerViewActivity
import com.example.dogsandddapters.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PersonPostViewHolder(val itemView: View,
                           val listener: PersonPostsRcyclerViewActivity.OnItemClickListener?,
                           var personposts: List<PersonPost>?): RecyclerView.ViewHolder(itemView) {

    var requestTextView: TextView? = null
    var offerTextView: TextView? = null
    var contactTextView: TextView? = null
    var idTextView: TextView? = null
    var imageView: ImageView? = null
    var personpost: PersonPost? = null

    init {
        requestTextView = itemView.findViewById(R.id.requestTextViewperson)
        offerTextView = itemView.findViewById(R.id.offerTextViewperson)
        contactTextView = itemView.findViewById(R.id.contactTextViewperson)
        imageView = itemView.findViewById(R.id.postImageViewperson)
        idTextView = itemView.findViewById(R.id.personPostIdTextViewperson)


        itemView.setOnClickListener {
            Log.i("TAG", "PersonPostViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onPersonPostClicked(personpost)
        }
    }

//    fun bind(personpost: PersonPost?) {
//        this.personpost = personpost
//        requestTextView?.text = personpost?.request
//        offerTextView?.text = personpost?.offer
//        contactTextView?.text = personpost?.contact
//        idTextView?.text = personpost?.postid
////        Picasso.get().load(personpost?.image)
////            .resize(400, 400)
////            .centerCrop()
////            .into(imageView)
//    }

    fun bind(personpost: PersonPost?) {
        this.personpost = personpost
        requestTextView?.text = personpost?.request
        offerTextView?.text = personpost?.offer
        contactTextView?.text = personpost?.contact
        idTextView?.text = personpost?.postid

        // Load image using Picasso
        personpost?.image?.let { imageUrl ->
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get().load(imageUrl)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            // Image loaded successfully
                        }

                        override fun onError(e: Exception?) {
                            // Log or handle the error
                            Log.e("Picasso", "Error loading image", e)
                        }
                    })
            }
        }
    }

}