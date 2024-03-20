package com.example.dogsandddapters;

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface DogApiService {
    @GET("breed/{breed}/list")
    fun getSubBreeds(@Path("breed") breed: String): Call<DogApiResponse>

}

