package com.example.dogsandddapters.Modules.GeneralPosts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dogsandddapters.Models.GeneralPost


class GeneralPostViewModel: ViewModel() {
    var generalposts: LiveData<MutableList<GeneralPost>>? = null
}
