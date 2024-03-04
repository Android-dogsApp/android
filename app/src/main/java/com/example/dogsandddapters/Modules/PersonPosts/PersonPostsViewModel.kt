package com.example.dogsandddapters.Modules.PersonPosts


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogsandddapters.Models.PersonPost

class PersonPostsViewModel: ViewModel() {
    var personposts: LiveData<MutableList<PersonPost>>? = null

    fun addAllPersonPosts(newPosts: List<PersonPost>){
        personposts?.value?.addAll(newPosts)
    }
}

