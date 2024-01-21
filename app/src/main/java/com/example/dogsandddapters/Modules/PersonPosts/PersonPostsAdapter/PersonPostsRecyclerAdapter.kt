package com.example.dogsandddapters.Modules.PersonPosts.PersonPostAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import  com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Modules.GeneralPosts.PersonPostAdapter.PersonPostViewHolder
import  com.example.dogsandddapters.Modules.PersonPosts.PersonPostsRcyclerViewActivity
import  com.example.dogsandddapters.R

class PersonPostsRecyclerAdapter(var personposts: List<PersonPost>?): RecyclerView.Adapter<PersonPostViewHolder>() {

    var listener: PersonPostsRcyclerViewActivity.OnItemClickListener? = null

    override fun getItemCount(): Int = personposts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.general_post_layout_row, parent, false)
        return PersonPostViewHolder(itemView, listener, personposts)
    }

    override fun onBindViewHolder(holder: PersonPostViewHolder, position: Int) {
        val personpost = personposts?.get(position)
        holder.bind(personpost)
    }
}