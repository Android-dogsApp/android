package com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostsRcyclerViewActivity
import com.example.dogsandddapters.R

class GeneralPostViewHolder(val itemView: View,
                        val listener: GeneralPostsRcyclerViewActivity.OnItemClickListener?,
                        var generalposts: List<GeneralPost>?): RecyclerView.ViewHolder(itemView) {

    var requestTextView: TextView? = null
    var offerTextView: TextView? = null
    var contactTextView: TextView? = null
    var idTextView: TextView? = null
    var imageImageView: ImageView? = null
    var generalpost: GeneralPost? = null

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

    fun bind(generalpost: GeneralPost?) {
        this.generalpost = generalpost
        requestTextView?.text = generalpost?.request
        offerTextView?.text = generalpost?.offer
        contactTextView?.text = generalpost?.contact
        idTextView?.text = generalpost?.id
       //imageImageView?.text= generalpost?.image
    }
}