package com.example.dogsandddapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogsandddapters.Models.Person

class PersonViewModel : ViewModel(){
    var person: LiveData<Person>? = null

}