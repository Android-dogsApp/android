package com.example.dogsandddapters.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GeneralPost(
    @PrimaryKey val id: String,
    val request: String,
    val offer: String,
    val contact: String,
    val image: String)
