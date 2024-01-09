package com.example.dogsandddapters.Models

class AdapterModel private constructor() {
    val adapters: MutableList<Adapter> = ArrayList()
    companion object {
        val instance: AdapterModel = AdapterModel()
    }

}