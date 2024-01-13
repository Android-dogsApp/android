package com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import  com.example.dogsandddapters.Models.GeneralPost
import  com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostsRcyclerViewActivity
import  com.example.dogsandddapters.R

class GeneralPostsRecyclerAdapter(var generalposts: List<GeneralPost>?): RecyclerView.Adapter<GeneralPostViewHolder>() {

    var listener: GeneralPostsRcyclerViewActivity.OnItemClickListener? = null

    override fun getItemCount(): Int = generalposts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.general_post_layout_row, parent, false)
        return GeneralPostViewHolder(itemView, listener, generalposts)
    }

    override fun onBindViewHolder(holder: GeneralPostViewHolder, position: Int) {
        val generalpost = generalposts?.get(position)
        holder.bind(generalpost)
    }
}