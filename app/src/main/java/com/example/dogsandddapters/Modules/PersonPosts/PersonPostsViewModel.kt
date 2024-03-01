package com.example.dogsandddapters.Modules.PersonPosts


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogsandddapters.Models.Person

class PersonPostsViewModel: ViewModel() {
    var personposts: LiveData<MutableList<Person>>? = null
}

