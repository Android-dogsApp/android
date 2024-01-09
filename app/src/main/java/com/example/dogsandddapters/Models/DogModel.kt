package com.example.dogsandddapters.Models

class DogModel private constructor() {

    val dogs: MutableList<Dog> = ArrayList()
    companion object {
        val instance: DogModel = DogModel()
    }

}
